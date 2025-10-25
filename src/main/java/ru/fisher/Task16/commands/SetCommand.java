package ru.fisher.Task16.commands;

import ru.fisher.Task16.events.request.SetRequestedEvent;
import ru.fisher.Task16.models.State;
import ru.fisher.Task16.interfaces.Command;
import ru.fisher.Task16.interfaces.Event;

import java.util.List;


public class SetCommand implements Command {

    private final State newState;

    public SetCommand(State newState) {
        this.newState = newState;
    }

    @Override
    public List<Event> toRequestEvents() {
        return List.of(new SetRequestedEvent(newState));
    }
}
