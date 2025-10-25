package ru.fisher.Task16.commands;

import ru.fisher.Task16.events.request.MoveRequestedEvent;
import ru.fisher.Task16.interfaces.Command;
import ru.fisher.Task16.interfaces.Event;

import java.util.List;

public class MoveCommand implements Command {

    private final Double distance;

    public MoveCommand(Double distance) {
        this.distance = distance;
    }

    @Override
    public List<Event> toRequestEvents() {
        return List.of(new MoveRequestedEvent(distance));
    }
}
