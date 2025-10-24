package ru.fisher.Task15;

import ru.fisher.Task15.commands.SetCommand;
import ru.fisher.Task15.commands.StartCommand;
import ru.fisher.Task15.commands.StopCommand;
import ru.fisher.Task15.commands.MoveCommand;
import ru.fisher.Task15.commands.TurnCommand;
import ru.fisher.Task15.interfaces.RobotCommunicator;

public class Application {

    public static void main(String[] args) {
        EventStore eventStore = new EventStore();
        RobotCommunicator communicator = new ConsoleCommunicator();
        CommandHandler handler = new CommandHandler(eventStore, communicator);

        handler.handle(new MoveCommand(100.0));
        handler.handle(new MoveCommand(40.0));
        handler.handle(new TurnCommand(-30));
        handler.handle(new SetCommand(State.water));
        handler.handle(new StartCommand());
        handler.handle(new StopCommand());
        handler.handle(new MoveCommand(150.0));
        handler.handle(new TurnCommand(65));
        handler.handle(new StartCommand());
        handler.handle(new MoveCommand(75.0));
        handler.handle(new StopCommand());

        System.out.println("=== Undo/Redo пример ===");

        handler.undo();
        handler.undo();
        handler.redo();

        handler.handle(new MoveCommand(120.0));

        handler.undo();


        System.out.println("Итоговое состояние робота: после 2 undo() и 1 redo() " + handler.getCurrentState());

        System.out.println("Показать 6-ю команду");
        System.out.println(eventStore.getStateAt(6));
    }
}
