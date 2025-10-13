package ru.fisher.Task10.interfaces;

import ru.fisher.Task10.models.RobotState;

@FunctionalInterface
public interface StopFunction {
    RobotState stop(RobotState state);
}
