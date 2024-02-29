package com.hackerrank.queue;

import java.util.*;

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
        //here i is k
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
        while(!dq.isEmpty() && dq.peekLast() < a){//keep deque decreasing
            dq.removeLast();
        }
        dq.add(a);
    }

    /**
     * Given an array of positive integers nums and a positive integer target,
     * return the minimal length of a subarray whose sum is greater than or equal to target.
     * If there is no such subarray, return 0 instead.
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen(int target, int[] nums) {
        int sum = 0;
        int ret = Integer.MAX_VALUE;
        boolean found = false;
        for(int right=0, left=0; right<nums.length; ++right){
            sum += nums[right];
            if(sum>=target){
                found = true;
                while(left <=right){
                   if(sum - nums[left] < target) break;
                   else{
                       sum -= nums[left];
                       left++;
                   }
                }
//                while(sum>=target){
//                    sum -= nums[left];
//                    left++; //increment later
//                }
//                //decrement and then add
//                sum += nums[--left];
                ret = Math.min(right - left + 1, ret);
            }
        }
        return found ? ret : 0;
    }

    public static void main1(String[] args) {
        System.out.println(new SlidingWindowMax().numberOfSubarrays(new int[]{2,2,2,1,2,2,1,2,2,2 }, 2));
    }

    /**
     * Given an array of integers nums and an integer k.
     * A continuous subarray is called nice if there are k odd numbers on it.
     * @param A
     * @param k
     * @return
     */
    public int numberOfSubarrays(int[] A, int k) {
        //exactly(K) = atMost(K) - atMost(K-1)
        return atMost(A, k) - atMost(A, k - 1);
    }

    public int atMost(int[] A, int k) {
        int res = 0, i = 0, n = A.length;
        int count =0;
        for (int j = 0; j < n; j++) {
            count += A[j] % 2;
            while (count > k){
                count -= A[i++] % 2;
            }
            res += j - i + 1; //count the array ending at j
        }
        return res;
    }

    /**
     * You are given an integer array nums and an integer k.
     * There is a sliding window of size k which is moving from the very left of the array to the very right.
     * You can only see the k numbers in the window. Each time the sliding window moves right by one position.
     *
     * Return the median array for each window in the original array. Answers within 10-5 of the actual value will be accepted.
     * @param nums
     * @param k
     * @return
     */
    public double[] medianSlidingWindow(int[] nums, int k) {
        List<Double> sortedKList = new LinkedList<>();
        for(int i=0; i<k; ++i){
            sortedKList.add((double) nums[i]);
        }

        Collections.sort(sortedKList);
        double [] medians = new double[nums.length-k+1];
        int j=0;
        medians[j++] = k%2 == 0 ?  (sortedKList.get((k/2)-1) + sortedKList.get(k/2))/2.0 : sortedKList.get(k/2);
        for(int i=k; i<nums.length; ++i){
            int i1 = Collections.binarySearch(sortedKList, (double)nums[i - k]);
            sortedKList.remove(i1);//this is linear
            i1 = Collections.binarySearch(sortedKList, (double)nums[i]);
            if(i1 < 0) i1 = ~i1;
            sortedKList.add(i1, (double)nums[i]);
            medians[j++] = k%2 == 0 ?  (sortedKList.get((k/2)-1) + sortedKList.get(k/2))/2.0 : sortedKList.get(k/2);
        }
        return medians;
    }

    public static void main(String[] args) {
        double[] doubles = new SlidingWindowMax().medianSlidingWindow(new int[]{1,4,2,3}, 4);
        //new SlidingWindowMax().medianSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3);

        for (double aDouble : doubles) {
            System.out.println(aDouble);
        }


    }
}
