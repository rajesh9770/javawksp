package com.problem;

/**
 * Given a char array representing tasks CPU need to do. It contains capital letters A to Z where different letters represent different tasks.
 * Tasks could be done without original order.
 * Each task could be done in one interval. For each interval, CPU could finish one task or just be idle.
 *
 * However, there is a non-negative cooling interval n that means between two same tasks,
 * there must be at least n intervals that CPU are doing different tasks or just be idle.
 *
 * You need to return the least number of intervals the CPU will take to finish all the given tasks.
 */
public class CPUCoolOff {

    public static int leastInterval(char[] tasks, int n) {
        int[] map = new int[26];
        int max = 0; //get the max task - a task that appeared the most
        for(char c: tasks) {
            max = Math.max(max, ++map[c-'A']);
        }
        /*****
         * For (AAAABBBCCC) and n=2, schedule A then others
         * A--
         * A--
         * A--
         */
        max--; //decrement max by one and conside max * (n+1) matrix to place the jobs. Jobs that come with max number take first, second, ... colums and the last one can be placed in the
        //max+1 row.
        int idealSlots = max * (n+1);
        for(int i=0; i<26; ++i) {
            idealSlots -= Math.min(map[i], max);
        }
        return (idealSlots >0) ? idealSlots + tasks.length : tasks.length;
    }


    public static void main(String[] args) {
        System.out.println(leastInterval(new char[] {'A','A','A','B','B','B'}, 2) ==  8);
        System.out.println(leastInterval(new char[] {'A','A','A','A','B','B','B', 'C','C','C'}, 2) ==  10);
    }
}
