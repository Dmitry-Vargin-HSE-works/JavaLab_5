package com.company;

import javax.management.BadAttributeValueExpException;

public class Person {
    private final int start;
    private final int end;

    // 1 if a person rise, -1 if a person fall
    private final int direction;

    public Person(int start_floor, int end_floor) throws RuntimeException {
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

    @Override
    public String toString() {
        return String.format("Person: {s=%d, e=%d, d=%d}", start, end, direction);
    }
}
