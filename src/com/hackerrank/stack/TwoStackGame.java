package com.hackerrank.stack;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Rajesh on 11/16/2017.
 */
public class TwoStackGame {

    //Given two stacks, find maximum elements you can remove from stacks so that sum does not exceed x.
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int testcases = in.nextInt();
        for(int a0 = 0; a0 < testcases; a0++){
            int n = in.nextInt();
            int m = in.nextInt();
            int x = in.nextInt();
            int [] a = new int [n];
            for(int a_i=0; a_i < n; a_i++){
                a[a_i]=in.nextInt();
            }
            int[] b = new int[m];
            for(int b_i=0; b_i < m; b_i++){
                b[b_i]=in.nextInt();
            }
            int ccount = 0;
            int maxCount = 0;
            int sum = 0;
            Stack<Integer> temp = new Stack<>();
            // we need to find out how many we take from each stack.
            //first take all from first stack upto the sum reaches x
            for(int i=0; i<n; ++i){
                if(sum + a[i] <=x){
                    ++ccount;
                    ++maxCount;
                    temp.push(a[i]);
                    sum += a[i];
                }else break;
            }
            // now start adding from the second stack and if sum exceeds x, remove from the elements
            // that were added from the first stack.
            int j=0;
            while(j<m){
                if(sum + b[j] <=x){
                    ++ccount;
                    maxCount = Math.max(maxCount, ccount);
                    sum += b[j];
                    ++j;
                }else{
                    if(temp.empty()) break;
                    sum -= temp.pop(); //remove the element taken from first stack and decrement the count
                    --ccount;
                }
            }
            System.out.println(maxCount);
        }

    }
}
