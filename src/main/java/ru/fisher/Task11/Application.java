package ru.fisher.Task11;

import ru.fisher.Task11.interfaces.CommonInterface;
import ru.fisher.Task11.models.Command;
import ru.fisher.Task11.models.RobotState;
import ru.fisher.Task11.models.State;

import java.util.List;

public class Application {

    public static void main(String[] args) {
        ConsoleCommunicator communicator = new ConsoleCommunicator();
        PureRobot pureRobot = new PureRobot(communicator);

        // Создаем общую функцию
        CommonInterface commonInterface = RobotInterface.setup(pureRobot, communicator);

        RobotAPI robot = new RobotAPI(commonInterface);

        RobotState initialState = new RobotState(0.0, 0.0, 0, State.soap);
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

        robot.executeCommands(commands, initialState);
    }
}
