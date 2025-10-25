package ru.fisher.Task16;

import ru.fisher.Task16.interfaces.Command;
import ru.fisher.Task16.interfaces.Event;

import java.util.List;

public class CommandHandler {

    private final EventStore store;

    public CommandHandler(EventStore store) {
        this.store = store;
    }

    // Принимает команду и сохраняет request-событие(я)
    public void handle(Command cmd) {
        List<Event> requests = cmd.toRequestEvents();

        // записываем запросы в EventStore
        store.appendAll(requests);

    }

}
