package ru.fisher.Task15.events;

import ru.fisher.Task15.RobotState;
import ru.fisher.Task15.interfaces.Event;

public record TurnedEvent(int deltaAngle) implements Event {
    @Override
    public RobotState runEvent(RobotState state) {
        return new RobotState(state.x(), state.y(), deltaAngle, state.state());
    }
}
