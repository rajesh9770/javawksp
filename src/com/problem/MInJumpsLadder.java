package com.problem;

/**
 * Given an array of non-negative integers, you are initially positioned at the first index of the array.
 *
 * Each element in the array represents your maximum jump length at that position.
 *
 * Your goal is to reach the last index in the minimum number of jumps.
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
            int newLadder = i+ nums[i]; //hight that you can reach from the ladder
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
        System.out.println(jump(new int[] {1,1,1,1}) == 3);
    }

}
