package ru.fisher;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;


enum State { water, soap, brush }

@Setter
@Getter
class Command {

    String name;
    String value;

    public Command(String name, String value) {
        this.name = name;
        this.value = value;
    }

}

@Setter
@Getter
class Position {

    Integer x;
    Integer y;

    public Position(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "POS " + "x = " + x + ", y = " + y;
    }
}

public class Robot {

    private Position position;
    private Integer angle;
    private State state;

    public Robot(Position position, Integer angle) {
        this.position = position;
        this.angle = angle;
        this.state = State.soap;
    }

    public void executeCommand(List<Command> commands) {
        for (Command command : commands) {
            switch (command.name) {
                case "move" -> move(command);
                case "turn" -> turn(command);
                case "set" -> set(command);
                case "start" -> start();
                case "stop" -> stop(command);
                default -> System.out.println("Unknown command");
            }
        }
    }

    public void move(Command command) {
        double distance = Double.parseDouble(command.value);
        double radians = Math.toRadians(this.angle);
        this.position.x = (int) Math.round(distance * Math.cos(radians));
        this.position.y = (int) Math.round(distance * Math.sin(radians));
        System.out.println(this.position);
    }

    public void turn(Command command) {
        int delta = Integer.parseInt(command.value);
        this.angle = (this.angle + delta) % 360;
        System.out.println("ANGLE " + this.angle);
    }

    public void set(Command command) {
        this.state = State.valueOf(command.value);
        System.out.println("STATE " + this.state);
    }

    public void start() {
        System.out.println("START WITH " + this.state);
    }

    public void stop(Command command) {
        System.out.println(command.name);
    }


    public static void main(String[] args) {
        List<Command> commansList = Arrays.asList(
                new Command("move", "40"),
                new Command("turn", "-30"),
                new Command("set", "soap"),
                new Command("start", null),
                new Command("stop", null),
                new Command("move", "150"),
                new Command("turn", "35"),
                new Command("set", "brush"),
                new Command("move", "30"),
                new Command("stop", null)
        );
        Robot robot = new Robot(new Position(0, 0), 0);
        robot.executeCommand(commansList);
    }
}


