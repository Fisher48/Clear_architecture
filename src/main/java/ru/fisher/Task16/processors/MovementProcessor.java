package ru.fisher.Task16.processors;

import ru.fisher.Task16.EventStore;
import ru.fisher.Task16.models.RobotState;
import ru.fisher.Task16.events.request.MoveRequestedEvent;
import ru.fisher.Task16.events.result.MovedEvent;
import ru.fisher.Task16.interfaces.Event;
import ru.fisher.Task16.interfaces.EventProcessor;
import ru.fisher.Task16.interfaces.RobotCommunicator;


public class MovementProcessor implements EventProcessor {
    private final EventStore store;
    private final RobotCommunicator communicator;

    public MovementProcessor(EventStore store, RobotCommunicator communicator) {
        this.store = store;
        this.communicator = communicator;
        // подписываемся на события — обработка происходит в EventStore (асинхронно)
        store.subscribe(this::onEvent);
    }

    @Override
    public void onEvent(Event e) {
        if (!(e instanceof MoveRequestedEvent(Double distance))) return;

        // Вычисляем новое состояние, основываясь на текущем запросе
        RobotState current = store.rebuildState();
        double radians = Math.toRadians(current.angle());
        double newX = Math.round(current.x() + distance * Math.cos(radians));
        double newY = Math.round(current.y() + distance * Math.sin(radians));

        // Создаём результирующее событие
        MovedEvent result = new MovedEvent(newX, newY);

        // Сохраняем результат в EventStore — это вызовет новую публикацию
        store.append(result);

        // Уведомляем внешний мир (коммуникатор)
        communicator.transferToRobot("POS: " + newX + ", " + newY);
    }
}
