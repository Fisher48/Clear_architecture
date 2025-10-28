package ru.fisher.Task19.nodes;

import ru.fisher.Task19.Result;
import ru.fisher.Task19.RobotState;
import ru.fisher.Task19.interfaces.Node;
import ru.fisher.Task19.interfaces.RobotCommunicator;
import ru.fisher.Task19.responses.StartResponse;

import java.util.function.Function;

public class StartNode extends CommandNode<StartResponse>{

    private final RobotCommunicator communicator;

    public StartNode(Function<StartResponse, Node> next, RobotCommunicator communicator) {
        super(next);
        this.communicator = communicator;
    }

    @Override
    protected Result<StartResponse> run(RobotState state) {
        StartResponse response = new StartResponse();
        communicator.transferToRobot("START -> " + response);
        return new Result<>(response, state);
    }
}
