package ru.fisher.Task21;


import ru.fisher.Task21.interfaces.Robot;
import ru.fisher.Task21.models.RobotState;
import ru.fisher.Task21.models.State;

record RealRobot(RobotState robotState) implements Robot {

    @Override
    public Robot move(double distance) {
        double radians = Math.toRadians(robotState.angle());
        double newX = robotState.x() + Math.round(distance * Math.cos(radians));
        double newY = robotState.y() + Math.round(distance * Math.sin(radians));
        RobotState state = new RobotState(newX, newY, robotState.angle(), robotState.state());
        System.out.println("POS " + newX + ", " + newY);
        return new RealRobot(state);
    }

    @Override
    public Robot set(State newState) {
        RobotState state = new RobotState(robotState.x(), robotState.y(), robotState.angle(), newState);
        System.out.println("STATE: " + newState);
        return new RealRobot(state);
    }

    @Override
    public Robot turn(int delta) {
        int newAngle = (robotState.angle() + delta) % 360;
        RobotState state = new RobotState(robotState.x(), robotState.y(), newAngle, robotState.state());
        System.out.println("ANGLE: " + newAngle);
        return new RealRobot(state);
    }

    @Override
    public RobotState state() {
        return robotState;
    }

    @Override
    public Robot start() {
        RobotState state = new RobotState(robotState.x(), robotState.y(), robotState.angle(), robotState.state());
        System.out.println("START");
        return new RealRobot(state);
    }

    @Override
    public Robot stop() {
        RobotState state = new RobotState(robotState.x(), robotState.y(), robotState.angle(), robotState.state());
        System.out.println("STOP");
        return new RealRobot(state);
    }

}
