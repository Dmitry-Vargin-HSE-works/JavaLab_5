package com.company;

import java.util.*;

public class HomeThread extends Thread{

    // A number of iterations
    // if it equals -1, it will run infinitely
    private long lifetime;
    private List<HashSet<Person>> people;
    private int height;
    private List<LiftThread> lifts;

    private long sleep_time = 1000;

    public HomeThread(long lifetime, int floor_num) {
        this.lifetime = lifetime;
        people = new ArrayList<HashSet<Person>>(Collections.nCopies(floor_num, new HashSet<Person>()));
        height = floor_num;
        lifts = new LinkedList<LiftThread>();
    }

    @Override
    public void run() {
        Random random = new Random();
        int chance = 5;
        int max_chance = 100;
        int rand_chance;
        int rand_end;
        while (lifetime != 0) {
            for (int i = 0; i < height; ++i) {
                rand_chance = random.nextInt(max_chance);
                if (rand_chance < chance) {
                    rand_end = random.nextInt(height);
                    if (i == rand_end)
                        continue;
                    Person tmp = new Person(i, rand_end);
                    people.get(i).add(tmp);
                    System.out.println(tmp + " ожидает лифт.\n");
                }
            }
            if (lifetime > 0) {
                lifetime--;
            }
        }
    }

    public boolean is_Alive() {
        return lifetime != 0;
    }

    public long getLifetime() {
        return lifetime;
    }

    public HashSet<Person> getPeopleFromFloor(int floor_num) {
        return people.get(floor_num);
    }

    public List<HashSet<Person>> getPeople() {
        return people;
    }

    public void removePersonFromFloor(Person person, int floor) {
        people.get(floor).removeIf(p -> person == p);
    }

    public void addLift(LiftThread lift) {
        lifts.add(lift);
        lift.setHome(this);
    }

    public LiftThread popLift(int id) {
        int tmp = 0;
        for (int i = 0; i < lifts.size(); ++i) {
            if (lifts.get(i).getId_() == id) {
                tmp = i;
                LiftThread lift = lifts.get(tmp);
                lifts.remove(tmp);
                return lift;
            }
        }
        return null;
    }

    public List<LiftThread> getLifts() {
        return lifts;
    }

    public long getSleepTime() {
        return sleep_time;
    }
}
