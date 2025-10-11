package ru.fisher.Task9;

import ru.fisher.Task9.interfaces.RobotCommunicator;
import ru.fisher.Task9.models.Command;
import ru.fisher.Task9.models.RobotState;
import ru.fisher.Task9.models.State;

import java.util.List;

public class App {

    public static void main(String[] args) {
        // Создаем коммуникатор и используем тот, который нужен
        RobotCommunicator console = new ConsoleCommunicator();

        // Внедряем зависимость через конструктор
        Pure_robot pureRobot = new Pure_robot(console);

        RobotState robotState = new RobotState(0.0, 0.0, 0, State.soap);
        List<Command> commands = List.of(
                new Command("move", "100"),
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

        pureRobot.executeCommand(commands, robotState);
    }
}
