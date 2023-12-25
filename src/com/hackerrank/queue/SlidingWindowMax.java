package com.hackerrank.queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * You are given an array of integers nums, there is a sliding window of size k which is moving from the very left of the array to the very right.
 * You can only see the k numbers in the window. Each time the sliding window moves right by one position.
 *
 * Return the max sliding window.
 */

public class SlidingWindowMax {
    Deque<Integer> dq;
    public int[] maxSlidingWindow(int[] nums, int k) {
        dq = new LinkedList<>();
        int [] ret = new int[nums.length -k + 1];
        int i=0;
        for(i=0; i<k; ++i){
            add(nums[i]);
        }
        int j =0;
        ret[j++] = dq.peekFirst();
        for(; i<nums.length; ++i) {
            if(dq.peekFirst().equals(nums[i-k])){
                dq.removeFirst();
            }
            add(nums[i]);
            ret[j++] = dq.peekFirst();
        }

        return ret;
    }

    public void add(int a){
        while(!dq.isEmpty() && dq.peekLast() < a){
            dq.removeLast();
        }
        dq.add(a);
    }

}
