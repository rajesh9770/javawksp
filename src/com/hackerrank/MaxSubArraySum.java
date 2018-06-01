package com.hackerrank;

import java.util.Scanner;
import java.util.TreeSet;

/**
 * Given an n-element array of integers and an integer m , determine the maximum value of the sum of any of its subarrays modulo m
 */
public class MaxSubArraySum {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int testcases = in.nextInt();
        while(testcases-->0){
            int arraySize = in.nextInt();
            long mod = in.nextLong();
            long[] arr = new long[arraySize];
            for(int i=0; i<arraySize; ++i){
                arr[i] = in.nextLong() %mod;
            }
            System.out.println(solve(arr, arraySize, mod));
        }
        in.close();

    }

    private static long solve(long[] arr, int arraySize, long mod) {
        TreeSet<Long> set = new TreeSet<>();
        long max = arr[0]%mod;
        if(max<0) max = (max + mod) %mod;
        long partialSum = 0;
        for(long i: arr){
            partialSum += i;
            partialSum %= mod;
            set.add(partialSum);
            max = Math.max(max, partialSum);
            Long ceiling = set.higher(partialSum);
            if(ceiling != null){
                max = Math.max(max, (partialSum - ceiling + mod) % mod ); 
            }
        }
        return max;
    }

}
