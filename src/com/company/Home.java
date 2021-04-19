package com.company;

import java.util.*;

public class Home extends Thread {
    private long id_;
    static private long last_id = -1;

    private int floors_num;
    private List<HashSet<Person>> floors;

    private Set<Lift> lifts;

    public Home(int floors_num) {
        id_ = ++last_id;
        this.floors_num = floors_num;
        floors = new ArrayList<>(Collections.nCopies(floors_num, new HashSet<Person>()));
    }

    public void addLift(Lift lift) {
        lifts.add(lift);
    }

    public void addLifts(Collection<Lift> lifts) {
        this.lifts.addAll(lifts);
    }

    @Override
    public void run() {
        int chance = 5, max_chance = 1000;
        Random random = new Random();
        int r;
        Person tmp;
        List<Person> delete_list = new LinkedList<>();
        while (true) {
            for (int i = 0; i < floors_num; ++i) {
                r = random.nextInt(1000);
                if (r < chance) {
                    r = random.nextInt(floors_num);
                    if (r != i)
                        tmp = new Person(i, r);
                }
            }

            for (int i = 0; i < floors_num; ++i) {
                for (Lift lift: lifts) {
                    if (i == lift.getFloor()) {
                        for (Person person: lift.getPeople()) {
                            if (person.getEnd() == i) {
                                delete_list.add(person);
                            }
                        }
                        for (Person person: delete_list) {
                            lift.removePerson(person);
                        }
                        delete_list.clear();

                        for (Person person: floors.get(i)) {
                            if (person.getDirection() == lift.getDirection() && lift.getPeople().size() < lift.max_people) {
                                lift.addPerson(person);
                                delete_list.add(person);
                            }
                        }
                        for (Person person: delete_list) {
                            floors.get(i).remove(person);
                        }
                        delete_list.clear();
                    }
                }
            }
        }
        for (Lift lift: lifts) {
            lift.stop();
        }
    }
}
