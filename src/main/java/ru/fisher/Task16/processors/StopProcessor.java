package ru.fisher.Task16.processors;

import ru.fisher.Task16.EventStore;
import ru.fisher.Task16.events.request.StopRequestedEvent;
import ru.fisher.Task16.events.result.StopedEvent;
import ru.fisher.Task16.interfaces.Event;
import ru.fisher.Task16.interfaces.EventProcessor;
import ru.fisher.Task16.interfaces.RobotCommunicator;

public class StopProcessor implements EventProcessor {

    private final EventStore eventStore;
    private final RobotCommunicator robotCommunicator;

    public StopProcessor(EventStore eventStore, RobotCommunicator robotCommunicator) {
        this.eventStore = eventStore;
        this.robotCommunicator = robotCommunicator;

        // подписываемся на события — обработка происходит в EventStore (асинхронно)
        eventStore.subscribe(this::onEvent);
    }

    @Override
    public void onEvent(Event event) {
        if (!(event instanceof StopRequestedEvent)) return;
        eventStore.append(new StopedEvent());
        robotCommunicator.transferToRobot("STOP");
    }
}
