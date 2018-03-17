package com.hackerrank;

import java.util.Arrays;
import java.util.Scanner;

/**
 * You are given a list of size , initialized with zeroes. 
 * You have to perform  operations on the list and output the maximum of final values of all the  elements in the list. 
 * For every operation, you are given three integers ,  and  and you have to add value  to all the elements ranging from index  to 
 * (both inclusive).
 * First line will contain two integers  and  separated by a single space.
Next  lines will contain three integers ,  and  separated by a single space.
Numbers in list are numbered from   1 to n .
5 3
1 2 100
2 5 100
3 4 100
Ans: 200
 *
 */
public class StepFunction {

    public StepFunction() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();

        long [] intervals = new long[n+1];
        while(m-->0){
            int a = in.nextInt();
            int b = in.nextInt();
            int sum = in.nextInt();
            intervals[a] += sum;
            if(b+1<=n) intervals[b+1] -=sum;
        }
        long max = 0;
        long runningSum = 0;
        for(int i=1; i<=n; ++i){
            runningSum += intervals[i];
            if(max <runningSum) max = runningSum;
        }
        System.out.println(max);

    }

}
