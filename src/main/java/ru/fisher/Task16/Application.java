package ru.fisher.Task16;

import ru.fisher.Task16.models.State;
import ru.fisher.Task16.commands.StopCommand;
import ru.fisher.Task16.commands.*;
import ru.fisher.Task16.interfaces.RobotCommunicator;
import ru.fisher.Task16.processors.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        EventStore eventStore = new EventStore(executor);
        RobotCommunicator communicator = new ConsoleCommunicator();

        // Создаём процессоры (они сами подписываются на store)
        MovementProcessor movementProcessor = new MovementProcessor(eventStore, communicator);
        TurnProcessor turnProcessor = new TurnProcessor(eventStore, communicator);
        SetProcessor setProcessor = new SetProcessor(eventStore, communicator);
        StartProcessor startProcessor = new StartProcessor(eventStore, communicator);
        StopProcessor stopProcessor = new StopProcessor(eventStore, communicator);

        CommandHandler handler = new CommandHandler(eventStore);

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

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        executor.shutdown();
    }
}
