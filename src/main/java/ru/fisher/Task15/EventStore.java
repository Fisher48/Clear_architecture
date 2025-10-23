package ru.fisher.Task15;

import ru.fisher.Task15.interfaces.Event;

import java.util.ArrayList;
import java.util.List;

public class EventStore {

    private final List<Event> events = new ArrayList<>(); // история событий
    private int currentIndex = -1; // начальный индекс

    public void saveAll(List<Event> newEvents) {
        // удаляем "будущее" при добавлении новых событий
        while (events.size() - 1 > currentIndex) {
            events.removeLast();
        }
        events.addAll(newEvents);
        currentIndex = events.size() - 1;
    }

    public List<Event> getAllEvents() {
        return new ArrayList<>(events);
    }

    // Пересчитывает текущее состояние с нуля
    public RobotState rebuildState() {
        RobotState state = new RobotState(0.0, 0.0, 0, State.soap);
        for (Event e : events) {
            state = e.runEvent(state);
        }
        return state;
    }

    // Получить состояние с индексом targetIndex
    public RobotState getStateAt(int targetIndex) {
        RobotState state = new RobotState(0.0, 0.0, 0, State.soap);
        int endIndex = Math.min(targetIndex, events.size() - 1);
        for (int i = 0; i <= endIndex; i++) {
            state = events.get(i).runEvent(state);
        }
        return state;
    }

    public boolean canUndo() {
        return currentIndex >= 0;
    }

    public boolean canRedo() {
        return currentIndex < events.size() - 1;
    }

    public void undo() {
        if (canUndo()) currentIndex--;
    }

    public void redo() {
        if (canRedo()) currentIndex++;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }
}
