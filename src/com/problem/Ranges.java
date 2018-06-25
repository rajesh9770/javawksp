package com.problem;

import java.util.*;

/* 
# There's a room with a TV and people are coming in and out to watch it. The TV is on only when there's at least a person in the room. 
# For each person that comes in, we record the start and end time. We want to know for how long the TV has been on. In other words: 
# Given a list of arrays of time intervals, write a function that calculates the total amount of time covered by the intervals. 
# For example: 

# input = [(1,4), (2,3)] 
# > 3 
# input = [(4,6), (1,2)] 
# > 3 
# input = [(1,4), (6,8), (2,4), (7,9), (10, 15)] 
# > 11

Here a resource(TV) is shared among people and we are counting how long the resource is used.
*/

public class Ranges {

    public static class Interval implements Comparable<Interval> {
        int start,end;
        public Interval(int start, int end){
            this.start = start;
            this.end = end;
        }

        public boolean contains(int c){
            return c>=start && c<=end; 
        }

        @Override
        public int compareTo(Interval interval) {
            return this.start - interval.start;
        }
    }

    NavigableMap<Integer, Interval> intervals = new TreeMap<Integer, Interval>();

    public Ranges() {

    }

    public void addInterval(int start, int end){
            Integer newStart = null;
            Integer newEnd = null;
            Integer floorKey = intervals.floorKey(start);
            if(floorKey == null) newStart = start;
            else{
                Interval floorInterval = intervals.get(floorKey);
                if(floorInterval.contains(start)) newStart = floorKey;
                else newStart = start;
            }
            Integer floorEndKey = intervals.floorKey(end);
            if(floorEndKey == null) newEnd = end;
            else{
                Interval floorInterval = intervals.get(floorEndKey);
                if(floorInterval.contains(end)) newEnd = floorInterval.end;
                else newEnd = end;
            }
            SortedMap<Integer, Interval> subMap = intervals.subMap(newStart, newEnd);
            subMap.clear();
            intervals.put(newStart, new Interval(newStart, newEnd));
    }

    public int getSum(){
        int sum = 0;
        for(Interval intv : intervals.values()){
            System.out.println("[" + intv.start + "," + intv.end + "]");
            sum += (intv.end - intv.start) ;
        }
        return sum;
    }

    public static void mainOld(String[] args) {
        Ranges ranges = new Ranges();
        ranges.addInterval(1, 4);
        ranges.addInterval(2,3);
        System.out.println(ranges.getSum());
        ranges = new Ranges();
        ranges.addInterval(4,6);
        ranges.addInterval(1,2);
        System.out.println(ranges.getSum());
        
        ranges = new Ranges();
        ranges.addInterval(1,4);
        ranges.addInterval(6,8);
        ranges.addInterval(2,4);
        ranges.addInterval(7,9);
        ranges.addInterval(10,15);
        
        System.out.println(ranges.getSum());
    }

    //this is O(n)
    public static void mainFaster(String [] args){
        //[1, 4] [2, 3]
        int N=100;
        int intervals []  = new int[N];
        for(int i=0; i<N; ++i){
            intervals[i] = 0;
        }
        intervals[1] += 1;
        intervals[4] -= 1;
        intervals[2] += 1;
        intervals[3] -= 1;

        int runningCt = 0;
        int TVOn = 0;
        for(int i=0; i<N; ++i){
            runningCt += intervals[i];
            if(runningCt>0) ++TVOn;
        }
        System.out.println(TVOn);

//      input = [(4,6), (1,2)]
//      > 3
//      input = [(1,4), (6,8), (2,4), (7,9), (10, 15)]
//      > 11

        for(int i=0; i<N; ++i){
            intervals[i] = 0;
        }
        intervals[4] += 1;
        intervals[6] -= 1;
        intervals[1] += 1;
        intervals[2] -= 1;

        runningCt = 0;
        TVOn = 0;
        for(int i=0; i<N; ++i){
            runningCt += intervals[i];
            if(runningCt>0) ++TVOn;
        }
        System.out.println(TVOn);


//      input = [(1,4), (6,8), (2,4), (7,9), (10, 15)]
//      > 11

        for(int i=0; i<N; ++i){
            intervals[i] = 0;
        }
        intervals[1] += 1; //it's not interval[i] = 1, you need to add +1 to existing value, since there could be multiple people entering at the same time
        intervals[4] -= 1; //it's not interval[i] = -1, you need to subtract 1 to existing value, since there could be multiple people leaving at the same time
        intervals[6] += 1;
        intervals[8] -= 1;
        intervals[2] += 1;
        intervals[4] -= 1;
        intervals[7] += 1;
        intervals[9] -= 1;
        intervals[10] += 1;
        intervals[15] -= 1;

        runningCt = 0;
        TVOn = 0;
        for(int i=0; i<N; ++i){
            runningCt += intervals[i];
            if(runningCt>0) ++TVOn;
        }
        System.out.println(TVOn);
    }

    //O(nlog(n))
    public static long processIntervals(Interval [] intervals){
        //sort by start time
        Arrays.sort(intervals);
        int TVOn = intervals[0].end - intervals[0].start;
        int prevMaxEnd = intervals[0].end;

        for(int i = 1; i<intervals.length; ++i){
            if(prevMaxEnd<=intervals[i].start){
                TVOn += intervals[i].end - intervals[i].start;
                prevMaxEnd = intervals[i].end;
            }else if(prevMaxEnd<=intervals[i].end){
                TVOn += intervals[i].end - prevMaxEnd;
                prevMaxEnd = intervals[i].end;
            }else{
                //nothing to do, this interval is covered by another
            }
        }
        return TVOn;
    }


    public static void main(String[] args) {

        Interval [] intervals = new Interval[]{
                new Interval(1, 4),
                new Interval(2, 3)
        };
        System.out.println(processIntervals(intervals));


        // input = [(4,6), (1,2)]
        // 3
        intervals = new Interval[]{
                new Interval(4, 6),
                new Interval(1, 2)
        };
        System.out.println(processIntervals(intervals));

        //# input = [(1,4), (6,8), (2,4), (7,9), (10, 15)]
        //Ans: 11
        intervals = new Interval[]{
                new Interval(1, 4),
                new Interval(6, 8),
                new Interval(2, 4),
                new Interval(7, 9),
                new Interval(10, 15)
        };
        System.out.println(processIntervals(intervals));
    }
}
