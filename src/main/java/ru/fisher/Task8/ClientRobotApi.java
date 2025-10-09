package ru.fisher.Task8;


import java.util.Arrays;
import java.util.List;

public class ClientRobotApi {

    public static void main(String[] args) {
        PureRobot pureRobot = new PureRobot();
        RobotState robotState = new RobotState(0.0,0.0,0,State.soap);

        // Список команд (последовательность)
        List<Command> commandsList = Arrays.asList(
                new Command("move", "40"),
                new Command("turn", "-30"),
                new Command("set", "soap"),
                new Command("start", null),
                new Command("stop", null),
                new Command("move", "150"),
                new Command("turn", "65"),
                new Command("set", "brush"),
                new Command("start", null),
                new Command("move", "75"),
                new Command("stop", null)
        );

       RobotState finalState = pureRobot.executeCommand(commandsList, robotState);
       System.out.println("Итоговое состояние робота: " +
               finalState.x().toString() + " " + finalState.y().toString() + " " +
               finalState.angle().toString() + " " + finalState.state().toString());

    }
}
