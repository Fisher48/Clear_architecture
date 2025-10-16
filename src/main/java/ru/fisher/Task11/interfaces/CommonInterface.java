package ru.fisher.Task11.interfaces;

import ru.fisher.Task11.models.Command;
import ru.fisher.Task11.models.RobotState;

@FunctionalInterface
public interface CommonInterface {
    RobotState execute(RobotState robotState, Command command);
}
