package com.company;

import javax.management.BadAttributeValueExpException;

public class Person {
    private long id_;
    static private long last_id = -1;
    private final int start;
    private final int end;
    private int takenBy = -1;

    // 1 if a person rise, -1 if a person fall
    private final int direction;

    public Person(int start_floor, int end_floor) throws RuntimeException {
        id_ = ++last_id;
        start = start_floor;
        end = end_floor;
        if (start < end)
            direction = 1;
        else if (start > end)
            direction = -1;
        else
            throw new RuntimeException("\n\nStart and end floors can't be equal!");
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getDirection() {
        return direction;
    }

    public long getId_() {
        return id_;
    }

    @Override
    public String toString() {
        return String.format("Person %d: {s=%d, e=%d, d=%d}", id_,  start, end, direction);
    }

    public int getTakenBy() {
        return takenBy;
    }

    public void setTakenBy(int takenBy) {
        this.takenBy = takenBy;
    }
}
