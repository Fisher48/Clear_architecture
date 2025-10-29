package ru.fisher.Task21;

import ru.fisher.Task21.interfaces.Command;
import ru.fisher.Task21.models.RobotState;

public class TurnCommand implements Command {

    private final int deltaAngle;

    public TurnCommand(int deltaAngle) {
        this.deltaAngle = deltaAngle;
    }

    @Override
    public RobotState execute(RobotState currentState) {
        int newAngle = (currentState.angle() + deltaAngle) % 360;

        RobotState newState = new RobotState(
                currentState.x(),
                currentState.y(),
                newAngle,
                currentState.state());
        System.out.println(newAngle);
        return newState;
    }
}
