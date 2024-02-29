package com.hackerrank.dollar;


import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Median {

    //Find the median of two sorted arrays
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int [] x = nums1.length < nums2.length ? nums1 : nums2;
        int [] y = nums1.length < nums2.length ? nums2 : nums1;

        int low = 0;
        int high = x.length; //high is one + the right end index (=x.length-1)
        while(low<=high){
            //low, ..... low+k-1, low+k, ....high=low+2k-1  len = 2k.
            int midx = (low+high)/2; //gives higher of the two midpoints in case of even set of numbers, i.e. when x1, ..., x2n, it gives n+1

            int midy = (x.length + y.length + 1)/2 - midx;

            int maxx = midx>0 ? x[midx-1] : Integer.MIN_VALUE;  //max from left half
            int minx = midx<x.length ? x[midx] : Integer.MAX_VALUE;  //min from right half

            int maxy = midy>0 ? y[midy-1] : Integer.MIN_VALUE;  //max from left half
            int miny = midy<y.length ? y[midy] : Integer.MAX_VALUE;  //min from right half

            if(maxx<= miny && maxy <= minx) {
                if((x.length+y.length) %2 == 0) {
                    return ((double)(Math.max(maxx, maxy) + Math.min(minx, miny)))/2.0;
                }else{
                   return (double)(Math.max(maxx, maxy));
                }
            }else if(maxx > miny) {
                high = midx-1;
            }else {
                low = midx +1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        double medianSortedArrays = findMedianSortedArrays(new int[]{1}, new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
        System.out.println(medianSortedArrays + " "  + (medianSortedArrays == 6));

        medianSortedArrays = findMedianSortedArrays(new int[0], new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
        System.out.println(medianSortedArrays + " "  + (medianSortedArrays == 6));

        medianSortedArrays = findMedianSortedArrays(new int[0], new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,12, 13});
        System.out.println(medianSortedArrays + " "  + (medianSortedArrays == 7));
    }

    //two pointer solution
    class Solution {
        private int p1 = 0, p2 = 0;

        // Get the smaller value between nums1[p1] and nums2[p2] and move the pointer forwards.

        private int getMin(int[] nums1, int[] nums2) {
            if (p1 < nums1.length && p2 < nums2.length) {
                return nums1[p1] < nums2[p2] ? nums1[p1++] : nums2[p2++];
            } else if (p1 < nums1.length) {
                return nums1[p1++];
            } else if (p2 < nums2.length) {
                return nums2[p2++];
            }
            return -1;
        }
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int m = nums1.length, n = nums2.length;
            if ((m + n) % 2 == 0) {
                for (int i = 0; i < (m + n) / 2 - 1; ++i) {
                    getMin(nums1, nums2);
                }
                return (double)(getMin(nums1, nums2) + getMin(nums1, nums2)) / 2;
            } else {
                for (int i = 0; i < (m + n) / 2; ++i) {
                    getMin(nums1, nums2);
                }
                return getMin(nums1, nums2);
            }
        }
    }

    //Median of stream
    private Queue<Integer> left = new PriorityQueue(1, (Comparator<Integer>) (o1, o2) -> o2 - o1); //max-heap

    private Queue<Integer> right = new PriorityQueue(); //min-heap
    // Adds a number into the data structure.
    public void addNum(int num) {
        right.add(num);
        left.add(right.poll());
        if (right.size() < left.size())
            right.add(left.poll());
    }

    // Returns the median of current data stream
    public double findMedian() {
        return right.size() > left.size()
                ? right.peek()
                : (right.peek() + left.peek()) / 2.0;
    }
}
