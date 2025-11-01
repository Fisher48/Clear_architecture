package ru.fisher.Task22;

import ru.fisher.Task22.interfaces.RobotCommunicator;
import ru.fisher.Task22.models.CommandList;
import ru.fisher.Task22.models.MonadState;
import ru.fisher.Task22.models.RobotState;
import ru.fisher.Task22.models.State;

import java.util.EnumSet;


public class Application {

    public static void main(String[] args) {
        RobotCommunicator communicator = new ConsoleCommunicator();
        RobotMonad robot = new RobotMonad(communicator);

        RobotState initialState = new RobotState(0.0, 0.0, 0,
                State.soap, 15, 10, EnumSet.allOf(CommandList.class));

        // Монадная цепочка вычислений, с проверкой на выход за пределы границы и емкостями с водой и мылом
        MonadState<RobotState, Void> program =
                robot.move(100)
                        .then(robot.move(40))
                        .then(robot.turn(-30))
                        .then(robot.set(State.soap))
                        .then(robot.start())
                        .then(robot.stop())
                        .then(robot.move(150))
                        .then(robot.turn(65))
                        .then(robot.set(State.water))
                        .then(robot.start())
                        .then(robot.set(State.soap))
                        .then(robot.move(75))
                        .then(robot.refillWater(50))
                        .then(robot.turn(65))
                        .then(robot.move(50))
                        .then(robot.stop());

        // Запускаем программу
        program.run(initialState);
    }
}
