package ru.fisher.Task8;

import java.util.List;

public class PureRobot {

    private void transferToRobot(String command) {
        System.out.println(command);
    }

    public RobotState executeCommand(List<Command> commands, RobotState currentState) {
        RobotState newState = currentState;
        for (Command command : commands) {
            switch (command.name()) {
                case "move" -> {
                    if (command.value() != null) newState = move(command, newState);
                }
                case "turn" -> {
                    if (command.value() != null) newState = turn(command, newState);
                }
                case "set" -> {
                    if (command.value() != null) newState = set(command, newState);
                }
                case "start" -> start(newState);
                case "stop" -> stop(newState);
                default -> transferToRobot("Unknown command: " + command.name());
            }
        }
        return newState;
    }

    private RobotState move(Command command, RobotState currentState) {
        double distance = Double.parseDouble(command.value());
        double radians = Math.toRadians(currentState.angle());

        // Создаем новый объект
        RobotState newState = new RobotState(
                currentState.x() + Math.round(distance * Math.cos(radians)),
                currentState.y() + Math.round(distance * Math.sin(radians)),
                currentState.angle(),
                currentState.state());

        transferToRobot("POS " + newState.x() + ", " + newState.y());
        return newState;  // возвращаем новое состояние
    }

    private RobotState turn(Command command, RobotState currentState) {
        int delta = Integer.parseInt(command.value());
        int newAngle = (currentState.angle() + delta) % 360;

        RobotState newState = new RobotState(
                currentState.x(),
                currentState.y(),
                newAngle,
                currentState.state());

        transferToRobot("ANGLE " + newState.angle());
        return newState;
    }

    private RobotState set(Command command, RobotState currentState) {
        RobotState newState = new RobotState(
                currentState.x(),
                currentState.y(),
                currentState.angle(),
                State.valueOf(command.value()));

        transferToRobot("STATE " + newState.state());
        return newState;
    }

    private RobotState start(RobotState currentState) {
        transferToRobot("START WITH " + currentState.state());
        return currentState;
    }

    private RobotState stop(RobotState currentState) {
        transferToRobot("STOP");
        return currentState;
    }
}
