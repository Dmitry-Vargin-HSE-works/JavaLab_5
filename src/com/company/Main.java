package com.company;


import java.util.Random;

public class Main {
    public static void main(String[] args) {
        HomeThread homeThread = new HomeThread(Long.MAX_VALUE, 5);
        LiftThread liftThread1 = new LiftThread(homeThread, 5);
        LiftThread liftThread2 = new LiftThread(homeThread, 5);

        homeThread.start();
        liftThread1.start();
        liftThread2.start();
    }
}
