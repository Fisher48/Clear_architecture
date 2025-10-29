package ru.fisher.Task21.commands;

import ru.fisher.Task21.interfaces.Command;
import ru.fisher.Task21.models.RobotState;

public class StopCommand implements Command {
    @Override
    public RobotState execute(RobotState currentState) {
        return currentState;
    }
}
