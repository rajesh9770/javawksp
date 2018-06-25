package com.problem;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Given start and stop times for jobs along a time line.
 * At time t, find how many jobs are running.
 * Over entire time line find the maximum jobs that are running at the same instance.
 * Here we are counting the resources required for the job and resources are not shared.
 */
public class JobResources {

    public static class Interval implements Comparable<Interval>{
        public int start;
        public int end;

        @Override
        public int compareTo(Interval interval) {
            return start- interval.start;
        }
        public Interval(int start, int end){
            this.start = start;
            this.end = end;
        }
    }

    public static void process(Interval[] intervals, int time){
        Arrays.sort(intervals);
        PriorityQueue<Interval> queue = new PriorityQueue<>(intervals.length, new Comparator<Interval>() {
            @Override
            public int compare(Interval t1, Interval t2) {
                return t1.end - t2.end;
            }
        });

        int runningJObs = 1;
        int maxConcurrentJObs = 1;
        queue.add(intervals[0]);
        boolean timeFound = false;
        for(int i=1; i<intervals.length; ++i){
            //remove the completed tasks - tasks that have end time <= min.
            int min = Math.min(time, intervals[i].start);
            Interval prevInterval = null;
            while( (prevInterval = queue.peek()) != null && prevInterval.end <=min){
                queue.remove();
                --runningJObs;
            }

            if(time< intervals[i].start){
                timeFound = true;
                break; //runningJobs gives the # of jobs running
            }else{
                //add current task
                queue.add(intervals[i]);
                ++runningJObs;
                maxConcurrentJObs = Math.max(maxConcurrentJObs, runningJObs);
            }
        }
        if(!timeFound){
            Interval prevInterval = null;
            while( (prevInterval = queue.peek()) != null && prevInterval.end <=time){
                queue.remove();
                --runningJObs;
            }
        }

        System.out.println("Running job at " + time + " = " + runningJObs);
        System.out.println("MaxRunning Jobs "  + maxConcurrentJObs);
    }

    public static void main(String[] args) {
//        Interval [] intervals = new Interval[]{
//                new Interval(1, 4),
//        };
//        process(intervals, 4); //0 , 1
//        process(intervals, 3); //1 , 1
//        process(intervals, 1); //1 , 1

        System.out.println("TestCase2: ");
        Interval [] intervals2 = new Interval[]{
                new Interval(1, 4),
                new Interval(6, 8),
                new Interval(2, 4),
                new Interval(7, 9),
                new Interval(10, 15),
                new Interval(3, 7)
        };
        process(intervals2, 4); //1, 3
        process(intervals2, 5); //1, 3
        process(intervals2, 7); //2, 3
        process(intervals2, 15); //0, 3
    }

}
