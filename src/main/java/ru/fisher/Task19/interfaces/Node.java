package ru.fisher.Task19.interfaces;

import ru.fisher.Task19.RobotState;

public interface Node {
    RobotState interpret(RobotState state);
}
