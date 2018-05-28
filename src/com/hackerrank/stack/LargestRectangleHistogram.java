package com.hackerrank.stack;

import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Rajesh on 11/16/2017.
 * https://www.youtube.com/watch?v=VNbkzsnllsU
 */
public class LargestRectangleHistogram {

    static long largestRectangle1(int[] h){
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

    public static void main(String[] args) {
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

    //also see MaxDifference in sort package
    // histogram with n bars
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
}
