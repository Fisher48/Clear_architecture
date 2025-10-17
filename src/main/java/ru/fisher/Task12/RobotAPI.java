package ru.fisher.Task12;


import ru.fisher.Task12.models.RobotState;
import ru.fisher.Task12.models.Command;

import java.util.Stack;

public class RobotAPI {

    private final PureRobot robot;
    private RobotState robotState;
    private final Stack<String> commandStack = new Stack<>();

    public RobotAPI(PureRobot robot, RobotState initial) {
        this.robot = robot;
        this.robotState = initial;
    }

    // Осуществляем интерпретацию команд - используя стек
    public void execute(String input) {
        for (String string : input.split(" ")) {
            switch (string) {
                case "move" -> robotState = robot.move(new Command("move", commandStack.pop()), robotState);
                case "turn" -> robotState = robot.turn(new Command("turn", commandStack.pop()), robotState);
                case "set" -> robotState = robot.set(new Command("set", commandStack.pop()), robotState);
                case "start" -> robotState = robot.start(robotState);
                case "stop" -> robotState = robot.stop(robotState);
                default -> commandStack.push(string); // числа, строки и состояния
            }
        }
    }
}
