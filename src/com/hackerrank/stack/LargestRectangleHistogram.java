package com.hackerrank.stack;

import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Rajesh on 11/16/2017.
 * https://www.youtube.com/watch?v=VNbkzsnllsU
 * https://tech.pic-collage.com/algorithm-largest-area-in-histogram-84cc70500f0c
 * Given an array of integers heights representing the histogram's bar height where the width of each bar is 1, return the area of the largest rectangle in the histogram.
 * https://leetcode.com/problems/largest-rectangle-in-histogram/
 */
public class LargestRectangleHistogram {

static long largestRectangle1(int[] h){//O(n^2)
        int max = 0;
        int start =0;
        int end =0;
        for(int i=0; i<h.length; ++i){
            int height = h[i];
            for(int j=i; j<h.length; ++j){
                height = Math.min(height, h[j]);
                max = Math.max(max, height* (j-i+1));
                System.out.println(i + " " + j + " " + height + " " + max);
            }
        }
        return max;
    }

    static long largestRectangle(int[] h) {
        //position[i] is an index of the smallest number on right which is greater than or equal to corresponding height[i]
        //basically, it tells that the rectangle with height[i] starts at position[i]
        Stack<Integer> position = new Stack<>();
        Stack<Integer> height = new Stack<>();
        position.push(0);
        height.push(h[0]);
        int maxHeight = h[0]*1;
        int start=0, end=0, height1=h[0];
        for(int i=1; i<h.length; ++i){
            if(h[i]>height.peek()){
                // record the start of new height which is bigger than previously seen
                position.push(i);
                height.push(h[i]);
            }else if(h[i]<height.peek()){
                // pop out bigger heights, since they can not continue further to the right
                int positionForCurrentElement = i;
                while(!height.isEmpty() && height.peek() > h[i]){
                    int previousHighHeight = height.pop();
                    int previousHighHeightStartPosition = position.pop();
                    int areaWithpreviousHighHeight = previousHighHeight * (i-previousHighHeightStartPosition);
                    int tmp = maxHeight;
                    maxHeight = Math.max(maxHeight, areaWithpreviousHighHeight);
                    if(maxHeight>tmp){
                       start =  previousHighHeightStartPosition; end =i; height1 = previousHighHeight;
                    }
                    positionForCurrentElement = previousHighHeightStartPosition;
                }
                if(height.isEmpty() || height.peek() < h[i]){
                    position.push(positionForCurrentElement);
                    height.push(h[i]);
                }else{
                    //do nothing
                }
            }else{
                //do nothing
            }
        }
        while(!position.isEmpty()){
            int tmp = maxHeight;
            int h1= height.pop();
            int p1= position.pop();
            maxHeight = Math.max(maxHeight, h1 * (h.length - p1));
            if(maxHeight>tmp){
                start = p1 ; end =h.length; height1 = h1;
            }
        }
//        System.out.println(start + " " + end + " " + height1);
        return maxHeight;
    }

    public static void main2(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] h = new int[n];
        for(int h_i = 0; h_i < n; h_i++){
            h[h_i] = in.nextInt();
        }
        long result = largestRectangle(h);
        System.out.println(result);
        in.close();
    }

    //also see MaxDifference.getMaxArea in sort package. That is same as this one.
    //histogram with n bars
    static int getMaxArea(int hist[], int n)
    {
        // Create an empty stack. The stack holds indexes of hist[] array
        // The bars stored in stack are always in increasing order of their
        // heights.
        Stack<Integer> s = new Stack<>();

        int max_area = 0; // Initialize max area
        int tp;  // To store top of stack
        int area_with_top; // To store area with top bar as the smallest bar

        // Run through all bars of given histogram
        int i = 0;
        while (i < n)
        {
            // If this bar is higher than the bar on top stack, push it to stack
            if (s.empty() || hist[s.peek()] <= hist[i])
                s.push(i++);

                // If this bar is lower than top of stack, then calculate area of rectangle
                // with stack top as the smallest (or minimum height) bar. 'i' is
                // 'right index' for the top and element before top in stack is 'left index'
            else
            {
                tp = s.peek();  // store the top index
                s.pop();  // pop the top

                // Calculate the area with hist[tp] stack as smallest bar
                area_with_top = hist[tp] * (s.empty() ? i : i - s.peek() - 1);

                // update max area, if needed
                if (max_area < area_with_top)
                    max_area = area_with_top;
            }
        }

        // Now pop the remaining bars from stack and calculate area with every
        // popped bar as the smallest bar
        while (s.empty() == false)
        {
            tp = s.peek();
            s.pop();
            area_with_top = hist[tp] * (s.empty() ? i : i - s.peek() - 1);

            if (max_area < area_with_top)
                max_area = area_with_top;
        }

        return max_area;
    }

    //alternate solution for getMaxArea
    /**
     *
     * nextSmallerElements and prevSmallerElements are two private helper methods used to find the indices of the
     * Next Smaller Element (NSE) and the Previous Smaller Element (PSE) for each bar in the histogram.
     * These methods use a stack to efficiently find these indices.
     *
     * The getMaxArea method is the public method responsible for finding the largest rectangular area possible in the given histogram.
     *
     * In the getMaxArea method:
     *
     * Two arrays, next and prev, are created to store the indices of NSE and PSE, respectively, for each bar in the histogram.
     *
     * The method calls the nextSmallerElements and prevSmallerElements to populate the next and prev arrays.
     *
     * A variable area is initialized to INT_MIN, which will be used to track the maximum area found so far.
     *
     * The method then iterates through each bar in the histogram.
     * For each bar, it calculates the width of the rectangle that can be formed using the current bar as the height.
     * The width is determined by the difference between the index of NSE and the index of PSE (minus one).
     *
     * The area of the rectangle is then calculated by multiplying the height (value of the current bar) with the width.
     * The maximum area found so far (area) is updated if the newly calculated area is greater.
     * Finally, the maximum area found is returned as the result.
     */
    /**
     * Returns indices: for ret[i] is the index  of the prev smallest element than the hist[i]
     * @param histo
     * @return
     */
    static int[] prevSmallestElement(int [] histo){
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int[] ret = new int[histo.length];

        for (int i=0; i<histo.length; ++i) {
            while(stack.peek() != -1 && histo[stack.peek()] >= histo[i]){
                stack.pop();
            }
            ret[i] = stack.peek();
            stack.push(i);
        }
        return ret;
    }
    static int[] nextSmallestElement(int [] histo){
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int[] ret = new int[histo.length];

        for (int i=histo.length-1; i>=0; --i) {
            while(stack.peek() != -1 && histo[stack.peek()] >= histo[i]){
                stack.pop();
            }
            ret[i] = stack.peek();
            stack.push(i);
        }
        return ret;
    }

    static int largestRectangleArea(int [] histo) {
        int n=histo.length;
        int[] next =nextSmallestElement(histo);
        int[] prev =prevSmallestElement(histo);

        int area=Integer.MIN_VALUE;
        for(int i=0;i<n;i++){
            int l=histo[i];
            if(next[i]==-1){
                next[i]=n;
            }
            int b=next[i]-prev[i]-1;
            int newArea=l*b;
            area=Math.max(area,newArea);
        }
        return area;
    }

    public static void main(String[] args) {
        //System.out.println(largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}) == 10);
        System.out.println(largestRectangleArea(new int[]{2, 4}) == 4);

    }
}
