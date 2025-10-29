package ru.fisher.Task21.commands;

import ru.fisher.Task21.interfaces.Command;
import ru.fisher.Task21.models.RobotState;


public class MoveCommand implements Command {

    private final double distance;

    public MoveCommand(double distance) {
        this.distance = distance;
    }

    @Override
    public RobotState execute(RobotState state) {
        double radians = Math.toRadians(state.angle());
        double newX = Math.round(state.x() + distance * Math.cos(radians));
        double newY = Math.round(state.y() + distance * Math.sin(radians));
        return new RobotState(newX, newY, state.angle(), state.state());
    }
}
