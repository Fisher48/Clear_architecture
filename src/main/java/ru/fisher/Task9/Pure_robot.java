package ru.fisher.Task9;


import ru.fisher.Task9.interfaces.RobotAPI;
import ru.fisher.Task9.interfaces.RobotCommunicator;
import ru.fisher.Task9.models.Command;
import ru.fisher.Task9.models.RobotState;
import ru.fisher.Task9.models.State;

import java.util.List;

public class Pure_robot implements RobotAPI {

    private final RobotCommunicator communicator;

    // Внедряем зависимость коммуникатора
    public Pure_robot(RobotCommunicator communicator) {
        this.communicator = communicator;
    }

    @Override
    public RobotState executeCommand(List<Command> commands, RobotState currentState) {
        RobotState newState = currentState;
        for (Command command : commands) {
            switch (command.getName()) {
                case "move" -> {
                    if (command.getValue() != null) newState = move(command, newState);
                }
                case "turn" -> {
                    if (command.getValue() != null) newState = turn(command, newState);
                }
                case "set" -> {
                    if (command.getValue() != null) newState = set(command, newState);
                }
                case "start" -> start(newState);
                case "stop" -> stop(newState);
                default -> communicator.transferToRobot("Unknown command: " + command.getName());
            }
        }
        return newState;
    }

    private RobotState move(Command command, RobotState currentState) {
        double distance = Double.parseDouble(command.getValue());
        double radians = Math.toRadians(currentState.getAngle());

        // Создаем новый объект
        RobotState newState = new RobotState(
                currentState.getX() + Math.round(distance * Math.cos(radians)),
                currentState.getY() + Math.round(distance * Math.sin(radians)),
                currentState.getAngle(),
                currentState.getState()
        );

        communicator.transferToRobot("POS " + newState.getX() + ", " + newState.getY());
        return newState;  // возвращаем новое состояние
    }

    private RobotState turn(Command command, RobotState currentState) {
        int delta = Integer.parseInt(command.getValue());
        int newAngle = (currentState.getAngle() + delta) % 360;

        RobotState newState = new RobotState(
                currentState.getX(),
                currentState.getY(),
                newAngle,
                currentState.getState());

        communicator.transferToRobot("ANGLE " + newState.getAngle());
        return newState;
    }

    private RobotState set(Command command, RobotState currentState) {
        RobotState newState = new RobotState(
                currentState.getX(),
                currentState.getY(),
                currentState.getAngle(),
                State.valueOf(command.getValue()));

        communicator.transferToRobot("STATE " + newState.getState());
        return newState;
    }

    private RobotState start(RobotState currentState) {
        communicator.transferToRobot("START WITH " + currentState.getState());
        return currentState;
    }

    private RobotState stop(RobotState currentState) {
        communicator.transferToRobot("STOP");
        return currentState;
    }

}
