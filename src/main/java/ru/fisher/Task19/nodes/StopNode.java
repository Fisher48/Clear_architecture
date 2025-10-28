package ru.fisher.Task19.nodes;

import ru.fisher.Task19.Result;
import ru.fisher.Task19.interfaces.RobotCommunicator;
import ru.fisher.Task19.RobotState;
import ru.fisher.Task19.interfaces.Node;
import ru.fisher.Task19.responses.StopResponse;

import java.util.function.Function;

public class StopNode extends CommandNode<StopResponse> {

    private final RobotCommunicator communicator;

    public StopNode(Function<StopResponse, Node> next, RobotCommunicator communicator) {
        super(next);
        this.communicator = communicator;
    }

    @Override
    protected Result<StopResponse> run(RobotState state) {
        StopResponse response = new StopResponse();
        communicator.transferToRobot("STOP -> " + response);
        return new Result<>(response, state);
    }
}
