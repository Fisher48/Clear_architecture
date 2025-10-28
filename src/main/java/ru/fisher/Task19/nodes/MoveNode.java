package ru.fisher.Task19.nodes;

import ru.fisher.Task19.*;
import ru.fisher.Task19.interfaces.Node;
import ru.fisher.Task19.interfaces.RobotCommunicator;
import ru.fisher.Task19.responses.MoveResponse;

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
        // Вычисляем и проверяем позицию
        double radians = Math.toRadians(state.angle());
        double newX = Math.round(state.x() + distance * Math.cos(radians));
        double newY = Math.round(state.y() + distance * Math.sin(radians));
        Validators.MoveResponse.MoveCheckResult check =
                Validators.MoveResponse.checkPosition(newX, newY);

        // Определяем успех и дистанцию
        boolean success = check.result().equals(Validators.MoveResponse.OK);
        double actualDistance = success ? distance :
                Math.hypot(check.x() - state.x(), check.y() - state.y());

        // Отправляем в коммуникатор
        communicator.transferToRobot(success ?
                String.format("POS (%.1f, %.1f)", check.x(), check.y()) :
                String.format("HIT_BARRIER at (%.1f, %.1f)", check.x(), check.y()));

        // Возвращаем результат
        return new Result<>(
                new MoveResponse(actualDistance, success),
                new RobotState(check.x(), check.y(), state.angle(), state.state(),
                        state.waterLevel(), state.soapLevel())
        );
    }
}
