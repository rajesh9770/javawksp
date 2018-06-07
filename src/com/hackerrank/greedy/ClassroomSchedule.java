package com.hackerrank.greedy;

import javafx.util.Pair;


import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
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

        public JobSchedule(Timestamp start){
            this.start = start;
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
                finishingTimes.add(schedule.start);
                ++maxLectureRooms;
            }else{
                Timestamp earlierAvailClassroom = finishingTimes.peek();
                if(earlierAvailClassroom.compareTo(schedule.start)<0){//this classroom is now avail
                    finishingTimes.remove(); //remove it
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
        int i = Collections.binarySearch(schedules, new JobSchedule(currTime));
        if (i < 0) i = ~i;  //index where jobSchedule starting at currTime would have been inserted.
        while(i>0){
            JobSchedule jobSchedule = schedules.get(i--);
            if(jobSchedule.end.compareTo(currTime)>0) ++numOfClassroomInUse;
        }
        return numOfClassroomInUse;
    }

    public static void main(String[] args) {
        for(int i='a'; i<='z'; ++i)
            System.out.println((char)i);
    }
}
