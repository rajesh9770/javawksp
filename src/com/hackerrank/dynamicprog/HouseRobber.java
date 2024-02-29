package com.hackerrank.dynamicprog;


import com.functional.java8lambdas.Track;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public int rob(int[] nums) {
        if(nums == null || nums.length == 0) return 0;
        if(nums.length == 1) return nums[0];

        int ret[] = new int[nums.length];
        ret[0] = nums[0];
        ret[1] = Math.max(nums[0], nums[1]);
        for(int i=2; i< nums.length; ++i){
            ret[i] = Math.max(
                    nums[i] + ret[i-2], //you take nums[i]
                    ret[i-1] //you do not take nums[i]
                    );
        }
        return ret[nums.length-1];
    }

    public static void main1(String[] args) {
        System.out.println(new HouseRobber().rob(new int[]{1,2,3,1}) == 4);
        System.out.println(new HouseRobber().rob(new int[]{2,7,9,3,1}) == 12);
    }


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
     * How do we backtrack?
     * @param weights
     * @param W
     * @return
     */
    public static long UnboundedKnapsack(int [] weights, int W) {
        Arrays.sort(weights);// O(n*ln(n))
        long [] wg = new long [W+1]; //wg[i] == max weight but not exceeding i and considering all given weights.
        wg[0] = 0;
        wg[1] = weights[0] ==1 ? 1: 0;
        for(int i = 2; i<=W; ++i){ //O(n*W)
            wg[i] = 0;
            for(int j=0; j<weights.length; ++j){
                if(weights[j] <= i) wg[i] = Math.max(wg[i], weights[j] + wg[i-weights[j]]);
                else break;
            }
        }
        return wg[W];
    }

    /**
     * https://leetcode.com/problems/combination-sum/
     * Given an array of distinct integers candidates and a target integer target,
     * return a list of all unique combinations of candidates where the chosen numbers sum to target.
     * You may return the combinations in any order.
     *
     * The same number may be chosen from candidates an unlimited number of times.
     * Two combinations are unique if the frequency of at least one of the chosen numbers is different.
     *
     * The test cases are generated such that the number of unique combinations that sum up to target is less than 150 combinations for the given input.
     */

    public List<List<Integer>> combinationSum(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack(list, new ArrayList<>(), nums, target, 0);
        return list;
    }

    private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums, int remain, int start){
        if(remain == 0) list.add(new ArrayList<>(tempList));
        else{
            for(int i = start;  i < nums.length && (nums[i] <= remain); i++){
                tempList.add(nums[i]);
                backtrack(list, tempList, nums, remain - nums[i], i); // not i + 1 because we can reuse same elements. In case of bounded knapsack, this should be i+1.
                tempList.remove(tempList.size() - 1);
            }
        }
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
            dp[i][0] =   weights[0] <= i ? weights[0] : 0;  //in case of unbounded dp[i][0] = (i/weights[0]) * (weights[0])
        }

        for(int i=1; i<W+1; ++i){
            for(int j=1; j<weights.length; ++j){
                if(i>=weights[j]){
                    dp[i][j] = Math.max(dp[i][j-1], weights[j] + dp[i-weights[j]][j-1]); ////in case of unbounded dp[i][j] = Math.max(dp[i][j-1], weights[j] + dp[i-weights[j]][j])
                }else{//can't take weight[j]
                    dp[i][j] = dp[i][j-1];
                }
            }
        }
        return dp[W][weights.length-1];
    }

    /**
     * In the 0–1 Knapsack problem, we are given a set of items, each with a weight and a value,
     * and we need to determine the number of each item to include in a collection
     * so that the total weight is less than or equal to a given limit and the total value is as large as possible.
     */
    public static int knapsack(int[] v, int[] w, int W){
        //no sorting on w or v required
        int len = w.length;
        int [][] dp = new int[W+1][len];
        //dp[i][j] == max Value that can be obtained with max allowed weight i and with first j weights
        for(int j=0;j<len; ++j) dp[0][j] = 0; //if total weight allowed is 0, then only one solution exists, i.e. take no weights and total weight dp[0][w] is 0.
        for(int i=0; i<W+1; ++i ){
            //with just one weight w[0], we can take only take w[0] weight if allowed weight i is greater or equal to w[0].
            dp[i][0] =   w[0] <=i ? v[0] : 0;
        }
        for(int i=1; i<W+1; ++i) {
            for (int j = 1; j < len; ++j) {
                if(w[j]<=i){//we can include w[j]
                    dp[i][j] = Math.max(dp[i][j-1], v[j] + dp[i-w[j]][j-1]);
                }else{
                    dp[i][j] = dp[i][j-1];
                }
            }
        }
        return dp[W][len-1];
    }

    /**
     * Given a set of positive integers and an integer k, check if there is any non-empty subset that sums to k.
     * Difference between subsetSum and knapsack is that in knapsack sum <= k, in subsetSum we need sum = k
     * @param A
     */
    public static boolean subsetSum(int[] A, int k){
        int len = A.length;
        //dp[i][j] == true if there is a subset that sums to i and with first j elements
        boolean [][] dp = new boolean[k+1][len];
        for(int j=0;j<len; ++j) dp[0][j] = true; //if total sum allowed is 0, then only one solution exists, i.e. take no weights.
        for(int i=0; i<k+1; ++i ){
            //with just one number w[0], we can take only take w[0] weight if allowed sum i is equal to w[0].
            dp[i][0] =   A[0] ==i ? true : false;
        }
        for(int i=1; i<k+1; ++i){
            for(int j=1; j<len; ++j){
                if(A[j]<=i){
                    dp[i][j] = dp[i][j-1] || dp[i-A[j]][j-1];
                }else{
                    dp[i][j] = dp[i][j-1];
                }
            }
        }
        return dp[k][len-1];
    }

    /**
     * Given a set of positive integers and an integer k, count # of non-empty subsets that sums to k
     * @param A
     * @param k
     * @return
     */
    public static long subsetSumCounts(int[] A, int k){
        int len = A.length;
        //dp[i][j] == # of subsets that sums to i and with first j elements
        long [][] dp = new long[k+1][len];
        for(int j=0;j<len; ++j) dp[0][j] = 1; //if total sum allowed is 0, then only one solution exists, i.e. take no weights.
        for(int i=1; i<k+1; ++i ){//<- start from 1
            //with just one number w[0], we can take only take w[0] weight if allowed sum i is equal to w[0].
            dp[i][0] =   A[0] ==i ? 1 : 0;
        }
        for(int i=1; i<k+1; ++i){
            for(int j=1; j<len; ++j){
                if(A[j]<=i){
                    dp[i][j] = dp[i][j-1] + dp[i-A[j]][j-1];
                }else{
                    dp[i][j] = dp[i][j-1];
                }
            }
        }
        return dp[k][len-1];
    }

    /**
     * This is same as counting # of subsets with sum i, but with 1D DP.
     * @param A
     * @param k
     * @return
     */
    public static int countPartitions1D(int[] A, int k) {
        long mod = (long)1e9 + 7, total = 0, res = 1;
        //dp[i] == # of subsets that sums to i and with all elements from A. This gives the last column of 2D dp of the method countPartitions below.
        long dp[] = new long[k];
        dp[0] = 1;
        for (int a : A) {
            for (int i = k - 1 - a; i >= 0; --i)
                dp[i + a] = (dp[i + a] + dp[i]) % mod;
            res = res * 2 % mod;
            total += a;
        }
        for (int i = 0; i < k; ++i) {
            res -= total - i < k ? dp[i] : dp[i] * 2;
        }
        return (int)((res % mod + mod) % mod);
    }

    public static int countPartitions(int[] A, int k) {

        long mod = (long)1e9 + 7, res = 1, total = 0;
        int len = A.length;
        for(int i=0; i<len; ++i){
            res = (res * 2) % mod;
            total += A[i];
        }
        //Gr1 + Gr2 >=2*k => Total >=2*k
        if(total < 2 * k) return 0;

        //dp[i][j] == # of subsets that sums to i and with first j elements
        long [][] dp = new long[k+1][len];
        for(int j=0;j<len; ++j) dp[0][j] = 1; //if total sum allowed is 0, then only one solution exists, i.e. take no weights.
        for(int i=1; i<k+1; ++i ){//<- start from 1
            //with just one number w[0], we can take only take w[0] weight if allowed sum i is equal to w[0].
            dp[i][0] =   A[0] ==i ? 1 : 0;
        }
        for(int i=1; i<k+1; ++i){
            for(int j=1; j<len; ++j){
                if(A[j]<=i){
                    dp[i][j] = (dp[i][j-1] + dp[i-A[j]][j-1]) % mod;
                }else{
                    dp[i][j] = dp[i][j-1];
                }
            }
        }

        for (int i = 0; i < k; ++i) {
            res -= dp[i][len-1] * 2;
        }
        return (int)((res % mod + mod) % mod);
    }

    public static void main(String[] args){
        int[] A =  {1,2,3,4};

        int k = 4;
        System.out.println(countPartitions1D(A, k));
    }
    public static void main2(String[] args)
    {
        // input: a set of items, each with a weight and a value
        int[] v = { 20, 5, 10, 40, 15, 25 };
        int[] w = { 1, 2, 3, 8, 7, 4 };

        // knapsack capacity
        int W = 10;

        System.out.println("Knapsack value is " + knapsack(v, w, W));//ans =60

        int[] A = { 7, 3, 2, 5, 8 };
        int k = 18;

        if (subsetSum(A, k)) {
            System.out.println("Subsequence with the given sum exists");
        }
        else {
            System.out.println("Subsequence with the given sum does not exist");
        }
        System.out.println(subsetSumCounts(A, 10));
    }
}
