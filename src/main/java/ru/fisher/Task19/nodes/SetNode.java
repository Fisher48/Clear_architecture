package ru.fisher.Task19.nodes;

import ru.fisher.Task19.Validators;
import ru.fisher.Task19.Result;
import ru.fisher.Task19.RobotState;
import ru.fisher.Task19.State;
import ru.fisher.Task19.interfaces.Node;
import ru.fisher.Task19.interfaces.RobotCommunicator;
import ru.fisher.Task19.responses.SetResponse;

import java.util.function.Function;

public class SetNode extends CommandNode<SetResponse> {

    private final State newMode;

    private final RobotCommunicator communicator;

    public SetNode(State newMode, RobotCommunicator communicator, Function<SetResponse, Node> next) {
        super(next);
        this.newMode = newMode;
        this.communicator = communicator;
    }

    @Override
    protected Result<SetResponse> run(RobotState state) {
        // Проверяем доступность ресурсов для нового режима
        String resourceCheck = Validators.SetStateResponse.checkResources(newMode, state.waterLevel(), state.soapLevel());

        if (!resourceCheck.equals(Validators.SetStateResponse.OK)) {
            communicator.transferToRobot("Cannot set state " + newMode + ": " + resourceCheck);
            SetResponse response = new SetResponse(newMode, false);
            return new Result<>(response, state); // Возвращаем старое состояние при ошибке
        }

        // Расход ресурсов (только если проверка прошла)
        int newWaterLevel = state.waterLevel();
        int newSoapLevel = state.soapLevel();

        if (newMode == State.water) {
            newWaterLevel = Math.max(0, state.waterLevel() - 20);
        } else if (newMode == State.soap) {
            newSoapLevel = Math.max(0, state.soapLevel() - 10);
        }

        RobotState newState = new RobotState(state.x(), state.y(), state.angle(), newMode, newWaterLevel, newSoapLevel);
        SetResponse response = new SetResponse(newMode, true);
        communicator.transferToRobot("STATE -> " + newMode + " (water: " + newWaterLevel + ", soap: " + newSoapLevel + ")");
        return new Result<>(response, newState);
    }
}
