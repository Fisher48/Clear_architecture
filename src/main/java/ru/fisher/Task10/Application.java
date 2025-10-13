package ru.fisher.Task10;

import ru.fisher.Task10.Functions.*;
import ru.fisher.Task10.models.Command;
import ru.fisher.Task10.models.RobotState;
import ru.fisher.Task10.models.State;


import java.util.List;

public class Application {

    public static void main(String[] args) {
        ConsoleCommunicator console = new ConsoleCommunicator();

        // Каждая функция внедряется как зависимость
        RobotAPI robot = new RobotAPI(
                RobotFunctions.createMoveFunction(console),
                RobotFunctions.createTurnFunction(console),
                RobotFunctions.createSetFunction(console),
                RobotFunctions.createStartFunction(console),
                RobotFunctions.createStopFunction(console),
                console
        );

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
