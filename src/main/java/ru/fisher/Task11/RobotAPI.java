package ru.fisher.Task11;

import ru.fisher.Task11.interfaces.CommonInterface;
import ru.fisher.Task11.models.Command;
import ru.fisher.Task11.models.RobotState;

import java.util.List;

public class RobotAPI {

    private final CommonInterface commonInterface;

    public RobotAPI(CommonInterface commonInterface) {
        this.commonInterface = commonInterface;
    }

    public RobotState executeCommands(List<Command> commands, RobotState state) {
        RobotState newState = state;
        for (Command command : commands) {
            newState = commonInterface.execute(newState, command);
        }
        return newState;
    }
}
