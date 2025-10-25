package ru.fisher.Task16.events.result;

import ru.fisher.Task16.interfaces.Event;

public record MovedEvent(Double newX, Double newY) implements Event {
}
