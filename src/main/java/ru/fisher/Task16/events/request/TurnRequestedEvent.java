package ru.fisher.Task16.events.request;

import ru.fisher.Task16.interfaces.Event;

public record TurnRequestedEvent(Integer deltaAngle) implements Event {
}
