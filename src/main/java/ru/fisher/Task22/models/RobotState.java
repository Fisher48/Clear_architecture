package ru.fisher.Task22.models;


import java.util.Set;

public record RobotState(Double x,
                         Double y,
                         Integer angle,
                         State state,
                         Integer waterLevel,
                         Integer soapLevel,
                         Set<CommandList> allowedCommands) {

}
