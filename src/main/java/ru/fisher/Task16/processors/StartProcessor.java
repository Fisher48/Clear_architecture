package ru.fisher.Task16.processors;

import ru.fisher.Task16.EventStore;
import ru.fisher.Task16.events.request.StartRequestedEvent;
import ru.fisher.Task16.events.result.StartedEvent;
import ru.fisher.Task16.interfaces.Event;
import ru.fisher.Task16.interfaces.EventProcessor;
import ru.fisher.Task16.interfaces.RobotCommunicator;

public class StartProcessor implements EventProcessor {

    private final EventStore eventStore;
    private final RobotCommunicator robotCommunicator;

    public StartProcessor(EventStore eventStore, RobotCommunicator robotCommunicator) {
        this.eventStore = eventStore;
        this.robotCommunicator = robotCommunicator;

        eventStore.subscribe(this::onEvent);
    }

    @Override
    public void onEvent(Event event) {
        if (!(event instanceof StartRequestedEvent)) return;
        eventStore.append(new StartedEvent());
        robotCommunicator.transferToRobot("START");
    }
}
