package ru.fisher.Task15.commands;

import ru.fisher.Task15.RobotState;
import ru.fisher.Task15.events.TurnedEvent;
import ru.fisher.Task15.interfaces.Command;
import ru.fisher.Task15.interfaces.Event;

import java.util.List;


public class TurnCommand implements Command  {

    private final Integer deltaAngle;

    public TurnCommand(Integer deltaAngle) {
        this.deltaAngle = deltaAngle;
    }

    @Override
    public List<Event> toEvents(RobotState state) {
        int newAngle = state.angle() + deltaAngle;
        return List.of(new TurnedEvent(newAngle));
    }
}
