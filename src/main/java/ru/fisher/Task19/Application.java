package ru.fisher.Task19;


import ru.fisher.Task19.interfaces.RobotCommunicator;
import ru.fisher.Task19.nodes.MoveNode;
import ru.fisher.Task19.interfaces.Node;
import ru.fisher.Task19.nodes.StopNode;

public class Application {
    /**
     * MonadState<RobotState, Void> program =
     *                 robot.move(100)
     *                         .then(robot.move(40))
     *                         .then(robot.turn(-30))
     *                         .then(robot.set(State.soap))
     *                         .then(robot.start())
     *                         .then(robot.stop())
     *                         .then(robot.move(150))
     *                         .then(robot.turn(65))
     *                         .then(robot.set(State.water))
     *                         .then(robot.start())
     *                         .then(robot.set(State.soap))
     *                         .then(robot.move(75))
     *                         .then(robot.refillWater(50))
     *                         .then(robot.turn(65))
     *                         .then(robot.move(50))
     *                         .then(robot.stop());
     * @param args
     */
    public static void main(String[] args) {
        RobotCommunicator communicator = new ConsoleCommunicator();
        Node node = new MoveNode(100, communicator,
                moveResp -> new MoveNode(75.0, communicator,
                        moveResponse -> new MoveNode(50.0, communicator,
                        stopResp -> new StopNode(null, communicator)))
        );

        RobotState initialState = new RobotState(0.0, 0.0, 0, State.soap, 50, 25);

        // Запускаем программу
        RobotState finalState = node.interpret(initialState);
        System.out.println(finalState);
    }
}
