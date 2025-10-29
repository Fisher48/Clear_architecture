package ru.fisher.Task21;


import ru.fisher.Task21.interfaces.RobotCommunicator;

// Реализация интерфейса RobotCommunicator для отображения вывода в консоль
public class ConsoleCommunicator implements RobotCommunicator {
    @Override
    public void transferToRobot(String message) {
        System.out.println("Console output: " + message);
    }
}
