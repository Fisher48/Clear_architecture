package ru.fisher.Task21.interfaces;


import ru.fisher.Task21.models.RobotState;
import ru.fisher.Task21.models.State;

public interface Robot {

    Robot start();
    Robot stop();
    Robot move(double distance);
    Robot set(State state);
    Robot turn(int angle);
    RobotState state();

}
