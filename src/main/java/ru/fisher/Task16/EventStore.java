package ru.fisher.Task16;

import ru.fisher.Task16.events.result.*;
import ru.fisher.Task16.interfaces.Event;
import ru.fisher.Task16.models.RobotState;
import ru.fisher.Task16.models.State;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class EventStore {

    private final List<Event> events = new CopyOnWriteArrayList<>(); // история событий
    private final List<Consumer<Event>> subscribers = new CopyOnWriteArrayList<>();
    private final ExecutorService publishExecutor; // для асинхронной публикации

    public EventStore(ExecutorService publishExecutor) {
        this.publishExecutor = publishExecutor;
    }

    private RobotState applyEventToState(RobotState s, Event e) {
        if (e instanceof MovedEvent(Double newX, Double newY)) {
            return new RobotState(newX, newY, s.angle(), s.state());
        }
        else if (e instanceof TurnedEvent(Integer deltaAngle)) {
            return new RobotState(s.x(), s.y(), deltaAngle, s.state());
        }
        else if (e instanceof SettedEvent(State newState)) {
            return new RobotState(s.x(), s.y(), s.angle(), newState);
        }
        else if (e instanceof StartedEvent) {
            return s; // Состояние не меняется
        }
        else if (e instanceof StopedEvent) {
            return s; // Состояние не меняется
        }
        return s;
    }

    // Пересборка всех событий
    public RobotState rebuildState() {
        RobotState s = new RobotState(0.0, 0.0, 0, State.soap);
        for (Event e : allEvents()) {
            s = applyEventToState(s, e);
        }
        return s;
    }

    public synchronized void append(Event e) {
        events.add(e);
        publish(e);
    }

    public synchronized void appendAll(List<Event> evs) {
        if (evs == null || evs.isEmpty()) return;
        events.addAll(evs);
        for (Event e : evs) {
            publish(e);
        }
    }

    public List<Event> allEvents() {
        return List.copyOf(events);
    }

    // Подписка: подписчик получит все будущие события
    public void subscribe(Consumer<Event> subscriber) {
        subscribers.add(subscriber);
    }

    private void publish(Event e) {
        // асинхронно пушим событие всем подписчикам
        for (Consumer<Event> sub : subscribers) {
            publishExecutor.submit(() -> {
                try {
                    sub.accept(e);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
}
