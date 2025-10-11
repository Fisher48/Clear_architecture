package ru.fisher.Task9.models;

import lombok.Getter;

@Getter
public class Command {

    private final String name;
    private final String value;

    public Command(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
