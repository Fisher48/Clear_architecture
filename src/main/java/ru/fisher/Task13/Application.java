package ru.fisher.Task13;

import ru.fisher.Task13.interfaces.RobotCommunicator;
import ru.fisher.Task13.models.MonadState;
import ru.fisher.Task13.models.RobotState;
import ru.fisher.Task13.models.State;

public class Application {

    public static void main(String[] args) {
        RobotCommunicator communicator = new ConsoleCommunicator();
        RobotMonad robot = new RobotMonad(communicator);

        RobotState initialState = new RobotState(0.0, 0.0, 0, State.soap);

        // Монадная цепочка вычислений
        MonadState<RobotState, Void> program =
                robot.move(100)
                        .then(robot.move(40))
                        .then(robot.turn(-30))
                        .then(robot.set(State.soap))
                        .then(robot.start())
                        .then(robot.stop())
                        .then(robot.move(150))
                        .then(robot.turn(65))
                        .then(robot.set(State.brush))
                        .then(robot.start())
                        .then(robot.move(75))
                        .then(robot.stop());

        // Запускаем программу
        program.run(initialState);
    }
}
