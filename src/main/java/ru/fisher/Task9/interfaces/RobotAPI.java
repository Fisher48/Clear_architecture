package ru.fisher.Task9.interfaces;


import ru.fisher.Task9.models.Command;
import ru.fisher.Task9.models.RobotState;

import java.util.List;

// Интерфейс базового робота
public interface RobotAPI {
    RobotState executeCommand(List<Command> commandList, RobotState state);
}
