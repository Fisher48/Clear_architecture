package ru.fisher.Task19;

public record Result<R>(R response, RobotState newState) {}
