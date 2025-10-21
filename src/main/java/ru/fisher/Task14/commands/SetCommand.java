package ru.fisher.Task14.commands;

import ru.fisher.Task14.RobotState;
import ru.fisher.Task14.State;
import ru.fisher.Task14.interfaces.Command;
import ru.fisher.Task14.interfaces.RobotCommunicator;

public class SetCommand implements Command {

    private final State newState;

    public SetCommand(State newState) {
        this.newState = newState;
    }

    @Override
    public RobotState execute(RobotState initialState, RobotCommunicator communicator) {
        RobotState state = new RobotState(
                initialState.x(),
                initialState.y(),
                initialState.angle(),
                newState);

        communicator.transferToRobot("STATE " + state.state());
        return state;
    }
}
