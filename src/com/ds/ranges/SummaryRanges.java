package com.ds.ranges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a sorted integer array without duplicates, return the summary of its ranges.
 *
 * Example 1:
 *
 * Input: [0,1,2,4,5,7]
 * Output: ["0->2","4->5","7"]
 * Explanation: 0,1,2 form a continuous range; 4,5 form a continuous range.
 * Example 2:
 *
 * Input: [0,2,3,4,6,8,9]
 * Output: ["0","2->4","6","8->9"]
 * Explanation: 2,3,4 form a continuous range; 8,9 form a continuous range.
 */
public class SummaryRanges {

    public static void main(String[] args) {
        SummaryRanges summaryRanges = new SummaryRanges();
//        System.out.println(summaryRanges.summaryRanges(new int[]{0,1,2,4,5,7}));
//        System.out.println(summaryRanges.summaryRanges(new int[]{0,2,3,4,6,8,9}));
        System.out.println(summaryRanges.findMissingRanges(new int[]{0, 1, 3, 50, 75, 99, 100}, -1, 100));
    }

    public List<String> summaryRanges(int[] nums) {

        int prev = nums[0];
        int start = prev;
        StringBuilder range = new StringBuilder().append(prev);
        List<String> ret = new ArrayList();

        for(int i=1; i< nums.length; ++i) {
            if (nums[i] == prev+1) {
                prev++;
                continue;
            }
            //complete the prev range
            if (start != prev) {
                range.append("->").append(prev);
            }
            ret.add(range.toString());
            //start the new range
            range.setLength(0);
            start = prev = nums[i];
            range.append(prev);
        }

        if (start != prev) {
            range.append("->").append(prev);
        }

        ret.add(range.toString());
        return ret;
    }


    /**
     * Given a sorted integer array nums, where the range of elements are in the inclusive range [lower, upper], return its missing ranges.
     * Input: nums = [0, 1, 3, 50, 75], lower = 0 and upper = 99,
     * Output: ["2", "4->49", "51->74", "76->99"]
     */

    public List<String> findMissingRanges(int [] nums, int lower, int upper) {

        List<String> ret = new ArrayList<>();
        int nextExpected = lower;
        for (int i=0; i<nums.length; ++i) {
            if(nextExpected < nums[i]) {
                ret.add(addRange(nextExpected, nums[i]-1));
                nextExpected = nums[i] +1;
            }else{ //nextExpected == nums[i]
                ++nextExpected;
            }
        }
        if (nextExpected <= upper) {
            ret.add(addRange(nextExpected, upper));
        }
        return ret;
    }

    private String addRange(int nextExpected, int i) {
        if (nextExpected == i) {
            return Integer.toString(i);
        }else{
            return nextExpected+"-"+i;
        }
    }
}
