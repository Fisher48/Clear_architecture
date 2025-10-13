package ru.fisher.Task10;

import ru.fisher.Task10.interfaces.RobotCommunicator;

// Реализация интерфейса RobotCommunicator для отображения вывода в консоль
public class ConsoleCommunicator implements RobotCommunicator {

    @Override
    public void transferToRobot(String message) {
        System.out.println("Console output: " + message);
    }
}
