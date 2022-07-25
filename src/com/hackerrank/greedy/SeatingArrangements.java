package com.hackerrank.greedy;

import java.util.Arrays;

/**
 * There are n guests attending a dinner party, numbered from 1 to n. The ith guest has a height of arr[i-1] inches.
 * The guests will sit down at a circular table which has n seats, numbered from 1 to n in clockwise order around the table.
 * As the host, you will choose how to arrange the guests, one per seat. Note that there are n! possible permutations of seat assignments.
 * Once the guests have sat down, the awkwardness between a pair of guests sitting in adjacent seats is defined as the absolute difference between their two heights.
 * Note that, because the table is circular, seats 1 and n are considered to be adjacent to one another, and that there are therefore n pairs of adjacent guests.
 * The overall awkwardness of the seating arrangement is then defined as the maximum awkwardness of any pair of adjacent guests.
 * Determine the minimum possible overall awkwardness of any seating arrangement.
 */
public class SeatingArrangements {

    public static int minOverallAwkwardness(int[] arr){
        Arrays.sort(arr);
        // arrange in this order
        // 0 2 4 ... N-1 N-2 N-4 ... 5 3 1 //N is odd
        // 0 2 4 ... N-2 N-1 N-3 ... 5 3 1 //N is even
        int max = Math.max(arr[1]-arr[0], arr[2]-arr[0]);
        for(int i=1;i<arr.length-2; ++i){
            max = Math.max(max, arr[i+2]-arr[i]);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(minOverallAwkwardness(new int[]{5,10,6,8}));
        System.out.println(minOverallAwkwardness(new int[]{1, 2, 5, 3, 7}));
    }
}
