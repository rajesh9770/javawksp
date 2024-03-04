package com.numbers;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
 *
 * You must write an algorithm that runs in O(n) time.
 */
public class LongestConsecutiveSequence {
    public int longestConsecutive(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        Iterator<Integer> it = set.iterator();
        int ans = 0;
        while(it.hasNext()){
            Integer next = it.next();
            if(!set.contains(next-1)) {
                int i = next + 1;
                for (; set.contains(i); ++i) ;
                ans = Math.max(ans, i - next);
            }
        }
        return ans;
    }
}
