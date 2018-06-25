package com.hackerrank.sort;

//import java.util.Scanner;

/**
 * Created by Rajesh on 1/3/2018.
 */
public class Median {

    public static void main(String[] args) {
//        Scanner sc= new Scanner(System.in);
//        int n= sc.nextInt();
//        int i;
//        int [] a = new int[n];
//        for(i=0;i<n;i++)
//        {
//            a[i]= sc.nextInt();
//        }
//        int result = findMedian(a);
//        System.out.println(result);
//        sc.close();
//
        int k = 0;
        int [] t1 = new int [] {80, 72, 93, 85, 92, 60, 55};  //55 60 72 80 85 92 93
        k = t1.length/2 + 1;
        System.out.println(kStatistics(t1, 0, t1.length-1, k)); //80
        t1 = new int [] {80, 72, 93, 85, 92, 60, 55};
        System.out.println(findMedian(t1));

        int [] t2 = new int [] {2,5,8,11,16,21,30};
        k = t2.length/2 + 1;
        System.out.println(kStatistics(t2, 0, t2.length-1, 4)); //11
        t2 = new int [] {2,5,8,11,16,21,30};
        System.out.println(findMedian(t2));

        int [] t3 = new int [] {12, 23, 8, 46, 5, 42, 19};
        k = t3.length/2 + 1;
        System.out.println(kStatistics(t3, 0, t3.length-1, k)); //19
        t3 = new int [] {12, 23, 8, 46, 5, 42, 19};
        System.out.println(findMedian(t3));

        int [] t4 = new int [] {2, 4, 7, 9, 3};
        k = t4.length/2 + 1;
        System.out.println(kStatistics(t4, 0, t4.length-1, k)); //4
        t4 = new int [] {2, 4, 7, 9, 3};
        System.out.println(findMedian(t4));

        //for even # of elements
        int t5[] = new int [] {33, 30, 42, 22, 18, 31}; //18, 22, 30, 31, 33, and 42.

        int i1 = kStatistics(t5, 0, t5.length - 1, t5.length / 2 + 1);//31
        int i2 = kStatistics(t5, 0, t5.length - 1, t5.length / 2 );//30
        System.out.println(i1 + " " + i2);

        double d = ((double) (kStatistics(t5, 0, t5.length-1, t5.length/2 + 1) + kStatistics(t5, 0, t5.length-1, t5.length/2 ))) /2;
        System.out.println(d); //30.5
        t5 = new int [] {33, 30, 42, 22, 18, 31};
        System.out.println(findMedian(t5));
    }

    static int findMedian(int[] arr) {
        int left = 0, right = arr.length-1;
        int kStatistics  =  arr.length/2+1;

        int pivotIdx = left;
        while(left < right) {

            pivotIdx = partition(arr, left, right, (left + right) / 2);
            int numOfElementsLessPivot = pivotIdx-left;
            if(numOfElementsLessPivot == kStatistics-1) return arr[pivotIdx];
            else if(numOfElementsLessPivot> kStatistics-1){
                right = pivotIdx-1;
            }else{
                left = pivotIdx+1;
                kStatistics = kStatistics - numOfElementsLessPivot -1;
            }
        }
        if(left==right && kStatistics==1) return arr[left];
        else throw new RuntimeException(String.format("Invalid interval {} {} or kstatistics", left, right, kStatistics));
    }

    public static int partition(int[] array, int begin, int end, int pivotIdx) {

        int pivot = array[pivotIdx];
        swap(array, pivotIdx, end);//move pivot at end
        for (int i = pivotIdx = begin; i < end; ++i) {
            if (array[i] < pivot) {
                if(pivotIdx != i) swap(array, pivotIdx, i);
                ++pivotIdx;
            }
        }
        swap(array, pivotIdx, end);
        //all the elements to the right of pivotIdx are less than pivot
        return (pivotIdx);
    }

    /**
     *
     * @param arr
     * @param left
     * @param right
     * @param k
     * @return kth smallest element i.e. #of elements smaller than a return value is k-1. (k>=1 and k <=total #of elem)
     */
    public static int kStatistics(int [] arr, int left, int right, int k){
        int total = right-left+1;
        if(k<1 || k>total) throw new RuntimeException("not valid value of k " + k);
        if(k==1){
            int min = arr[left];
            for(int i=left+1; i<=right; ++i) min = Math.min(min, arr[i]);
            return min;
        }
        if(k==total){
            int max = arr[left];
            for(int i=left+1; i<=right; ++i) max = Math.max(max, arr[i]);
            return max;
        }

        int pivotIdx = partition(arr, left, right, left + total/2);
        // [left ... pivotIdx-1] < arr[pivotIdx]
        int smallerThanPivot = pivotIdx - left;
        if(smallerThanPivot == k-1){
            return arr[pivotIdx];
        }
        if(smallerThanPivot >k-1){
            return kStatistics(arr, left, pivotIdx-1, k);
        }else{
            //there are smallerThanPivot + 1 elements in left side of pivot that are less than or equal to pivot
            return kStatistics(arr, pivotIdx+1, right, k-smallerThanPivot-1);
        }

    }

    public static void swap(int [] arr, int a, int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
