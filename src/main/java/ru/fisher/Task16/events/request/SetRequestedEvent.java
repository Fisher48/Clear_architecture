package ru.fisher.Task16.events.request;

import ru.fisher.Task16.models.State;
import ru.fisher.Task16.interfaces.Event;

public record SetRequestedEvent(State newState) implements Event {
}
