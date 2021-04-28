package com.company;


import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Home home = new Home(5);
        Lift lift1 = new Lift(home, 5);
        Lift lift2 = new Lift(home, 5);
        home.start();
        lift1.start();
        lift2.start();
    }
}
