package ru.fisher.Task15.commands;

import ru.fisher.Task15.RobotState;
import ru.fisher.Task15.State;
import ru.fisher.Task15.events.SetedEvent;
import ru.fisher.Task15.interfaces.Command;
import ru.fisher.Task15.interfaces.Event;

import java.util.List;


public class SetCommand implements Command {

    private final State newState;

    public SetCommand(State newState) {
        this.newState = newState;
    }

    @Override
    public List<Event> toEvents(RobotState state) {
        return List.of(new SetedEvent(newState));
    }
}
