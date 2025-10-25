package ru.fisher.Task16.interfaces;

public interface EventProcessor {
    // вызывается при новом событии
    void onEvent(Event event);
}
