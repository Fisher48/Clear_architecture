package ru.fisher.Task19.nodes;

import ru.fisher.Task19.Result;
import ru.fisher.Task19.RobotState;
import ru.fisher.Task19.interfaces.Node;
import ru.fisher.Task19.interfaces.RobotCommunicator;
import ru.fisher.Task19.responces.StartResponse;

import java.util.function.Function;

public class StartNode extends CommandNode<StartResponse>{

    private final RobotCommunicator communicator;

    protected StartNode(Function<StartResponse, Node> next, RobotCommunicator communicator) {
        super(next);
        this.communicator = communicator;
    }

    @Override
    protected Result<StartResponse> run(RobotState state) {
        communicator.transferToRobot("STOP");
        StartResponse response = new StartResponse();
        return new Result<>(response, state);
    }
}
