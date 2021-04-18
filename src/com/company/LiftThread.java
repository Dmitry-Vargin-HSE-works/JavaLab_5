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
        people = new HashSet<Person>();
        this.id_ = ++last_id;
        setHome(home);
        this.max_people = max_people;
    }

    @Override
    public void run() {
        System.out.println("Лифт " + id_ + " начал работать.\n");
        while (home.is_Alive()) {
            try {
                Thread.sleep(home.getSleepTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
    }

    public void dumpPeople() {
        for (Person person: people) {
            if (person.getEnd() == position) {
                people.remove(person);
                System.out.println(person + " вышел на " + position + " этаже.");
            }
        }
    }

    public void getPeople() {
        HashSet<Person> personSet = new HashSet<Person>();
        for (Person person: home.getPeopleFromFloor(position)) {
            if (person.getDirection() == direction && people.size() <= max_people && person.getTakenBy() == -1) {
                person.setTakenBy(id_);
                people.add(person);
                personSet.add(person);
                System.out.printf("%s зашел в лифт %d.%n", person, id_);
            }
        }

        for (Person person: personSet) {
            home.removePersonFromFloor(person, position);
        }
    }
}
