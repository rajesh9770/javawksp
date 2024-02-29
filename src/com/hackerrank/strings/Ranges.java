package com.hackerrank.strings;

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
    /**
     * IDIOM for overlapping ranges: check if (t1, t2) overlaps (b1, b2)
     *   b1 <= t2 && t1 <=b2
     * Same but with different symbols
     *  for overlapping ranges: check if (a1, b1) overlaps (a2, b2)
     *      *   a2 <= b1 && a1 <=b2
     */
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


    public static void mainForProcessIntervals(String[] args) {

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

    /**
     * Given an array of intervals where intervals[i] = [starti, endi],
     * merge all overlapping intervals,
     * and return an array of the non-overlapping intervals that cover all the intervals in the input.
     */

    public int[][] merge(int[][] intervals) {
        List<int[]> intervalsList = Arrays.asList(intervals); //ints.get(0) gives first row, etx
        Collections.sort(intervalsList, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return (o1[0] - o2[0]);
            }
        });

        int left = intervalsList.get(0)[0];
        int right = intervalsList.get(0)[1];

        List<int[]> ret = new ArrayList<>();
        for(int i=1; i<intervalsList.size(); ++i){
            int[] nextInterval = intervalsList.get(i);
            if(nextInterval[0]<=right){
                right = Math.max(right, nextInterval[1]);
            }else{
                ret.add(new int [] {left, right});
                left = nextInterval[0];
                right = nextInterval[1];
            }
        }
        ret.add(new int [] {left, right});

        int[][] ints1 = ret.toArray(new int[][]{});
        return  ints1;
    }

    public static void main1(String[] args) {
        int [][] intervals = new int[10][10];
        for(int i=0; i< 10; i++){
            for(int j=0; j< 10; j++){
                intervals[i][j] = (i+1)*(j+1);
            }
        }
        new Ranges().merge(intervals);
    }

    /**
     * You are given an array of non-overlapping intervals intervals where intervals[i] = [starti, endi]
     * represent the start and the end of the ith interval and intervals is sorted in ascending order by starti.
     * You are also given an interval newInterval = [start, end] that represents the start and end of another interval.
     *
     * Insert newInterval into intervals such that intervals is still sorted in ascending order by starti
     * and intervals still does not have any overlapping intervals (merge overlapping intervals if necessary).
     *
     * Return intervals after the insertion.
     */
    public int[][] insertBadTiming(int[][] intervals, int[] newInterval) {
        List<Integer> xCordinates = new ArrayList<>();
        List<int[]> ret = new ArrayList<>();
        for (int[] interval : intervals) {
            xCordinates.add(interval[0]);
        }
        int t1 = newInterval[0];
        int t2 = newInterval[1];

        int[] mergedInterval = new int[2];
        int mergedIntervalStart, mergedIntervalEnd;
        int i = Collections.binarySearch(xCordinates, t1);
        //find the index of the over lapping intervals and merge

        if(i>=0){//matches
           mergedInterval[0] = t1;
           mergedIntervalStart = i;
        }else{
            i = ~i;
            //check if previous interval contains t1
            if(i>0 && t1 <= intervals[i-1][1]){
                mergedInterval[0] = intervals[i-1][0];
                mergedIntervalStart = i-1;
            }else{
                mergedInterval[0] = t1;
                mergedIntervalStart = i;
            }
        }
        //now merge the i-th interval
        int j = Collections.binarySearch(xCordinates, t2);
        if(j>=0){//matches
            mergedInterval[1] = intervals[j][1];
            mergedIntervalEnd = j;
        }else{
            j = ~j;
            //check if previous interval contains t2
            if(j>0 && t2 <= intervals[j-1][1]){
                mergedInterval[1] = intervals[j-1][1];
                mergedIntervalEnd = j-1;
            }else{
                mergedInterval[1] = t2;
                mergedIntervalEnd = j-1;
            }
        }

        for(int k=0; k<mergedIntervalStart; ++k){
            ret.add(intervals[k]);
        }
        ret.add(mergedInterval);
        for(int k=mergedIntervalEnd+1; k<intervals.length; ++k){
            ret.add(intervals[k]);
        }
        return ret.toArray(new int[][]{});
    }

    public int[][] insert(int[][] intervals, int[] newInterval) {
        int i=0;

        List<int[]> ret = new ArrayList<>();
        while(i<intervals.length && intervals[i][1] < newInterval[0]){ //intervals[i] finishes before newInterval start
            ret.add(intervals[i]);
            i++;
        }
        int[] mergedInterval = newInterval;

        while(i<intervals.length && intervals[i][0]<= mergedInterval[1]){
            mergedInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            mergedInterval[1] = Math.max(mergedInterval[1], intervals[i][1]);
            ++i;
        }
        ret.add(mergedInterval);
        while(i<intervals.length){
            ret.add(intervals[i]);
            ++i;
        }

        return ret.toArray(new int[][]{});
    }

    public static void main(String[] args) {
        int[][] insert = new Ranges().insert(new int[][]{{1, 3}, {6, 9}}, new int[]{2, 5});
        System.out.println(Arrays.stream(insert).toArray());
        int[][] insert1 = new Ranges().insert(new int[][]{{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}}, new int[]{4, 8});
        System.out.println(Arrays.stream(insert1).toArray());
        int[][] insert2 = new Ranges().insert(new int[][]{{}, {5, 7}}, new int[]{2, 5});
        System.out.println(Arrays.stream(insert2).toArray());
    }
}
