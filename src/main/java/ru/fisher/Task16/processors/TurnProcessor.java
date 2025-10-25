package ru.fisher.Task16.processors;

import ru.fisher.Task16.EventStore;
import ru.fisher.Task16.events.request.TurnRequestedEvent;
import ru.fisher.Task16.events.result.TurnedEvent;
import ru.fisher.Task16.interfaces.Event;
import ru.fisher.Task16.interfaces.EventProcessor;
import ru.fisher.Task16.interfaces.RobotCommunicator;
import ru.fisher.Task16.models.RobotState;

public class TurnProcessor implements EventProcessor {

    private final EventStore store;
    private final RobotCommunicator communicator;

    public TurnProcessor(EventStore store, RobotCommunicator communicator) {
        this.store = store;
        this.communicator = communicator;

        // подписываемся на события — обработка происходит в EventStore (асинхронно)
        store.subscribe(this::onEvent);
    }

    @Override
    public void onEvent(Event event) {
        if (!(event instanceof TurnRequestedEvent req)) return;
        RobotState state = store.rebuildState();
        int newAngle = (state.angle() + req.deltaAngle()) % 360;

        // создаём результирующее событие (TurnedEvent)
        TurnedEvent result = new TurnedEvent(newAngle);

        // сохраняем результат в EventStore — это вызовет новую публикацию
        store.append(result);

        // уведомляем внешний мир (коммуникатор)
        communicator.transferToRobot("ANGLE: " + newAngle);
    }
}
