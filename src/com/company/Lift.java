package com.company;

import java.util.HashSet;
import java.util.Set;

public class Lift extends Thread {
    private long id_;
    static private long last_id = -1;

    int max_people;
    private Set<Person> people;
    private int floor;

    // 1 if a person rise, -1 if a person fall
    private final int direction;

    public Lift(int max_people) {
        id_ = ++last_id;
        this.max_people = max_people;
        people = new HashSet<>();
        floor = 0;
        direction = 1;
    }

    @Override
    public void run() {

    }

    public long getId_() {
        return id_;
    }

    public int getFloor() {
        return floor;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void addPerson(Person person) {
        people.add(person);
    }

    public void removePerson(int id_) {
        Person tmp = null;
        for (Person person: people) {
            if (person.getId_() == id_) {
                tmp = person;
                break;
            }
        }
        people.remove(tmp);
    }

    public void removePerson(Person person) {
        Person tmp = null;
        for (Person p: people) {
            if (p == person) {
                tmp = person;
                break;
            }
        }
        people.remove(tmp);
    }

    public int getDirection() {
        return direction;
    }

    public boolean isFull() {
        return people.size() == max_people;
    }

    public boolean isEmpty() {
        return people.isEmpty();
    }
}
