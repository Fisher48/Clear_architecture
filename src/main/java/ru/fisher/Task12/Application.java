package ru.fisher.Task12;

import ru.fisher.Task12.interfaces.RobotCommunicator;
import ru.fisher.Task12.models.RobotState;
import ru.fisher.Task12.models.State;


public class Application {

    public static void main(String[] args) {
        RobotCommunicator communicator = new ConsoleCommunicator();
        PureRobot pureRobot = new PureRobot(communicator);
        RobotState robotState = new RobotState(0.0,0.0,0, State.soap);
        RobotAPI stackRobot = new RobotAPI(pureRobot, robotState);

        // Подаем команды в постфиксном виде - единой строкой
        String commands = "100 move 40 move -30 turn soap set start stop 150 move 65 turn brush set start 75 move stop";

        stackRobot.execute(commands);
    }
}
