package com.hackerrank.dynamicprog;

import java.util.Scanner;

/**
 * Created by Rajesh on 11/9/2017.
 */
//https://pastebin.com/1R543whN
public class FairCut {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int k = scan.nextInt();
        int [] a = new int[n];
        int [][] dp = new int [n+1][k+1];

        dp[0][0] = 0;
        for(int i=0; i<n+1; ++i){
            dp[i][0] = Integer.MAX_VALUE; // Jim gets all, Jill nothing
        }
        for(int i=1; i<n+1; ++i){
            for(int j=1; j<=i; ++j){

            }
        }

    }
}
