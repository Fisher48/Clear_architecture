package ru.fisher.Task21;

import ru.fisher.Task21.interfaces.Robot;
import ru.fisher.Task21.models.RobotState;
import ru.fisher.Task21.models.State;

public class Application {
    public static void main(String[] args) {
        RobotState initialState = new RobotState(0.0, 0.0, 0, State.soap);
        Robot robot = new RealRobot(initialState, new ConsoleCommunicator());

        Robot finalRobot = robot.move(100.0)
                .move(40)
                .turn(-30)
                .set(State.soap)
                .start()
                .stop()
                .move(150)
                .turn(65)
                .set(State.brush).start()
                .move(75.0)
                .stop();

        System.out.println(finalRobot.state());

    }
}
