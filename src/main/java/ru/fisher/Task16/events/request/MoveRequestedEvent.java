package ru.fisher.Task16.events.request;

import ru.fisher.Task16.interfaces.Event;

public record MoveRequestedEvent(Double distance) implements Event {
}
