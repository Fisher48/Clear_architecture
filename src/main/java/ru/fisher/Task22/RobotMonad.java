package ru.fisher.Task22;

import ru.fisher.Task22.interfaces.RobotCommunicator;
import ru.fisher.Task22.models.CommandList;
import ru.fisher.Task22.models.MonadState;
import ru.fisher.Task22.models.RobotState;
import ru.fisher.Task22.models.State;


import java.util.HashSet;
import java.util.Set;

import static ru.fisher.Task22.models.CommandList.*;

public class RobotMonad {

    private final RobotCommunicator communicator;

    public RobotMonad(RobotCommunicator communicator) {
        this.communicator = communicator;
    }

    private Set<CommandList> updateCommandList(RobotState currentState, CommandList command, boolean remove) {
        Set<CommandList> newCommandList = new HashSet<>(currentState.allowedCommands());
        if (remove) {
            newCommandList.remove(command);
            return newCommandList;
        }
        newCommandList.add(command);
        return newCommandList;
    }


    public MonadState<RobotState, Void> move(double distance) {
        return MonadState.modify(state -> {
            double radians = Math.toRadians(state.angle());
            double newX = Math.round(state.x() + distance * Math.cos(radians));
            double newY = Math.round(state.y() + distance * Math.sin(radians));

            // Проверка позиции на барьеры
            Validators.MoveResponse.MoveCheckResult check =
                    Validators.MoveResponse.checkPosition(newX, newY);

            Set<CommandList> updated;

            if (check.result().equals(Validators.MoveResponse.OK)) {
                communicator.transferToRobot("POS " + check.x() + "," + check.y());
                updated = updateCommandList(state, MOVE, false);
            } else {
                communicator.transferToRobot("HIT_BARRIER at (" + check.x() + "," + check.y() + ")");
                // Если выезд за границы, то удаляем MOVE из набора доступных команд
                updated = updateCommandList(state, MOVE, true);
            }

            communicator.transferToRobot(updated.toString());
            return new RobotState(check.x(), check.y(), state.angle(),
                    state.state(), state.waterLevel(), state.soapLevel(), updated);
        });
    }

    public MonadState<RobotState, Void> turn(int angle) {
        return MonadState.modify(state -> {
            int newAngle = (state.angle() + angle) % 360;
            if (newAngle < 0) newAngle += 360;

            // Проверка позиции на барьеры
            Validators.MoveResponse.MoveCheckResult check =
                    Validators.MoveResponse.checkPosition(state.x(), state.y());

            // Если не было ОК, то удаляем MOVE из набора доступных команд
            Set<CommandList> updated = check.result().equals(Validators.MoveResponse.OK)
                    ? updateCommandList(state, MOVE, false)
                    : updateCommandList(state, MOVE, true);

            RobotState newState = new RobotState(state.x(), state.y(), newAngle,
                    state.state(), state.waterLevel(), state.soapLevel(), updated);
            communicator.transferToRobot("ANGLE " + newAngle);
            communicator.transferToRobot(newState.allowedCommands().toString());
            return newState;
        });
    }

    // Установка состояния с проверкой ресурсов
    public MonadState<RobotState, Void> set(State newMode) {
        return MonadState.modify(state -> {
            // Проверяем доступность ресурсов для нового режима
            String resourceCheck = Validators.SetStateResponse.checkResources(newMode, state.waterLevel(), state.soapLevel());

            // Если не было ОК, то удаляем SET из набора доступных команд
            if (!resourceCheck.equals(Validators.SetStateResponse.OK)) {
                Set<CommandList> updated = updateCommandList(state, SET, true);
                communicator.transferToRobot("Cannot set state " + newMode + ": " + resourceCheck);
                communicator.transferToRobot(updated.toString());
                return new RobotState(state.x(), state.y(), state.angle(),
                        state.state(), state.waterLevel(), state.soapLevel(), updated);
            }

            // Расход ресурсов (только если проверка прошла)
            int newWaterLevel = state.waterLevel();
            int newSoapLevel = state.soapLevel();

            if (newMode == State.water) {
                newWaterLevel = Math.max(0, state.waterLevel() - 20);
            } else if (newMode == State.soap) {
                newSoapLevel = Math.max(0, state.soapLevel() - 10);
            }

            RobotState newState = new RobotState(state.x(), state.y(), state.angle(),
                    newMode, newWaterLevel, newSoapLevel, state.allowedCommands());
            communicator.transferToRobot("STATE " + newMode + " (water: " + newWaterLevel + ", soap: " + newSoapLevel + ")");
            communicator.transferToRobot(newState.allowedCommands().toString());
            return newState;
        });
    }

    // Пополнение ресурсов
    public MonadState<RobotState, Void> refillWater(int amount) {
        return MonadState.modify(state -> {
            int newWaterLevel = state.waterLevel() + amount;
            RobotState newState = new RobotState(state.x(), state.y(), state.angle(), state.state(),
                    newWaterLevel, state.soapLevel(), updateCommandList(state, SET, false));
            communicator.transferToRobot("REFILL_WATER: " + amount + " (total: " + newWaterLevel + ")");
            communicator.transferToRobot(newState.allowedCommands().toString());
            return newState;
        });
    }

    public MonadState<RobotState, Void> refillSoap(int amount) {
        return MonadState.modify(state -> {
            int newSoapLevel = state.soapLevel() + amount;
            RobotState newState = new RobotState(state.x(), state.y(), state.angle(), state.state(),
                    state.waterLevel(), newSoapLevel, updateCommandList(state, SET, false));
            communicator.transferToRobot("REFILL_SOAP: " + amount + " (total: " + newSoapLevel + ")");
            communicator.transferToRobot(newState.allowedCommands().toString());
            return newState;
        });
    }

    public MonadState<RobotState, Void> start() {
        return MonadState.modify(state -> {
            communicator.transferToRobot("START WITH " + state.state());
            communicator.transferToRobot(state.allowedCommands().toString());
            return state;
        });
    }

    public MonadState<RobotState, Void> stop() {
        return MonadState.modify(state -> {
            communicator.transferToRobot("STOP");
            communicator.transferToRobot(state.allowedCommands().toString());
            return state;
        });
    }
}
