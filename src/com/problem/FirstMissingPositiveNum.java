package com.problem;

import java.util.Arrays;

//Given an unsorted integer array, find the smallest missing positive integer.
public class FirstMissingPositiveNum {

    public static int firstMissingPositive(int[] A) {

        //Put each number in its right place. i.e k at A[k-1]
        for(int i = 0; i < A.length; ++ i) {
            System.out.println("----");
            System.out.println(Arrays.toString(A));
            while (A[i] > 0 && A[i] <= A.length && A[A[i] - 1] != A[i])
                swap(A, i, A[i] - 1); //k = A[i]
            System.out.println(Arrays.toString(A));
        }

        for(int i = 0; i < A.length; ++ i)
            if(A[i] != i + 1)
                return i + 1;

        return A.length + 1;
    }

    public static void swap(int[] a, int i , int j){
        System.out.println("Swapping " + i + " " + j );
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void main(String[] args) {
        int [] A = new int [] {3,4, 9, 1};

        System.out.println(Arrays.toString(A));
        System.out.println(firstMissingPositive(A));
        System.out.println(Arrays.toString(A));
    }
}
