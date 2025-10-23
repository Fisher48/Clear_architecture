package ru.fisher.Task15.interfaces;

import ru.fisher.Task15.RobotState;
import java.util.List;


public interface Command {
    List<Event> toEvents(RobotState state);
}
