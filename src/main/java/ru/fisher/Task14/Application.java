package ru.fisher.Task14;

import ru.fisher.Task14.commands.*;
import ru.fisher.Task14.interfaces.Command;

import java.util.List;

public class Application {

    public static void main(String[] args) {
        List<Command> commands = List.of(
                new MoveCommand(100.0),
                new MoveCommand(40.0),
                new TurnCommand(-30),
                new SetCommand(State.soap),
                new StartCommand(),
                new StopCommand(),
                new MoveCommand(150.0),
                new TurnCommand(65),
                new StartCommand(),
                new MoveCommand(75.0),
                new StopCommand()
        );

        ConsoleCommunicator communicator = new ConsoleCommunicator();
        RobotState initialState = new RobotState(0.0,0.0,0,State.soap);
        RobotExecutor executor = new RobotExecutor(communicator);
        RobotState state = executor.run(commands, initialState);
        System.out.println(state);
    }
}
