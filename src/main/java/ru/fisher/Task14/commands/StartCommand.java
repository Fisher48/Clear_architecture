package ru.fisher.Task14.commands;

import ru.fisher.Task14.RobotState;
import ru.fisher.Task14.interfaces.Command;
import ru.fisher.Task14.interfaces.RobotCommunicator;

public class StartCommand implements Command  {

    @Override
    public RobotState execute(RobotState state, RobotCommunicator communicator) {
        communicator.transferToRobot("START");
        return new RobotState(state.x(), state.y(), state.angle(), state.state());
    }
}
