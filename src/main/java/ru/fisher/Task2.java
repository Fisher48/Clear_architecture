package ru.fisher;


public class Task2 {

    // Простая структура для данных робота
    static class RobotData {
        double x = 0;
        double y = 0;
        int angle = 0;
        String state = "soap";
    }

    public static void move(RobotData robot, int newDistance) {
        double radians = Math.toRadians(robot.angle);
        robot.x += Math.round(newDistance * Math.cos(radians));
        robot.y += Math.round(newDistance * Math.sin(radians));
        System.out.println("POS " + robot.x + ", " + robot.y);
    }

    public static void turn(RobotData robot, int newAngle) {
        robot.angle = (robot.angle + newAngle) % 360;
        System.out.println("ANGLE " + robot.angle);
    }

    public static void set(RobotData robot,String newState) {
        robot.state = newState;
        System.out.println("STATE " + newState);
    }

    public static void start() {
        System.out.println("START WITH start");
    }

    public static void stop() {
        System.out.println("STOP");
    }

    public static void main(String[] args) {
        RobotData data = new RobotData();
        move(data, 100);
        turn(data, 25);
        move(data, 100);
        start();
        stop();
        set(data, "water");
        turn(data, 90);
        move(data,125);
    }
}
