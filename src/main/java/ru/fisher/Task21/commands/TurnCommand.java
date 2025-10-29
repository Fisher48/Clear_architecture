package ru.fisher.Task21.commands;

import ru.fisher.Task21.interfaces.Command;
import ru.fisher.Task21.models.RobotState;

public class TurnCommand implements Command {

    private final int deltaAngle;

    public TurnCommand(int deltaAngle) {
        this.deltaAngle = deltaAngle;
    }

    @Override
    public RobotState execute(RobotState state) {
        int newAngle = (state.angle() + deltaAngle) % 360;
        RobotState newState = new RobotState(state.x(), state.y(), newAngle, state.state());
        return new RobotState(newState.x(), newState.y(), newAngle, newState.state());
    }
}
