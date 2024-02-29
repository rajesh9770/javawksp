package com.threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {
    Lock forks[];
    int N;
    public DiningPhilosophers() {
        N=5;
        forks = new Lock[N];
        for(int i=0; i<N; ++i){
            forks[i] = new ReentrantLock();
        }
    }

    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {
        Lock first, second;
        if(philosopher == 0){
            first = forks[(philosopher) ];
            second = forks[(philosopher-1+N) % 5];
        }else{
            first = forks[(philosopher-1+N) %5 ];
            second = forks[philosopher];
        }
        first.lock();
        second.lock();
        pickLeftFork.run();
        pickRightFork.run();
        eat.run();
        putRightFork.run();
        putLeftFork.run();
        second.unlock();
        first.unlock();
    }
}
