package ru.fisher.Task14.commands;

import ru.fisher.Task14.interfaces.RobotCommunicator;
import ru.fisher.Task14.RobotState;
import ru.fisher.Task14.interfaces.Command;

public class MoveCommand implements Command {

    private final Double distance;

    public MoveCommand(Double distance) {
        this.distance = distance;
    }

    @Override
    public RobotState execute(RobotState state, RobotCommunicator communicator) {
        double radians = Math.toRadians(state.angle());
        double deltaX = Math.round(distance * Math.cos(radians));
        double deltaY = Math.round(distance * Math.sin(radians));

        double newX = state.x() + deltaX;
        double newY = state.y() + deltaY;
        RobotState newState = new RobotState(newX, newY, state.angle(), state.state());

        communicator.transferToRobot("POS " + newX + "," + newY);
        return newState;
    }
}
