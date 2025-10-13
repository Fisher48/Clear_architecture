package ru.fisher.Task10.interfaces;

import ru.fisher.Task10.models.RobotState;

@FunctionalInterface
public interface MoveFunction {
    RobotState move(double distance, RobotState state);
}
