package ru.fisher.Task15.interfaces;

import ru.fisher.Task15.RobotState;


public interface Event {
    RobotState runEvent(RobotState state);
}
