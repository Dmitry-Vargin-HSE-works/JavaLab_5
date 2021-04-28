package com.company;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Lift extends Thread {
    private long id_;
    static private long last_id = -1;

    int max_people;
    private Set<Person> people;
    private int floor = 0;
    private int target = 0;
    private Home home;

    // 1 if a person rise, -1 if a person fall
    private int direction = 0;

    public Lift(Home home, int max_people) {
        id_ = ++last_id;
        this.max_people = max_people;
        people = new HashSet<>();
        this.home = home;
        home.addLift(this);
        System.out.println("Создан " + this);
    }

    @Override
    public void run() {
        int tmp = 0;
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (home.getQueue().isEmpty()) {
                continue;
            }
            if (getDirection() == 0) {
                target = home.getFirstInQueue().getStart();
            }
            getDirection();
            //System.out.println(floor + " - " + target);
            while (floor != target) {
                tmp = getDirection();
                if (tmp == 1) {
                    target = Math.max(target, maxTarget());
                } else if (tmp == -1) {
                    target = Math.min(target, minTarget());
                } else {
                    target = nearestTarget();
                }
                getDirection();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                floor += direction;
                System.out.printf("%s перешел на %d этаж.%n", this, floor);
            }
        }
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

    public Home getHome() {
        return home;
    }

    public int getDirection() {
        direction = Integer.compare(target, floor);
        return direction;
    }

    public int nearestTarget() {
        int min_d = home.getFloors().size() + 1;
        int target = 0;
        int tmp = 0;
        for (Person person: people) {
            tmp = Math.abs(person.getEnd() - floor);
            if (tmp < min_d && tmp != 0) {
                min_d = tmp;
                target = person.getEnd();
            }
        }
        return target;
    }

    public int maxTarget() {
        int max = 0;
        int x = 0;
        for (Person person: people) {
            x = person.getEnd();
            if (x > max) {
                max = x;
            }
        }
        return x;
    }

    public int minTarget() {
        int min = 0;
        int x = home.getFloors().size();
        for (Person person: people) {
            x = person.getEnd();
            if (x < min) {
                min = x;
            }
        }
        return x;
    }

    public boolean isFull() {
        return people.size() == max_people;
    }

    public boolean isEmpty() {
        return people.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("Лифт (%d) (%d людей)", id_, people.size());
    }
}
