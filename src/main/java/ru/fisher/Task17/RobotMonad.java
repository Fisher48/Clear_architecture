package ru.fisher.Task17;

import ru.fisher.Task17.interfaces.RobotCommunicator;
import ru.fisher.Task17.models.MonadState;
import ru.fisher.Task17.models.RobotState;
import ru.fisher.Task17.models.State;

public class RobotMonad {

    private final RobotCommunicator communicator;

    public RobotMonad(RobotCommunicator communicator) {
        this.communicator = communicator;
    }

    public MonadState<RobotState, Void> move(double distance) {
        return MonadState.modify(state -> {
            double radians = Math.toRadians(state.angle());
            double newX = Math.round(state.x() + distance * Math.cos(radians));
            double newY = Math.round(state.y() + distance * Math.sin(radians));

            // Проверка позиции на барьеры
            Validators.MoveResponse.MoveCheckResult check =
                    Validators.MoveResponse.checkPosition(newX, newY);

            RobotState newState = new RobotState(check.x(), check.y(), state.angle(),
                    state.state(), state.waterLevel(), state.soapLevel());

            if (check.result().equals(Validators.MoveResponse.OK)) {
                communicator.transferToRobot("POS " + check.x() + "," + check.y());
            } else {
                communicator.transferToRobot("HIT_BARRIER at (" + check.x() + "," + check.y() + ")");
            }

            return newState;

        });
    }

    public MonadState<RobotState, Void> turn(int angle) {
        return MonadState.modify(state -> {
            int newAngle = (state.angle() + angle) % 360;
            if (newAngle < 0) newAngle += 360;
            RobotState newState = new RobotState(state.x(), state.y(), newAngle, state.state(), state.waterLevel(), state.soapLevel());
            communicator.transferToRobot("ANGLE " + newAngle);
            return newState;
        });
    }

    // Установка состояния с проверкой ресурсов
    public MonadState<RobotState, Void> set(State newMode) {
        return MonadState.modify(state -> {
            // Проверяем доступность ресурсов для нового режима
            String resourceCheck = Validators.SetStateResponse.checkResources(newMode, state.waterLevel(), state.soapLevel());

            if (!resourceCheck.equals(Validators.SetStateResponse.OK)) {
                communicator.transferToRobot("Cannot set state " + newMode + ": " + resourceCheck);
                return state; // Возвращаем старое состояние при ошибке
            }

            // Расход ресурсов (только если проверка прошла)
            int newWaterLevel = state.waterLevel();
            int newSoapLevel = state.soapLevel();

            if (newMode == State.water) {
                newWaterLevel = Math.max(0, state.waterLevel() - 20);
            } else if (newMode == State.soap) {
                newSoapLevel = Math.max(0, state.soapLevel() - 10);
            }

            RobotState newState = new RobotState(state.x(), state.y(), state.angle(), newMode, newWaterLevel, newSoapLevel);
            communicator.transferToRobot("STATE " + newMode + " (water: " + newWaterLevel + ", soap: " + newSoapLevel + ")");
            return newState;
        });
    }

    // Пополнение ресурсов
    public MonadState<RobotState, Void> refillWater(int amount) {
        return MonadState.modify(state -> {
            int newWaterLevel = state.waterLevel() + amount;
            RobotState newState = new RobotState(state.x(), state.y(), state.angle(), state.state(),
                    newWaterLevel, state.soapLevel());
            communicator.transferToRobot("REFILL_WATER: " + amount + " (total: " + newWaterLevel + ")");
            return newState;
        });
    }

    public  MonadState<RobotState, Void> refillSoap(int amount) {
        return MonadState.modify(state -> {
            int newSoapLevel = state.soapLevel() + amount;
            RobotState newState = new RobotState(state.x(), state.y(), state.angle(), state.state(),
                    state.waterLevel(), newSoapLevel);
            communicator.transferToRobot("REFILL_SOAP: " + amount + " (total: " + newSoapLevel + ")");
            return newState;
        });
    }

    public MonadState<RobotState, Void> start() {
        return MonadState.modify(state -> {
            communicator.transferToRobot("START WITH " + state.state());
            return state;
        });
    }

    public MonadState<RobotState, Void> stop() {
        return MonadState.modify(state -> {
            communicator.transferToRobot("STOP");
            return state;
        });
    }
}
