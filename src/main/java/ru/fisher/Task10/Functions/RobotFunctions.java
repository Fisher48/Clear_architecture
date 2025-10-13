package ru.fisher.Task10.Functions;

import ru.fisher.Task10.interfaces.*;
import ru.fisher.Task10.models.RobotState;

// Реализация всех функций из функциональных интерфейсов
public class RobotFunctions {

    public static MoveFunction createMoveFunction(RobotCommunicator communicator) {
        return (distance, state) -> {
            double radians = Math.toRadians(state.angle());
            RobotState newState = new RobotState(
                    state.x() + distance * Math.cos(radians),
                    state.y() + distance * Math.sin(radians),
                    state.angle(),
                    state.state()
            );
            communicator.transferToRobot("POS " + newState.x() + ", " + newState.y());
            return newState;
        };
    }

    public static TurnFunction createTurnFunction(RobotCommunicator communicator) {
        return (angle, state) -> {
            int newAngle = (state.angle() + angle) % 360;
            RobotState newState = new RobotState(
                    state.x(), state.y(), newAngle, state.state()
            );
            communicator.transferToRobot("ANGLE " + newState.angle());
            return newState;
        };
    }

    public static SetFunction createSetFunction(RobotCommunicator communicator) {
        return (newState, currentState) -> {
            RobotState updatedState = new RobotState(
                    currentState.x(), currentState.y(), currentState.angle(), newState
            );
            communicator.transferToRobot("STATE " + newState);
            return updatedState;
        };
    }

    public static StartFunction createStartFunction(RobotCommunicator communicator) {
        return (state) -> {
            communicator.transferToRobot("START WITH " + state.state());
            return state;
        };
    }

    public static StopFunction createStopFunction(RobotCommunicator communicator) {
        return (state) -> {
            communicator.transferToRobot("STOP");
            return state;
        };
    }
}
