package ru.fisher.Task17;

import ru.fisher.Task17.interfaces.RobotCommunicator;
import ru.fisher.Task17.models.Command;
import ru.fisher.Task17.models.RobotState;
import ru.fisher.Task17.models.State;

// Ключевые функции управления роботом
public class PureRobot {

    private final RobotCommunicator communicator;

    // Внедряем зависимость коммуникатора
    public PureRobot(RobotCommunicator communicator) {
        this.communicator = communicator;
    }

    public RobotState move(Command command, RobotState currentState) {
        double distance = Double.parseDouble(command.value());
        double radians = Math.toRadians(currentState.angle());

        // Создаем новый объект
        RobotState newState = new RobotState(
                currentState.x() + Math.round(distance * Math.cos(radians)),
                currentState.y() + Math.round(distance * Math.sin(radians)),
                currentState.angle(),
                currentState.state(),
                currentState.waterLevel(),
                currentState.soapLevel()
        );

        communicator.transferToRobot("POS " + newState.x() + ", " + newState.y());
        return newState;  // возвращаем новое состояние
    }

    public RobotState turn(Command command, RobotState currentState) {
        int delta = Integer.parseInt(command.value());
        int newAngle = (currentState.angle() + delta) % 360;

        RobotState newState = new RobotState(
                currentState.x(),
                currentState.y(),
                newAngle,
                currentState.state(),
                currentState.waterLevel(),
                currentState.soapLevel());

        communicator.transferToRobot("ANGLE " + newState.angle());
        return newState;
    }

    public RobotState set(Command command, RobotState currentState) {
        RobotState newState = new RobotState(
                currentState.x(),
                currentState.y(),
                currentState.angle(),
                State.valueOf(command.value()),
                currentState.waterLevel(),
                currentState.soapLevel());

        communicator.transferToRobot("STATE " + newState.state());
        return newState;
    }

    public RobotState start(RobotState currentState) {
        communicator.transferToRobot("START WITH " + currentState.state());
        return currentState;
    }

    public RobotState stop(RobotState currentState) {
        communicator.transferToRobot("STOP");
        return currentState;
    }

}
