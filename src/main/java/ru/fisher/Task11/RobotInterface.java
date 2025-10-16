package ru.fisher.Task11;

import ru.fisher.Task11.interfaces.RobotCommunicator;
import ru.fisher.Task11.interfaces.CommonInterface;
import ru.fisher.Task11.models.RobotState;

// Реализация общей функции функционального интерфейса
public class RobotInterface {

    public static CommonInterface setup(PureRobot pureRobot, RobotCommunicator communicator) {
        return (state, command) -> {
            RobotState newState = state;
            switch (command.name()) {
                case "move" -> newState = pureRobot.move(command, state);
                case "turn" -> newState = pureRobot.turn(command, state);
                case "set" -> newState = pureRobot.set(command, state);
                case "start" -> newState = pureRobot.start(state);
                case "stop" -> newState = pureRobot.stop(state);
                default -> communicator.transferToRobot("Unknown command: " + command.name());
            }
            return newState;
        };
    }
}
