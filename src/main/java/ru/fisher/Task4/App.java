package ru.fisher.Task4;

import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        List<Command> commansList = Arrays.asList(
                new Command("move", "40"),
                new Command("turn", "-30"),
                new Command("set", "soap"),
                new Command("start", null),
                new Command("stop", null),
                new Command("move", "150"),
                new Command("turn", "65"),
                new Command("set", "brush"),
                new Command("move", "30"),
                new Command("stop", null)
        );
        Robot robot = new Robot(new Position(0.0, 0.0), 0);
        robot.executeCommand(commansList);
    }
}
