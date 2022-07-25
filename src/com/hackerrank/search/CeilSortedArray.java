package com.hackerrank.search;

public class CeilSortedArray {

    /**
     * return ceil - smallest element in the array that is biggerr than or equal to x
     * @param arr
     * @param start
     * @param end
     * @param x
     * @return
     */
    public static int ceil(int[] arr, int start, int end, int x) {
        if(start>end) return -1;
        if(arr[end]< x) return -1;
        if(x<=arr[start]) return start;

        int mid = (start + end)/2;
        if(arr[mid] == x) return mid;
        else if (arr[mid]<x) {
            if(mid+1<=end && x<=arr[mid+1] ) return mid+1;
            return ceil(arr, mid+1, end, x);
        }else {
            if(start <= mid-1 && x<arr[mid-1] ) return mid;
            return ceil(arr, start, mid-1, x);
        }
    }

    /**
     * return floor - largest element in the array that is smaller than or equal to x
     * @param arr
     * @param start
     * @param end
     * @param x
     * @return
     */
    public static int floor(int[] arr, int start, int end, int x) {
        if(start>end) return -1;
        if(arr[end]<= x) return end;
        if(x<arr[start]) return -1;

        int mid = (start + end)/2;
        if(arr[mid] == x) return mid;
        else if (arr[mid]<x) {
            if(mid+1<=end && x<arr[mid+1] ) return mid;
            return ceil(arr, mid+1, end, x);
        }else {
            if(start <= mid-1 && arr[mid-1]<=x ) return mid-1;
            return ceil(arr, start, mid-1, x);
        }
    }

    public static void main(String[] args) {
        int [] arr = {1,2,3,4,5,7,8,9,10};
        System.out.println(arr[ceil(arr,0, arr.length-1, 6)]);
        System.out.println(arr[floor(arr,0, arr.length-1, 6)]);
    }
}
