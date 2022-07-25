package com.hackerrank.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given an array A of integers, return the number of non-empty continuous subarrays that satisfy the following condition:
 *
 * The leftmost element of the subarray is not larger than other elements in the subarray.
 *
 * Example 1:
 *
 * Input: [1,4,2,5,3]
 * Output: 11
 * Explanation: There are 11 valid subarrays: [1],[4],[2],[5],[3],[1,4],[2,5],[1,4,2],[2,5,3],[1,4,2,5],[1,4,2,5,3].
 */
public class ValidSubArray {

    public static void main(String[] args) {
        System.out.println(validSubarrays(new int[] {1,4,2,5,3}));//11
        System.out.println(validSubarrays(new int[] {3,2,1}));//3
        System.out.println(validSubarrays(new int[] {2,2,2}));//6
    }
    public static int validSubarrays(int[] nums) {
        int res = 0;//nums.length;
        if(nums.length <= 1) {
            return res;
        }

        Deque<Integer> stack = new ArrayDeque<>();

        for(int i = 0; i < nums.length; i++) {
            int num = nums[i];
            while(!stack.isEmpty() && num < stack.peek()) {//stack is increasing - not strictly
                stack.pop();
            }
            stack.push(num); // when we push a number on stack, all elements bigger than number are poped out.
            res += stack.size();//stack size give # of valid subarrary ending in num.
            //stack.push(num);
        }
        return res;
    }
}
