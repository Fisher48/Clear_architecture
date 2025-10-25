package ru.fisher.Task16.processors;

import ru.fisher.Task16.EventStore;
import ru.fisher.Task16.events.request.SetRequestedEvent;
import ru.fisher.Task16.events.result.SettedEvent;
import ru.fisher.Task16.interfaces.Event;
import ru.fisher.Task16.interfaces.EventProcessor;
import ru.fisher.Task16.interfaces.RobotCommunicator;

public class SetProcessor implements EventProcessor {

    private final EventStore store;
    private final RobotCommunicator communicator;

    public SetProcessor(EventStore store, RobotCommunicator communicator) {
        this.store = store;
        this.communicator = communicator;

        // подписываемся на события — обработка происходит в EventStore (асинхронно)
        store.subscribe(this::onEvent);
    }

    @Override
    public void onEvent(Event event) {
        if (!(event instanceof SetRequestedEvent req)) return;
        store.append(new SettedEvent(req.newState()));
        communicator.transferToRobot("STATE: " + req.newState());
    }
}
