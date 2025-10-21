package ru.fisher.Task14;

import ru.fisher.Task14.interfaces.Command;
import ru.fisher.Task14.interfaces.RobotCommunicator;

import java.util.List;

public class RobotExecutor {

    private final RobotCommunicator communicator;

    public RobotExecutor(RobotCommunicator communicator) {
        this.communicator = communicator;
    }

    public RobotState run(List<Command> commands, RobotState initialState) {
        RobotState robotState = initialState;
        for (Command command : commands) {
           robotState = command.execute(robotState, communicator);
        }
        return robotState;
    }

}
