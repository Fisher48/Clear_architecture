package ru.fisher.Task13;

import ru.fisher.Task13.interfaces.RobotCommunicator;
import ru.fisher.Task13.models.MonadState;
import ru.fisher.Task13.models.RobotState;
import ru.fisher.Task13.models.State;

public class RobotMonad {

    private final RobotCommunicator communicator;

    public RobotMonad(RobotCommunicator communicator) {
        this.communicator = communicator;
    }

    public MonadState<RobotState, Void> move(double distance) {
        return MonadState.modify(state -> {
            double radians = Math.toRadians(state.angle());
            double newX = Math.round(state.x() + distance * Math.cos(radians));
            double newY = Math.round(state.y() + distance * Math.sin(radians));
            RobotState newState = new RobotState(newX, newY, state.angle(), state.state());
            communicator.transferToRobot("POS " + newX + "," + newY);
            return newState;
        });
    }

    public MonadState<RobotState, Void> turn(int angle) {
        return MonadState.modify(state -> {
            int newAngle = (state.angle() + angle) % 360;
            if (newAngle < 0) newAngle += 360;
            RobotState newState = new RobotState(state.x(), state.y(), newAngle, state.state());
            communicator.transferToRobot("ANGLE " + newAngle);
            return newState;
        });
    }

    public MonadState<RobotState, Void> set(State newMode) {
        return MonadState.modify(state -> {
            RobotState newState = new RobotState(state.x(), state.y(), state.angle(), newMode);
            communicator.transferToRobot("STATE " + newMode);
            return newState;
        });
    }

    public MonadState<RobotState, Void> start() {
        return MonadState.modify(state -> {
            communicator.transferToRobot("START WITH " + state.state());
            return state;
        });
    }

    public MonadState<RobotState, Void> stop() {
        return MonadState.modify(state -> {
            communicator.transferToRobot("STOP");
            return state;
        });
    }
}
