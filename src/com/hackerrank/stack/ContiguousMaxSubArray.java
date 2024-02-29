package com.hackerrank.stack;

import java.util.Arrays;
import java.util.Stack;

/**                             direction bottom to top
 * ContiguousMaxSubArray        desc                pop top < new
 * Histogram Area,ValidSubArray strictly incr       pop new <= top
 * Stock Span                   strictly desc       pop top <=new
 *
 * In case stack is desc, all elements (from array not in the stack) between pop and next pop are less than the pop.
 *      |
 *      |
 *      |               |
 *      |               |
 *      |<-  x<3    ->  |
 */
public class ContiguousMaxSubArray {

    /**
     * You are given an array arr of N integers. For each index i, you are required to determine the number of contiguous subarrays
     * that fulfills the following conditions:
     * The value at index i must be the maximum element in the contiguous subarrays, and
     * These contiguous subarrays must either start from or end on index i.
     *
     * arr = [3, 4, 1, 6, 2]
     * output = [1, 3, 1, 5, 1]
     * Explanation:
     * For index 0 - [3] is the only contiguous subarray that starts (or ends) with 3, and the maximum value in this subarray is 3.
     * For index 1 - [4], [3, 4], [4, 1]
     * For index 2 - [1]
     * For index 3 - [6], [6, 2], [1, 6], [4, 1, 6], [3, 4, 1, 6]
     * For index 4 - [2]
     * So, the answer for the above input is [1, 3, 1, 5, 1]
     *
     * @param arr
     */
    static int[] countSubarrays(int[] arr) {
        // Write your code here
        int [] left = new int[arr.length]; // keeps track of largest contiguous subarray on left whose length is > 1 such that arr[i] is max within that subarray.
        // This does not count the i-th element itself.
        for(int i=0; i< arr.length; ++i) {
            left[i] = 0;
        }
        Stack<Integer> leftStack = new Stack<>();
        for(int i=0; i<arr.length; ++i){
            while(!leftStack.isEmpty() && arr[leftStack.peek()] < arr[i]){ //keep stack decreasing from bottom to top of stack - add only smaller element
                Integer pop = leftStack.pop();
                //Size of largest subarray to the left of popped element + the popped element itself
                left[i] += (left[pop] +1) ;
            }
            leftStack.push(i);
        }

        //System.out.println("Left:" + Arrays.toString(left));
        int [] right = new int[arr.length];
        for(int i=0; i< arr.length; ++i) {
            right[i] = 0;
        }
        Stack<Integer> rightStack = new Stack<>();
        for(int i=arr.length-1; i>=0; --i){
            while(!rightStack.isEmpty() && arr[rightStack.peek()] < arr[i]){
                Integer pop = rightStack.pop();
                //Size of largest subarray to the right of popped element + the popped element itself
                right[i] += (right[pop] +1) ;
            }
            rightStack.push(i);
        }
        //System.out.println("Right: " + Arrays.toString(right));
        int [] total = new int [arr.length];
        for(int i=0; i<arr.length; ++i){
            total[i] = left[i] + right [i] + 1; // 1 for {current element}
        }
        return total;
    }


    static int[] countSubarrays2(int[] arr) {

        int i=0;
        Stack<Integer> leftStack = new Stack<>();
        int [] left = new int[arr.length]; // keeps track of largest contiguous subarray on left whose length is > 1 such that arr[i] is max within that subarray.
        // This does not count the i-th element itself.
        for(int k=0; k< arr.length; ++k) {
            left[k] = 0;
        }
        while(i<arr.length) {
            if(leftStack.isEmpty() || arr[i] <= arr[leftStack.peek()]) {
                leftStack.push(i);
                ++i;
            }else{
                Integer pop = leftStack.pop();
                left[i] += (left[pop] + 1);
            }
        }
        //System.out.println("Left: " + Arrays.toString(left));


        Stack<Integer> rightStack = new Stack<>();
        int [] right = new int[arr.length]; // keeps track of largest contiguous subarray on left whose length is > 1 such that arr[i] is max within that subarray.
        // This does not count the i-th element itself.
        for(int k=0; k< arr.length; ++k) {
            right[k] = 0;
        }
        i=arr.length-1;
        while(i>=0) {

            if(rightStack.isEmpty() || arr[i] <= arr[rightStack.peek()]) {
                rightStack.push(i);
                --i;
            }else{
                Integer pop = rightStack.pop();
                right[i] += (right[pop] + 1);
            }
        }

        //System.out.println("Right: " + Arrays.toString(right));
        int [] total = new int [arr.length];
        for(i=0; i<arr.length; ++i){
            total[i] = left[i] + right [i] + 1; // 1 for {current element}
        }
        return total;

    }
    public static void main(String[] args) {
        //System.out.println(Arrays.toString(countSubarrays(new int[] {3, 4, 1, 6, 2})));
        System.out.println(Arrays.toString(countSubarrays(new int[] {3, 4, 1, 2, 1,6})));
        System.out.println(Arrays.toString(countSubarrays2(new int[] {3, 4, 1, 2, 1,6})));
    }

}
