package ru.fisher.Task14.interfaces;


import ru.fisher.Task14.RobotState;

public interface Command {
    RobotState execute(RobotState state, RobotCommunicator communicator);
}
