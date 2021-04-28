package com.company;

import java.util.*;

public class Home extends Thread {
    private long id_;
    static private long last_id = -1;

    private int floors_num;
    private List<HashSet<Person>> floors;

    private Set<Lift> lifts;
    private List<Person> queue;

    public Home(int floors_num) {
        if (floors_num < 2) {
            throw new RuntimeException("floors_num should be more than 2");
        }
        id_ = ++last_id;
        this.floors_num = floors_num;
        floors = new ArrayList<>(Collections.nCopies(floors_num, new HashSet<Person>()));
        queue = new LinkedList<Person>();
        lifts = new HashSet<Lift>();
        System.out.printf("Создан %s.%n", this);
    }

    public void addLift(Lift lift) {
        lifts.add(lift);
    }

    public void addLifts(Collection<Lift> lifts) {
        this.lifts.addAll(lifts);
    }

    public List<HashSet<Person>> getFloors() {
        return floors;
    }

    public List<Person> getQueue() {
        return queue;
    }

    public Person getFirstInQueue() {
        return queue.get(0);
    }

    @Override
    public void run() {
        int chance = 10, max_chance = 1000;
        Random random = new Random();
        int r;
        Person tmp;
        List<Person> delete_list = new LinkedList<>();
        while (true) {
            for (int i = 0; i < floors_num; ++i) {
                r = random.nextInt(1000);
                if (r < chance) {
                    r = random.nextInt(floors_num);
                    if (r != i) {
                        tmp = new Person(i, r);
                        System.out.printf("%s ждет на %d этаже.%n", tmp, tmp.getStart());
                        floors.get(i).add(tmp);
                        queue.add(tmp);
                    }
                }
            }
            for (Lift lift: lifts) {
                for (Person person: lift.getPeople()) {
                    if (person.getEnd() == lift.getFloor()) {
                        delete_list.add(person);
                    }
                }
                for (Person person: delete_list) {
                    System.out.printf("%s вышел на %d этаже.%n", person, lift.getFloor());
                    lift.removePerson(person);
                }
                delete_list.clear();

                for (Person person: floors.get(lift.getFloor())) {
                    if ((person.getDirection() == lift.getDirection() || lift.getDirection() == 0) &&
                            lift.getPeople().size() < lift.max_people && lift.getFloor() == person.getStart()) {
                        lift.addPerson(person);
                        System.out.printf("%s зашел на %d этаже в %s.%n", person, lift.getFloor(), lift);
                        delete_list.add(person);
                    }
                }
                for (Person person: delete_list) {
                    floors.get(lift.getFloor()).remove(person);
                    queue.remove(person);
                }
                delete_list.clear();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Дом (%d)", id_);
    }
}
