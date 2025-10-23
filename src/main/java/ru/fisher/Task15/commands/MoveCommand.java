package ru.fisher.Task15.commands;

import ru.fisher.Task15.RobotState;
import ru.fisher.Task15.events.MovedEvent;
import ru.fisher.Task15.interfaces.Command;
import ru.fisher.Task15.interfaces.Event;

import java.util.List;

public class MoveCommand implements Command {

    private final Double distance;

    public MoveCommand(Double distance) {
        this.distance = distance;
    }

    @Override
    public List<Event> toEvents(RobotState state) {
        double radians = Math.toRadians(state.angle());
        double newX = Math.round(state.x() + distance * Math.cos(radians));
        double newY = Math.round(state.y() + distance * Math.sin(radians));
        return List.of(new MovedEvent(newX, newY));
    }
}
