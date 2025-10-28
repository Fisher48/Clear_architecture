package ru.fisher.Task19.nodes;

import ru.fisher.Task19.*;
import ru.fisher.Task19.interfaces.Node;
import ru.fisher.Task19.interfaces.RobotCommunicator;
import ru.fisher.Task19.responces.MoveResponse;

import java.util.function.Function;

public class MoveNode extends CommandNode<MoveResponse> {
    private final double distance;
    private final RobotCommunicator communicator;

    public MoveNode(double distance, RobotCommunicator communicator, Function<MoveResponse, Node> next) {
        super(next);
        this.distance = distance;
        this.communicator = communicator;
    }

    @Override
    protected Result<MoveResponse> run(RobotState state) {
        double radians = Math.toRadians(state.angle());
        double constrainedX = state.x() + Math.round(distance * Math.cos(radians));
        double constrainedY = state.y() + Math.round(distance * Math.sin(radians));

        // Возвращает constrained x,y и флаг
        Validators.MoveResponse.MoveCheckResult check =
                Validators.MoveResponse.checkPosition(constrainedX, constrainedY);
        double actualDx = Math.hypot(check.x() - state.x(), check.y() - state.y()); // реальный пройденный путь

        MoveResponse resp = new MoveResponse(actualDx, check.result().equals(Validators.MoveResponse.OK), check.result());
        RobotState newState = new RobotState(check.x(), check.y(), state.angle(), state.state(), state.waterLevel(), state.soapLevel());

        communicator.transferToRobot("MOVE -> " + resp);
        return new Result<>(resp, newState);
    }
}
