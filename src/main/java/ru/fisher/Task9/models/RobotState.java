package ru.fisher.Task9.models;

import lombok.Getter;

@Getter
public class RobotState {
    private final Double x;
    private final Double y;
    private final Integer angle;
    private final State state;

    public RobotState(Double x, Double y, Integer angle, State state) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.state = state;
    }
}
