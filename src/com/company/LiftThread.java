package com.company;

import java.util.HashSet;

public class LiftThread extends Thread{
    static private int last_id = -1;
    private int id_;

    private HomeThread home;
    private HashSet<Person> people;

    private int max_people;
    private int position = 0;
    private int direction = 1; // 1 if a person rise, -1 if a person fall

    public LiftThread(HomeThread home, int max_people) {
        this.id_ = ++last_id;
        setHome(home);
        this.max_people = max_people;
    }

    @Override
    public void run() {
        while (home.is_Alive()) {
            dumpPeople();
            try {
                Thread.sleep(home.getSleepTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getPeople();
        }
    }

    public int getId_() {
        return id_;
    }

    public HomeThread getHome() {
        return home;
    }

    public void setHome(HomeThread home) {
        this.home = home;
        home.addLift(this);
    }

    public void dumpPeople() {
        for (Person person: people) {
            if (person.getEnd() == position) {
                people.remove(person);
                System.out.println(person + " вышел на " + position + " этаже.\n");
            }
        }
    }

    public void getPeople() {
        for (Person person: home.getPeopleFromFloor(position)) {
            if (person.getDirection() == direction && people.size() <= max_people) {
                people.add(person);
                home.removePersonFromFloor(person, position);
                System.out.println(person + " зашел в лифт.\n");
            }
        }
    }
}
