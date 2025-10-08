package ru.fisher.Task6;

import java.util.Arrays;
import java.util.List;

public class RobotApi {
    public static void main(String[] args) {
        // Реализован по аналогии с модулем из примера pure_robot.py
        // Функциональная реализация в папке Task6, файле - Pure_robot.java
        Pure_robot pureRobot = new Pure_robot();

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

        // Вся реализация скрыта от пользователя.
        // Он использует только один публичный метод executeCommand(),
        // в который передает список команд с начальным состоянием.
        // Остальная реализация скрыта от него.
        pureRobot.executeCommand(commandsList, pureRobot.robotState);
    }
}
