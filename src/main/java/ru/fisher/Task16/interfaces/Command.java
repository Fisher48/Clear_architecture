package ru.fisher.Task16.interfaces;

import java.util.List;

public interface Command {
    List<Event> toRequestEvents();
}
