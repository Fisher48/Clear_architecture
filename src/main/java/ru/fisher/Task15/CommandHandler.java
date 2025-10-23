package ru.fisher.Task15;

import ru.fisher.Task15.interfaces.Event;
import ru.fisher.Task15.interfaces.Command;
import ru.fisher.Task15.interfaces.RobotCommunicator;

import java.util.List;


public class CommandHandler {

    private final EventStore store;
    private final RobotCommunicator communicator;

    public CommandHandler(EventStore store, RobotCommunicator communicator) {
        this.store = store;
        this.communicator = communicator;
    }

    // Обрабатываем события
    public void handle(Command command) {
        // 1. Получаем текущее состояние
        RobotState current = store.getStateAt(store.getCurrentIndex());

        // 2. Преобразуем команду в события
        List<Event> newEvents = command.toEvents(current);

        if (!newEvents.isEmpty()) {
            // 3. Сохраняем события
            store.saveAll(newEvents);

            // 4. Применяем события
            applyEvents(newEvents);
        }
    }

    // Применяем события
    private void applyEvents(List<Event> events) {
        RobotState state = store.rebuildState();
        for (Event e : events) {
            state = e.runEvent(state);
            communicator.transferToRobot("Console output: " + state);
        }
    }

    public void undo() {
        if (store.canUndo()) {
            store.undo();
            store.getStateAt(store.getCurrentIndex());
        }
    }

    public void redo() {
        if (store.canRedo()) {
            store.redo();
            store.getStateAt(store.getCurrentIndex());
        }
    }

    public RobotState getCurrentState() {
        return store.getStateAt(store.getCurrentIndex());
    }
}
