package ru.fisher.Task4;


import java.util.List;

public class Robot {

    private final Position position;
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
        this.position.x += (double) Math.round(distance * Math.cos(radians));
        this.position.y += (double) Math.round(distance * Math.sin(radians));
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

}
