package ru.fisher.Task16.commands;

import ru.fisher.Task16.events.request.TurnRequestedEvent;
import ru.fisher.Task16.interfaces.Command;
import ru.fisher.Task16.interfaces.Event;

import java.util.List;


public class TurnCommand implements Command {

    private final Integer deltaAngle;

    public TurnCommand(Integer deltaAngle) {
        this.deltaAngle = deltaAngle;
    }

    @Override
    public List<Event> toRequestEvents() {
        return List.of(new TurnRequestedEvent(deltaAngle));
    }
}
