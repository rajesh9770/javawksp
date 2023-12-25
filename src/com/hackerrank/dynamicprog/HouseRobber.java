package com.hackerrank.dynamicprog;


import java.util.Arrays;
import java.util.Scanner;

/**
 *  You are a professional robber planning to rob houses along a street.
 *  Each house has a certain amount of money stashed,
 *  the only constraint stopping you from robbing each of them is that adjacent houses have security systems connected
 *  and it will automatically contact the police if two adjacent houses were broken into on the same night.
 *
 * Given an integer array nums representing the amount of money of each house,
 * return the maximum amount of money you can rob tonight without alerting the police.
 */
public class HouseRobber {



    public static void mainForUnboundedKnapsack(String[] args){
        Scanner in = new Scanner(System.in);
        int testcases = in.nextInt();
        while(testcases-- >0){
            int n = in.nextInt();
            int w = in.nextInt();
            int [] arr = new int[n];
            for(int i=0; i<n; ++i)
                arr[i] = in.nextInt();
            System.out.println(UnboundedKnapsack(arr, w));
        }
        in.close();
    }

    /**
     * Given an array of integers and a target sum, determine the sum nearest to but not exceeding the target that can be created.
     * To create the sum, use any element of your array zero or more times.
     *
     * For example, if  arr = [2,3,4] and your target sum is 10, you might select [2,2,2,2,2], [2,2,3,3] or [3,3,4].
     * In this case, you can arrive at exactly the target.
     * @param weights
     * @param W
     * @return
     */
    public static long UnboundedKnapsack(int [] weights, int W) {
        Arrays.sort(weights);
        long [] wg = new long [W+1];
        wg[0] = 0;
        wg[1] = weights[0] ==1 ? 1: 0;
        for(int i = 2; i<=W; ++i){
            wg[i] = 0;
            for(int j=0; j<weights.length; ++j){
                if(weights[j] <= i) wg[i] = Math.max(wg[i], weights[j] + wg[i-weights[j]]);
                else break;
            }
        }
        return wg[W];
    }

    /**
     * Weights are in ascending order. Bag has capacity W.
     * Only one weight of size weights[i] is available.
     * @param weights
     * @param W
     * @return
     */
    public static long BoundedKnapsack(int W, int [] weights){
        long dp[][] = new long[W+1][weights.length];
        for(int j=0;j<weights.length; ++j) dp[0][j] = 0; //if total weight allowed is 0, then only one solution exists, i.e. take no weights and total weight dp[0][w] is 0.
        for(int i=0; i<W+1; ++i ){
            //with just one weight weight[0], we can take only take weights[0] weight if allowed weight is greater or equal to weights[0].
            dp[i][0] =   weights[0] >=i ? weights[0] : 0;
        }

        for(int i=1; i<W+1; ++i){
            for(int j=1; j<weights.length; ++j){
                if(i>=weights[j]){
                    dp[i][j] = Math.max(dp[i][j-1], weights[j] + dp[i-weights[j]][j-1]);
                }else{//can't take weight[j]
                    dp[i][j] = dp[i][j-1];
                }
            }
        }
        return dp[W][weights.length-1];
    }
}
