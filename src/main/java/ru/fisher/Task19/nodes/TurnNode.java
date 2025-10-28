package ru.fisher.Task19.nodes;

import ru.fisher.Task19.Result;
import ru.fisher.Task19.interfaces.RobotCommunicator;
import ru.fisher.Task19.RobotState;
import ru.fisher.Task19.interfaces.Node;
import ru.fisher.Task19.responses.TurnResponse;

import java.util.function.Function;

public class TurnNode extends CommandNode<TurnResponse> {

    private final Integer angle;
    private final RobotCommunicator communicator;

    public TurnNode(Function<TurnResponse, Node> next, Integer angle, RobotCommunicator communicator) {
        super(next);
        this.angle = angle;
        this.communicator = communicator;
    }

    @Override
    protected Result<TurnResponse> run(RobotState state) {
        int newAngle = (state.angle() + angle) % 360;
        if (newAngle < 0) newAngle += 360;
        RobotState newState = new RobotState(state.x(), state.y(), newAngle, state.state(), state.waterLevel(), state.soapLevel());
        TurnResponse response = new TurnResponse(newAngle, true);
        communicator.transferToRobot("ANGLE -> " + newAngle);
        return new Result<>(response, newState);
    }
}
