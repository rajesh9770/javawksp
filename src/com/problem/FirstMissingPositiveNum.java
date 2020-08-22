package com.problem;

//Given an unsorted integer array, find the smallest missing positive integer.
public class FirstMissingPositiveNum {

    public int firstMissingPositive(int[] A) {

        //Put each number in its right place. i.e k at A[k-1]
        for(int i = 0; i < A.length; ++ i) {
            while (A[i] > 0 && A[i] <= A.length && A[A[i] - 1] != A[i])
                swap(A, i, A[i] - 1); //k = A[i]
        }

        for(int i = 0; i < A.length; ++ i)
            if(A[i] != i + 1)
                return i + 1;

        return A.length + 1;
    }

    public void swap(int[] a, int i , int j){
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
