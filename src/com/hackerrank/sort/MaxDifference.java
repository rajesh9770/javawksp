package com.hackerrank.sort;

import java.util.*;
import javafx.util.Pair;

/**
 * Created by Rajesh on 3/11/2018.
 */
public class MaxDifference {


    /**
     * Given an array A[] and a number x, check for pair in A[] with sum as x
     */

    public static boolean findPairWithSum(int arr[], int sum){  //O(nln(n))

        int l = 0, r = arr.length-1;
        Arrays.sort(arr);  //O(nln(n))
        while(l<r){
            int tmp = arr[l] + arr[r];
            if(tmp == sum) return true;

            if(tmp < sum) l++;
            else r--;
        }
        return false;
    }

    public static boolean findPairWithSum2(int arr[], int sum){  //O(n)

        Map<Integer, Set<Integer>> valToIndices = new HashMap<>();
        for(int i=0; i< arr.length; ++i){
            Set<Integer> indices = valToIndices.get(arr[i]);
            if(indices == null) {
                indices = new HashSet<>();
                valToIndices.put(arr[i], indices);
            }
            indices.add(i);
        }
        for (int i=0; i<arr.length; ++i){
            int target = sum - arr[i];
            Set<Integer> indices = valToIndices.get(target);
            if (indices != null) {
                if(indices.contains(i) && indices.size()==1) continue;
                else return true;
            }
        }
        return false;
    }

    //Given a list of n integers arr[0..(n-1)], determine the number of different pairs of elements within it which sum to k.
    public static int numberOfWays(int[] arr, int k) {
        // Write your code here
        Map<Integer, Integer> nums = new HashMap<>();
        for(int a: arr){
            if(nums.containsKey(a)){
                Integer ct = nums.get(a);
                nums.put(a, ct+1);
            }else{
                nums.put(a, 1);
            }
        }
        int ret = 0;
        // iterate through each element and increment the
        // count (Notice that every pair is counted twice)
        for(int a: nums.keySet()){
            if(nums.containsKey(k-a)){
                Integer ct = nums.get(k - a);
                if(k-a == a){
                    ret += (ct*(ct-1));
                }else{
                    ret += ct * nums.get(a);
                }
            }
        }
        return ret/2;
    }
    /**
     * Given an unsorted array and a number n, find if there exists a pair of elements in the array whose difference is n.
     *
     */
    static boolean findPair(int arr[], int n){
        Arrays.sort(arr);
        int i=0, j=1; //j=1 saves one loop
        while(i<arr.length && j<arr.length){
            if(i!=j && arr[j]-arr[i] ==n){
                return true;
            }
            if(arr[j]-arr[i] <n){
                j++;
            }else{
                i++;
            }
        }
        return false;
    }

    /**
     * Given an array, find the maximum j – i such that arr[j] > arr[i]
     * @param a
     * @return
     */
    public static int maxIndexDiff(int[] a) {

        int RMax[] = new int[a.length];
        int LMin[] = new int[a.length];
        /* Construct LMin[] such that LMin[i] stores the minimum value
           from (arr[0], arr[1], ... arr[i]) */
        LMin[0] = a[0];
        for(int i=1; i<a.length; ++i)
            LMin[i] = Math.min(LMin[i-1], a[i]);
        /* Construct RMax[] such that RMax[j] stores the maximum value
           from (arr[j], arr[j+1], ..arr[n-1]) */
        RMax[a.length-1] = a[a.length-1];
        for(int j=a.length-2; j>=0; --j)
            RMax[j] = Math.max(RMax[j+1], a[j]);

        int i=0, j=0;
        int indexDiff = -1;
        /**
         * While traversing LMin[] and RMax[] if we see that LMin[i] is greater than RMax[j],
         * then we must move ahead in LMin[] (or do i++) because all elements on left of LMin[i] are greater than or equal to LMin[i].
         * Otherwise we must move ahead in RMax[j] to look for a greater j – i value.
         */
        while(i<a.length && j<a.length){
            if(LMin[i] <RMax[j]){
                indexDiff = Math.max(indexDiff, j-i);
                j++;
            }else{
                i++;
            }
        }
        return indexDiff;
    }

    /**
     * https://leetcode.com/problems/3sum/
     * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0?
     * Find all unique triplets in the array which gives the sum of zero.
     */

    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i=0; i< nums.length; ++i) {
            if (i>0 && nums[i-1] == nums[i]) continue;//we check if prev number is the same as the current one,
            // this is not the same as checking if current number is the same as next one.
            //use findPairWithSum to find two numbers that add up to nums[i]
            int left = i+1, right = nums.length-1;
            while (left < right) {
                if (nums[left] + nums[right] + nums[i] == 0)  {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left++;
                    while(left < right && nums[left] == nums[left-1]) left++;
                } else if (nums[left] + nums[right] + nums[i] < 0 ) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int [] nums = {-1,0,1,2,-1,-4};//after sort -4 -1 -1 0 1 2
        System.out.println(threeSum(nums));
    }

    /**
     * Given an array of positive numbers and a positive number ‘k,’
     * find the maximum sum of any contiguous subarray of size ‘k’.
     */

    public static int findMaxSumSubArray(int k, int[] arr) {
        if(arr.length>k){
            int currMaxSum = 0;
            for (int i=0; i<k; ++i) {
                currMaxSum += arr[i];
            }
            int currSum = currMaxSum;
            for(int i=k; i<arr.length; ++i){
                currSum = currSum + arr[i] - arr[i-k];
                if(currSum > currMaxSum){
                    currMaxSum = currSum;
                }
            }
            return currMaxSum;
        }
        return -1;
    }

    /**
     * Total Histogram Volume. Taller bar can go over the smaller bar.
     * Find the highest bars on left and right sides.
     * Take the smaller of two heights.
     * The difference between smaller height and height of current element is the amount of water that can be stored in this array element.
     * Think of one histogram as wide block that is 1 unit wide and a[i] tall.
     *    _            _       _
     *   | |  _       |_|  _  |_|
     *  _| |_| |      |_|_|_|_|_|
     * | | | | |      |_|_|_|_|_|
     * ------------------------------
     * For first figure, Answer is 1, since only 3rd histogram can hold 1 unit of water.
     * For the second figure, Answer is  5.
     * The quetion is asking total amount water that can be stored.
     */
    public static long histogramVolume(int [] a){
        int LHigh[] = new int [a.length];
        int RHigh[] = new int [a.length];

        LHigh[0] = a[0];
        for(int i=1; i<a.length; ++i){
            LHigh[i] = Math.max(LHigh[i-1], a[i]);
        }

        RHigh[a.length-1] = a[a.length-1];
        for(int j=a.length-2; j>=0; --j){
            RHigh[j] = Math.max(RHigh[j+1], a[j]);
        }

        int vol = 0;
        for(int i=0; i<a.length; ++i){
            int height = Math.min(LHigh[i], RHigh[i]);
            if(height>a[i]) vol += (height-a[i]) ;
        }
        return vol;
    }

    // The main function to find the maximum rectangular area under given
    // histogram with n bars. Taller bar can't go over smaller one. Smaller histogram can go through taller ones.
    //
    //
    //
    //                   _
    //                 _|_|_
    //             _  |_|_|_|
    //           _|_| |_|_|_|_
    //          |_|_|_|_|_|_|_|_
    //          |_|_|_|_|_|_|_|_|
    //           1 2 3 4 5 6 7 8
    //Column 4 5 6  (4 * 3 = 12)

    public static long getMaxArea(int hist[]){

        Stack<Integer> stack = new Stack<>();
        int i=0;
        long maxArea = 0;

        while(i<hist.length){
            if(stack.isEmpty() || hist[stack.peek()] < hist[i]){ //stack is always strictly increasing
                stack.push(i);
                ++i;
            }else{
                //When we push the element on stack, we pop all elements that are >= that element to make a space for it.
                //So all the elements between two stack indices j and j+1 are >= the element at index j+1.
                // If this bar is lower than top of stack, then calculate area of rectangle
                // with stack top as the smallest (or minimum height) bar. 'i' is
                // 'right index' for the top and element before top in stack is 'left index'
                int top = stack.pop();
                //hist[top] on right bounded by i-1 (i is one over) i.e. hist[top] >= hist[i] and hist[top] <= hist[i-1] as it is on stack
                // and on left bounded by new top on stack (after above pop) since stack is always increasing.
                // i.e. hist[stack.newtop] < hist[top].
                // Since we pop when we see a smaller element implies that all elements from [stack.newtop+1 .... top] are of the height > top.
                // so rectangle hist[top] spans from stack.newtop + 1 to i-1, i.e its width is i-1-stack.newtop-1 +1
                // -----------------------------------*---------------*---------
                //             stack.newtop  stack.newtop+1          i-1   i
                // if stack is empty, then all the elements on left of hist[top] are greater than hist[top],
                // so width is from 0 to i-1 i.e. i.
                long areaWithTop = hist[top] * (stack.isEmpty() ? i : (i-stack.peek()-1));
                maxArea = Math.max(maxArea, areaWithTop);
            }
        }

        while(!stack.isEmpty()){
            int top = stack.pop();
            long areaWithTop = hist[top] * (stack.isEmpty() ? i : (i-stack.peek()-1));
            maxArea = Math.max(maxArea, areaWithTop);
        }
        return maxArea;
    }

    public static void mainForMaxArea(){
        int hist[] = { 6, 2, 5, 4, 5, 1, 6 };
        System.out.println("Maximum area is " + getMaxArea(hist));
        //ans is 9, since the rectangle with height 3 spans 3 units
        // and has more area than 1*5 or 4*2 or 5*1
        System.out.println("Maximum area is " + getMaxArea(new int[]{1, 2, 3, 4, 5}));
        System.out.println("Maximum area is " + getMaxArea(new int[]{1, 2, 3, 4, 5, 1000}));
        System.out.println("Maximum area is " + getMaxArea(new int[]{2,2,2,2}));
    }

    public static void main2(String[] args) {
        mainForMaxArea();
        //System.out.println(numberOfWays(new int[] {1,2,2,4,4,4}, 6));
    }


    /**
     * https://leetcode.com/problems/container-with-most-water/
     * Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai).
     * n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0).
     * Find two lines, which together with x-axis forms a container, such that the container contains the most water.
     * This problem is simpler than the above two. In this problem, both small and taller bar can lie between the two outer lines.
     * We have to maximize the Area that can be formed between the vertical lines using the shorter line as length
     * and the distance between the lines as the width of the rectangle forming the area.
     */

    public int maxArea(int[] height) {
        int maxarea = 0, l = 0, r = height.length - 1;
        while (l < r) {
            maxarea = Math.max(maxarea, Math.min(height[l], height[r]) * (r - l));
            if (height[l] < height[r])
                l++;
            else
                r--;
        }
        return maxarea;
    }
}
