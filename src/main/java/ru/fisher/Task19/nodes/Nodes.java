package ru.fisher.Task19.nodes;

import ru.fisher.Task19.State;
import ru.fisher.Task19.interfaces.Node;
import ru.fisher.Task19.interfaces.RobotCommunicator;

public class Nodes {
    public static Node move(double distance, RobotCommunicator communicator) {
        return new MoveNode(distance, communicator, null);
    }

    public static Node move(double distance, RobotCommunicator communicator, Node next) {
        return new MoveNode(distance, communicator, response -> next);
    }

    public static Node turn(int angle, RobotCommunicator communicator, Node next) {
        return new TurnNode(turnResponse -> next, angle, communicator);
    }

    public static Node set(State state, RobotCommunicator communicator, Node next) {
        return new SetNode(state, communicator, setResponse -> next);
    }

    public static Node start(RobotCommunicator communicator, Node next) {
        return new StartNode(startResponse -> next, communicator);
    }

    public static Node stop(RobotCommunicator communicator) {
        return new StopNode(null, communicator);
    }

}
