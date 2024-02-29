package com.hackerrank.strings;

import java.util.*;

/**
 * https://leetcode.com/problems/jump-game/
 * Given an array of non-negative integers, you are initially positioned at the first index of the array.
 *
 * Each element in the array represents your maximum jump length at that position.
 *
 * Your goal is to reach the last index in the minimum number of jumps.
 * Input: nums = [2,3,1,1,4]
 * Output: true
 * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
 */
public class MInJumpsLadder {

    public static int jump(int[] nums) {
        if(nums.length == 1) {
            return 1;
        }
        int max = nums[0];
        int ladders = 1;
        int currLadderEnd = nums[0];
        for(int i=0; i<nums.length; ++i){
            int newLadder = i+ nums[i]; // height that you can reach from the ladder
            if(max < newLadder) max = newLadder;
            //when you reach the end of current ladder and haven't reach the end; take the new ladder that is tallest seen so far.
            if(i == currLadderEnd && i< nums.length -1){
                ++ladders;
                currLadderEnd=max; //set the end of current ladder to the tallest ladder found found so far.
            }
        }
        return ladders;
    }

    public static void main(String[] args) {
//        System.out.println(jump2(new int[] {2,3,1,1,4}) == 2);
//        System.out.println(jump2(new int[] {3,2,1,0,4}) == -1);
        //System.out.println(jump2(new int[] {3,1,2,1,0}) == -1);
//          canReach(new int[]{3, 0, 2, 1, 2}, 2);
//        System.out.println(jump2(new int[] {1,1,1,1}) == 3);
        System.out.println(maxScore(new int[]{2,3,6,1,9,2}, 5) == 13);
        System.out.println(maxScore(new int[]{2,4,6,8}, 3) == 20);
    }

    /**
     * Follow the steps below to solve the problem:
     *
     * Initialize the variables maxReach, step, and jump to keep track of the maximum reachable index, the remaining steps at the current position, and the number of jumps taken, respectively.
     * Traverse the array and update the maximum reachable index based on the sum of the current index and its corresponding array value.
     * This helps determine how far the current jump can take us.
     * If the remaining steps for the current jump are exhausted, a new jump is initiated.
     * If the current index surpasses the maximum reachable index, indicating no further progress, -1 is returned meaning no solution is found.
     * @param nums
     * @return
     */
    /**
     * Jump game ii
     * Greedy approach.
     * As we traverse the array, keep track of max reachable index.
     * @param nums
     * @return
     * https://www.youtube.com/watch?v=9kyHYVxL4fw
     * 3,2,1,0,4 ans is -1
     */
    public static int jump2(int[] nums) {
        int jumps = 0;
        int maxReach = 0; //maximum index that is reachable so far
        int maxReachFromPrevPosition=0;

        if(nums.length == 1) return jumps;
        //backtracking
        int nextOptimalJump=0;
        List<Integer> optimalSteps = new ArrayList<>();
        //optimalSteps.add(nextPossibleJump);

        for(int i=0; i< nums.length; ++i){
            if(maxReach <= nums[i] + i) {
                maxReach = nums[i] + i; //we are at i-th index, from there we can go upto num[i] + i index
                nextOptimalJump = i;
            }

            if (i >= maxReach) return -1; // from i-th step we should be able to get at least one step further.
            if(i == maxReachFromPrevPosition) {//we have to jump from i, somewhere between i to maxreach that yields optimal jumps.
                // Optimal jump index is the one where maxReach is recorded so far.
                // But when we reach maxreach, we need to take another jump, so we set last idx = maxreach
                maxReachFromPrevPosition = maxReach;
                ++jumps;
                optimalSteps.add(nextOptimalJump);

                if (maxReach >= nums.length - 1) {
                    break;
                }
            }
        }
        System.out.println(optimalSteps);
        return jumps;
    }


    /**
     * Jump game iii
     * Given an array of non-negative integers arr, you are initially positioned at start index of the array.
     * When you are at index i, you can jump to i + arr[i] or i - arr[i], check if you can reach any index with value 0.
     *
     * Notice that you can not jump outside of the array at any time.
     */

    public static boolean canReach(int[] arr, int start) {
        boolean [] visited = new boolean[arr.length];
        Arrays.fill(visited, false);
        List<Integer> nodes = new LinkedList<>();
        nodes.add(start);
        visited[start] = true;
        boolean found = false;
        while(!nodes.isEmpty()){
            Integer idx = nodes.remove(0);
            if(arr[idx] == 0){
                found = true;
                break;
            }
            int right = idx + arr[idx];
            if(right < arr.length && !visited[right]){
                nodes.add(right);
                visited[right] = true;
            }
            int left = idx - arr[idx];
            if(left >=0 && !visited[left]){
                nodes.add(left);
                visited[left] = true;
            }
        }
        return found;
        //[3,0,2,1,2]
        //2
    }

    public boolean canReachREcursive(int[] arr, int start) {
        boolean [] visited = new boolean[arr.length];
        return visit(arr, start, visited);

    }
    public static boolean visit(int[] arr, int i, boolean [] visited) {
        visited[i] = true;
        if(arr[i] == 0) {
            return true;
        }

        boolean found = false;
        if(arr[i] + i < arr.length && !visited[arr[i] + i]){
            found = visit(arr, arr[i] + i, visited);
        }
        if(found == true) return true;
        if(i - arr[i] >=0 && !visited[i - arr[i]]){
            found = visit(arr, i- arr[i], visited);
        }
        return found;
    }
    /**
     * Jump game vii
     * You are given a 0-indexed binary string s and two integers minJump and maxJump.
     * In the beginning, you are standing at index 0, which is equal to '0'.
     * You can move from index i to index j if the following conditions are fulfilled:
     *
     * i + minJump <= j <= min(i + maxJump, s.length - 1), and
     * s[j] == '0'.
     * Return true if you can reach index s.length - 1 in s, or false otherwise.
     * DP  and sliding window.
     * At the i-th index, the previous elements j whose jump [j+min, j+max] contains i form a sliding window.
     * This sliding window in terms of i is [i-max, i-min]
     *
     */
    public boolean canReach(String s, int minJump, int maxJump) {
        boolean [] dp = new boolean[s.length()]; //dp[i] == true if s[i] reachable
        dp[0] = true;
        int pre = 0; //pre means the number of previous position that we can jump from.
        for(int i=1; i<s.length(); ++i){
            if(i-minJump >=0){//i-min comes in
                if(dp[i-minJump]){
                    ++pre;
                }
            }
            if(i-maxJump -1 >=0){//i-max-1 goes out
                if(dp[i-maxJump-1]){
                    --pre;
                }
            }
            dp[i] = pre > 0 && s.charAt(i) == '0';
        }
        return dp[s.length()-1];
    }

    /**
     * You are given a 0-indexed integer array nums and a positive integer x.
     *
     * You are initially at position 0 in the array and you can visit other positions according to the following rules:
     *
     * If you are currently in position i, then you can move to any position j such that i < j.
     * For each position i that you visit, you get a score of nums[i].
     * If you move from a position i to a position j and the parities of nums[i] and nums[j] differ, then you lose a score of x.
     * Return the maximum total score you can get.
     *
     * Note that initially you have nums[0] points.
     */
    public static long maxScore(int[] nums, int x) {
        //Note that we have to take first number.
        //For test case [2, 99], 5  ans = 2 + (99-5).
        //So if the first element is even, we pretend previous odd value is nums[0]- x. So in this case, next odd value becomes 99 + (2-5)
        long even = nums[0] - ( (nums[0] % 2 == 0 ) ? 0 : x); //records max score in [0, i] which ends at even number
        long odd  = nums[0] - ( (nums[0] % 2 == 0 ) ? x : 0);

        for(int i=1; i<nums.length; ++i){
            if(nums[i] %2 == 1){
                /**
                 * When we see an odd number, we have two choices:
                 *
                 * take score from the previous odd number
                 * take score from the previous even number, minus x.
                 */
                odd = nums[i] + Math.max(odd, even-x);
            }else{
                /**
                 * When we see an even number, we have two choices:
                 *
                 * take score from the previous even number
                 * take score from the previous odd number, minus x.
                 */
                even =  nums[i] + Math.max(even, odd-x);
            }
        }

        return Math.max(even, odd);
    }

}
