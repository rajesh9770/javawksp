package com.hackerrank.sort;

import java.util.Scanner;

/**
 * Created by Rajesh on 1/3/2018.
 */
public class Median {

    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        int n= sc.nextInt();
        int i;
        int [] a = new int[n];
        for(i=0;i<n;i++)
        {
            a[i]= sc.nextInt();
        }
        int result = findMedian(a);
        System.out.println(result);
        sc.close();
    }

    static int findMedian(int[] arr) {
        int left = 0, right = arr.length-1;
        int half = (right-left+1)/2;
        while(left != right) {
            int pivotIdx = partition(arr, left, right, (left + right) / 2);
            int numOfElementsLessPivot = pivotIdx-left;
            if(numOfElementsLessPivot == half) break;
            else if(numOfElementsLessPivot>half){
                right = pivotIdx-1;
            }else{
                left = pivotIdx+1;
                half = half - numOfElementsLessPivot;
            }
        }
        return arr[half];
    }

    public static int partition(int[] array, int begin, int end, int pivotIdx) {

        int pivot = array[pivotIdx];
        swap(array, pivotIdx, end);//move pivot at end
        for (int i = pivotIdx = begin; i < end; ++i) {
            if (array[i] <= pivot) {
                if(pivotIdx != i) swap(array, pivotIdx, i);
                ++pivotIdx;
            }
        }
        swap(array, pivotIdx, end);
        return (pivotIdx);
    }

    public static void swap(int [] arr, int a, int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
