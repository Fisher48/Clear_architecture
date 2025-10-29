package ru.fisher.Task21.interfaces;


import ru.fisher.Task21.models.RobotState;

public interface Command<T> {
    T execute(RobotState currentState);
}
