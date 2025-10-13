package ru.fisher.Task10;

import ru.fisher.Task10.interfaces.*;
import ru.fisher.Task10.models.Command;
import ru.fisher.Task10.models.RobotState;
import ru.fisher.Task10.models.State;

import java.util.List;

public class RobotAPI {

    // Передаем функции как элементы конструктора
    private final MoveFunction moveFunc;
    private final TurnFunction turnFunc;
    private final SetFunction setFunc;
    private final StartFunction startFunc;
    private final StopFunction stopFunc;
    private final RobotCommunicator communicator;

    // Каждая функция внедряется как зависимость
    public RobotAPI(
            MoveFunction moveFunc,
            TurnFunction turnFunc,
            SetFunction setFunc,
            StartFunction startFunc,
            StopFunction stopFunc,
            RobotCommunicator communicator
    ) {
        this.moveFunc = moveFunc;
        this.turnFunc = turnFunc;
        this.setFunc = setFunc;
        this.startFunc = startFunc;
        this.stopFunc = stopFunc;
        this.communicator = communicator;
    }

    public RobotState executeCommands(List<Command> commands, RobotState currentState) {
        RobotState newState = currentState;
        for (Command command : commands) {
            switch (command.name()) {
                case "move" -> {
                    if (command.value() != null)
                        newState = moveFunc.move(Double.parseDouble(command.value()), newState);
                }
                case "turn" -> {
                    if (command.value() != null)
                        newState = turnFunc.turn(Integer.parseInt(command.value()), newState);
                }
                case "set" -> {
                    if (command.value() != null)
                        newState = setFunc.setState(State.valueOf(command.value()), newState);
                }
                case "start" -> newState = startFunc.start(newState);
                case "stop" -> newState = stopFunc.stop(newState);
                default -> communicator.transferToRobot("Unknown command: " + command.name());
            }
        }
        return newState;
    }
}
