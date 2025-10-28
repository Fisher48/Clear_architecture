package ru.fisher.Task19;


import ru.fisher.Task19.interfaces.RobotCommunicator;
import ru.fisher.Task19.nodes.MoveNode;
import ru.fisher.Task19.interfaces.Node;
import ru.fisher.Task19.nodes.Nodes;
import ru.fisher.Task19.nodes.SetNode;
import ru.fisher.Task19.nodes.StopNode;

public class Application {

    public static void main(String[] args) {
        RobotCommunicator communicator = new ConsoleCommunicator();

        // Обычная реализация через Node
        Node node = new MoveNode(100, communicator,
                moveResp -> new MoveNode(75.0, communicator,
                        moveResp2 -> new MoveNode(50.0, communicator,
                                moveResp3 -> new MoveNode(100, communicator,
                                        waterSet -> new SetNode(State.water, communicator,
                                                stopResp -> new StopNode(null, communicator)))))
        );

        // Реализация с помощью статических метод-фабрик Nodes
        Node nodes = Nodes.move(50.0, communicator,
                Nodes.move(25.0, communicator,
                        Nodes.set(State.soap, communicator,
                                Nodes.start(communicator,
                                        Nodes.turn(65, communicator,
                                                Nodes.set(State.water, communicator,
                                                        Nodes.move(50, communicator,
                                                                Nodes.turn(-30, communicator,
                                                                        Nodes.stop(communicator))))))))
        );

        RobotState initialState = new RobotState(0.0, 0.0, 0, State.soap, 50, 25);

        // Запускаем программу
        RobotState firstRobot = node.interpret(initialState);
        System.out.println(firstRobot);

        System.out.println("________");

        RobotState secondRobot = nodes.interpret(initialState);
        System.out.println(secondRobot);
    }
}
