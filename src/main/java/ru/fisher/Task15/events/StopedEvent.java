package ru.fisher.Task15.events;

import ru.fisher.Task15.RobotState;
import ru.fisher.Task15.interfaces.Event;

public record StopedEvent() implements Event {
    @Override
    public RobotState runEvent(RobotState state) {
        return state;
    }
}
