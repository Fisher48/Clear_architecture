package ru.fisher.Task15.events;

import ru.fisher.Task15.RobotState;
import ru.fisher.Task15.State;
import ru.fisher.Task15.interfaces.Event;

public record SetedEvent(State newState) implements Event {
    @Override
    public RobotState runEvent(RobotState state) {
        return new RobotState(state.x(), state.y(), state.angle(), newState);
    }
}
