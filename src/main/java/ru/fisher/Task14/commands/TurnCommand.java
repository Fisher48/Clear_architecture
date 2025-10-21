package ru.fisher.Task14.commands;

import ru.fisher.Task14.RobotState;
import ru.fisher.Task14.interfaces.Command;
import ru.fisher.Task14.interfaces.RobotCommunicator;

public class TurnCommand implements Command  {

    private final Integer deltaAngle;

    public TurnCommand(Integer deltaAngle) {
        this.deltaAngle = deltaAngle;
    }

    @Override
    public RobotState execute(RobotState state, RobotCommunicator communicator) {
        int newAngle = (state.angle() + deltaAngle) % 360;
        RobotState newState = new RobotState(state.x(), state.y(), newAngle, state.state());
        communicator.transferToRobot("ANGLE " + newAngle);
        return newState;
    }
}
