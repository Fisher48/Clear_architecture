package ru.fisher.Task16.events.result;

import ru.fisher.Task16.interfaces.Event;

public record TurnedEvent(Integer newAngle) implements Event {
}
