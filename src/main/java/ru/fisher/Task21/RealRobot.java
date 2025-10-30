package ru.fisher.Task21;


import ru.fisher.Task21.commands.*;
import ru.fisher.Task21.interfaces.Robot;
import ru.fisher.Task21.interfaces.RobotCommunicator;
import ru.fisher.Task21.models.RobotState;
import ru.fisher.Task21.models.State;

record RealRobot(RobotState robotState, RobotCommunicator communicator) implements Robot {

    @Override
    public Robot move(double distance) {
        RobotState newState = new MoveCommand(distance).execute(robotState);
        communicator.transferToRobot("POS: " + newState.x() + ", " + newState.y());
        return new RealRobot(newState, communicator);
    }

    @Override
    public Robot set(State newState) {
        RobotState state = new SetCommand(newState).execute(robotState);
        communicator.transferToRobot("STATE: " + state.state());
        return new RealRobot(state, communicator);
    }

    @Override
    public Robot turn(int delta) {
        int newAngle = (robotState.angle() + delta) % 360;
        RobotState newState = new TurnCommand(newAngle).execute(robotState);
        communicator.transferToRobot("ANGLE: " + newState.angle());
        return new RealRobot(newState, communicator);
    }

    @Override
    public RobotState state() {
        return robotState;
    }

    @Override
    public Robot start() {
        RobotState newState = new StartCommand().execute(robotState);
        communicator.transferToRobot("START");
        return new RealRobot(newState, communicator);
    }

    @Override
    public Robot stop() {
        RobotState newState = new StopCommand().execute(robotState);
        communicator.transferToRobot("STOP");
        return new RealRobot(newState, communicator);
    }

}
