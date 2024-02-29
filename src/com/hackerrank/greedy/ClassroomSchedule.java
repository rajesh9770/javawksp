package com.hackerrank.greedy;

import java.sql.Timestamp;
import java.util.*;

/**
 * Scheduling
 * Created by Rajesh on 6/4/2018.
 * Interval partitioning.
 * Lecture j starts at sj and finishes at fj.
 * Goal: find minimum number of classrooms to schedule all lectures
 * so that no two occur at the same time in the same room
 */
public class ClassroomSchedule
{
    public static class JobSchedule implements Comparable<JobSchedule>{

        Timestamp start, end;

        public JobSchedule(Timestamp start, Timestamp end){
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(JobSchedule o) {
            return start.compareTo(o.start);
        }
    }

    public static int getMaxClassroomNeeded(List<JobSchedule> schedules){

        Collections.sort(schedules);

        PriorityQueue<Timestamp> finishingTimes = new PriorityQueue<>();
        int maxLectureRooms = 0;
        for(JobSchedule schedule: schedules){
            if(finishingTimes.isEmpty()){
                finishingTimes.add(schedule.end);
                ++maxLectureRooms;
            }else{
                Timestamp earlierAvailClassroom = finishingTimes.peek();
                if(earlierAvailClassroom.compareTo(schedule.start)<0){//this classroom is now avail
                    finishingTimes.remove(); //remove it; remove one at a time
                    finishingTimes.add(schedule.end); //reuse it
                }else{
                    finishingTimes.add(schedule.end); //need to use new classroom, since all classroom in Priority queue end at later time
                    ++maxLectureRooms;
                }
            }
        }
        return maxLectureRooms;
    }

    /**
     * Returns numbers of classroom in use at CurrTime
     * @param schedules
     * @param currTime
     * @return
     */
    public static int getClassroomInUse(List<JobSchedule> schedules, Timestamp currTime){

        Collections.sort(schedules);
        int numOfClassroomInUse = 0;
        int i = Collections.binarySearch(schedules, new JobSchedule(currTime, new Timestamp(Long.MAX_VALUE)));
        if (i < 0) i = ~i;  //index where jobSchedule starting at currTime would have been inserted.
        while(i>0){
            JobSchedule jobSchedule = schedules.get(i--);
            if(jobSchedule.end.compareTo(currTime)>0) ++numOfClassroomInUse;
        }
        return numOfClassroomInUse;
    }

    public static void main(String[] args) {
        //System.out.println(new ClassroomSchedule().eraseOverlapIntervals(new int[][]{{0,10}, {1,2},{2,3},{3,4}}));
    }

    /**
     * Given an array of intervals intervals where intervals[i] = [starti, endi],
     * return the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.
     * Approach is greddy - same as scheduling as many non overlapping jobs as possible - sort by finishing times and take non overlapping intervals
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        //Sorting intervals in ascending order of finishing times O(nlogn),
        // then traverse intervals array to get the maximum number of non-overlapping intervals is O(n).
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        int nonOverLappingIntervals = 1;
        int end = intervals[0][1];
        for(int i=1; i< intervals.length; ++i){
            if(intervals[i][0]>=end){
                end = intervals[i][1];
                ++nonOverLappingIntervals;
            }
        }
        return intervals.length - nonOverLappingIntervals;
    }
}
