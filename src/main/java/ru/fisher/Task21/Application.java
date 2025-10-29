package ru.fisher.Task21;

import ru.fisher.Task21.interfaces.Robot;
import ru.fisher.Task21.models.RobotState;
import ru.fisher.Task21.models.State;

public class Application {
    public static void main(String[] args) {

        RobotState initialState = new RobotState(0.0, 0.0, 0, State.soap);
        Robot robot = new RealRobot(initialState);
        Robot finalRobot = robot.move(100.0)
                .start()
                .set(State.brush)
                .move(25.0)
                .turn(50)
                .move(250)
                .turn(65)
                .stop();

        System.out.println(finalRobot.state());

    }
}
