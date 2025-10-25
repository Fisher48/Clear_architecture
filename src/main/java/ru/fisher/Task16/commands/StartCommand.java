package ru.fisher.Task16.commands;

import ru.fisher.Task16.events.request.StartRequestedEvent;
import ru.fisher.Task16.interfaces.Command;
import ru.fisher.Task16.interfaces.Event;

import java.util.List;

public class StartCommand implements Command {

    @Override
    public List<Event> toRequestEvents() {
        return List.of(new StartRequestedEvent());
    }
}
