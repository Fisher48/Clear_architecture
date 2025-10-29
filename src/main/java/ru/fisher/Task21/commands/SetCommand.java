package ru.fisher.Task21.commands;

import ru.fisher.Task21.interfaces.Command;
import ru.fisher.Task21.models.RobotState;
import ru.fisher.Task21.models.State;

public class SetCommand implements Command {

    private final State newState;

    public SetCommand(State newState) {
        this.newState = newState;
    }

    @Override
    public RobotState execute(RobotState currentState) {
        return new RobotState(currentState.x(), currentState.y(), currentState.angle(), newState);
    }
}
