package com.ds.ranges;


import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * #1438, 327
 * Given an array of integers nums and an integer limit,
 * return the size of the longest non-empty subarray such that the absolute difference between any two elements of this subarray is less than or equal to limit.
 */
public class LongestSubarrayAbsoluteDiffLimit {

    PriorityQueue<Integer> min = new PriorityQueue<>();
    PriorityQueue<Integer> max = new PriorityQueue<>(Comparator.reverseOrder());

    public int longestSubarray(int[] nums, int limit) {
        int start = 0, end  = 0, ans = 0;
        min.add(nums[end]);
        max.add(nums[end]);

        while(start<=end && end <nums.length){


            if (max.peek()-min.peek() <= limit) {
                ans = Math.max(ans, end-start+1);
                ++end;
                if( end < nums.length) {
                    min.add(nums[end]);
                    max.add(nums[end]);
                }
            }else{
                min.remove(nums[start]);
                max.remove(nums[start]);
                ++start;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(new LongestSubarrayAbsoluteDiffLimit().longestSubarray(new int [] {8,2,4,7}, 4));
    }
}
