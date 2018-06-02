package com.hackerrank.recursion;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rajesh on 6/1/2018.
 */
public class StairCase {

    private static final Scanner scanner = new Scanner(System.in);


    private static Map<Integer, Long> cache = new HashMap<>();


    private static long count(int remainingSteps){
        if(remainingSteps == 0) return 0;
        if(remainingSteps == 1) return 1;
        if(remainingSteps == 2) return 2;
        if(remainingSteps == 3) return 4;
        if(cache.get(remainingSteps) != null) {
            return cache.get(remainingSteps) ;
        }
        long count = 0; //since remaining steps >0, we know we will take at least one step
        count += count(remainingSteps-1); //take one step;
        if(remainingSteps>=2) {  count += count(remainingSteps-2);} //take two step;
        if(remainingSteps>=3) {  count += count(remainingSteps-3);} //take three step;

        cache.put(remainingSteps, count);
        return count;
    }

    private static long countNoRecursion(int remainingSteps){
        long a1=1, a2=2, a3=4;
        for(int i=4; i<=remainingSteps; ++i){
            long temp = a1 + a2+ a3;
            a1=a2;
            a2=a3;
            a3=temp;
        }
        return a3;
    }

    public static void main(String[] args) {
        int s = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int sItr = 0; sItr < s; sItr++) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            System.out.println(count(n));
        }

        scanner.close();
    }
}
