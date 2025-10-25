package ru.fisher.Task16.events.result;

import ru.fisher.Task16.models.State;
import ru.fisher.Task16.interfaces.Event;

public record SettedEvent(State newState) implements Event {
}
