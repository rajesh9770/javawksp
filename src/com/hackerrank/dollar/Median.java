package com.hackerrank.dollar;



public class Median {

    //Find the median of two sorted arrays
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int [] x = nums1.length < nums2.length ? nums1 : nums2;
        int [] y = nums1.length < nums2.length ? nums2 : nums1;

        int low = 0;
        int high = x.length;
        while(low<=high){
            int midx = (low+high)/2;
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
        System.out.println(findMedianSortedArrays(new int[]{1,3}, new int[]{2}));
    }
}
