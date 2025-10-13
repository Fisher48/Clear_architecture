package ru.fisher.Task10.interfaces;

import ru.fisher.Task10.models.RobotState;
import ru.fisher.Task10.models.State;

@FunctionalInterface
public interface SetFunction {
    RobotState setState(State state, RobotState currentState);
}
