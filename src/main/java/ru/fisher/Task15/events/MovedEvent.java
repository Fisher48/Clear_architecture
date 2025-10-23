package ru.fisher.Task15.events;

import ru.fisher.Task15.RobotState;
import ru.fisher.Task15.interfaces.Event;

public record MovedEvent(Double newX, Double newY) implements Event {
    @Override
    public RobotState runEvent(RobotState state) {
        return new RobotState(newX, newY, state.angle(), state.state());
    }
}
