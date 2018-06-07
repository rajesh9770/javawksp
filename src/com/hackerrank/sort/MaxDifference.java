package com.hackerrank.sort;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created by Rajesh on 3/11/2018.
 */
public class MaxDifference {


    /**
     * Given an array A[] and a number x, check for pair in A[] with sum as x
     */

    public static boolean findPairWithSum(int arr[], int sum){

        int l = 0, r = arr.length-1;
        Arrays.sort(arr);
        while(l<r){
            int tmp = arr[l] + arr[r];
            if(tmp == sum) return true;

            if(tmp < sum) l++;
            else r--;
        }
        return false;
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
     * Histogram Volume
     * Find the highest bars on left and right sides.
     * Take the smaller of two heights.
     * The difference between smaller height and height of current element is the amount of water that can be stored in this array element.
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
    // histogram with n bars
    public static long getMaxArea(int hist[]){

        Stack<Integer> stack = new Stack<>();
        int i=0;
        long maxArea = 0;

        while(i<hist.length){
            if(stack.isEmpty() || hist[stack.peek()] < hist[i]){ //stack is always increasing
                stack.push(i);
                ++i;
            }else{
                // If this bar is lower than top of stack, then calculate area of rectangle
                // with stack top as the smallest (or minimum height) bar. 'i' is
                // 'right index' for the top and element before top in stack is 'left index'
                int top = stack.pop();
                //hist[top] on right bounded by i-1 (i is one over) i.e. hist[top] >= hist[i]
                // and on left bounded by new top on stack (after above pop) since stack is always increasing.
                // i.e. hist[stack.newtop] < hist[top].
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
    }

    public static void main(String[] args) {
        mainForMaxArea();
    }
}
