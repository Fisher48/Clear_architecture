package ru.fisher.Task4;


class Position {

    Double x;
    Double y;

    public Position(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "POS " + "x = " + x + ", y = " + y;
    }
}
