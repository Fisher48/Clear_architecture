package ru.fisher.Task15.commands;

import ru.fisher.Task15.RobotState;
import ru.fisher.Task15.events.StopedEvent;
import ru.fisher.Task15.interfaces.Command;
import ru.fisher.Task15.interfaces.Event;

import java.util.List;

public class StopCommand implements Command {
    @Override
    public List<Event> toEvents(RobotState state) {
        return List.of(new StopedEvent());
    }
}
