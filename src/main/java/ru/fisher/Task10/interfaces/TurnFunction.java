package ru.fisher.Task10.interfaces;

import ru.fisher.Task10.models.RobotState;

@FunctionalInterface
public interface TurnFunction {
    RobotState turn(int angle, RobotState state);
}
