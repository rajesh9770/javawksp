package com.hackerrank.stack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Rajesh on 11/18/2017.
 * You are given a list of  numbers . For each element at position i (), we define Left(i) and  Right(i) as:
 *  Left(i) = closest index j such that j < i and a_j > a_i </>. If no such j exists then j = 0.
 *  Right(i) = closest index k such that k > i and a_k > a_i. If no such k exists then k = 0.
 *
 * We define  IndexProduct(i)=  Left(i) * Right(j) . You need to find out the maximum IndexProduct(i) among all i.
 */
public class LeftRightIndexProduct {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        System.out.println("Input size: " + n);
        //if(true) return;
        int[] number = new int[n];

        for (int number_i = 0; number_i < n; number_i++){
            number[number_i] = in.nextInt();
        }
        int [] leftIdx = new int [n];
        int [] rightIdx = new int [n];
        leftIdx[0] = 0;
        rightIdx[n-1] =0;
        Stack<Integer> leftSmallerIdxs = new Stack<>(); //keeps track of indices of elements that are smaller
        leftSmallerIdxs.push(0);


        for(int i=1; i<n; ++i){
            int cNumber = number[i];
            while(!leftSmallerIdxs.isEmpty() && number[leftSmallerIdxs.peek()] <= cNumber){
                //pop of all elements that are smaller the current, since we are going to add the current element on stack.
                //and if a new element on right of current element is greater than the current elements, then all the poped elements
                //are smaller than the new element and are not useful. On the other hand if new element on right is smaller than the current element
                //then it;s left index cann't be higher than the index of current element.
                leftSmallerIdxs.pop();
            }
            if(!leftSmallerIdxs.isEmpty()){
                leftIdx[i] = leftSmallerIdxs.peek() +1; //indices start from 0; so add 1
            }else{
                leftIdx[i] = 0;
            }
            //System.out.println(leftIdx[i]);
            leftSmallerIdxs.push(i);
        }

        //if(true) return;

        Stack<Integer> rightSmallerIdxs = new Stack<>();
        rightSmallerIdxs.push(n-1);
        for(int r=n-2; r>=0; --r){
            int cNumber = number[r];
            while(!rightSmallerIdxs.isEmpty() && number[rightSmallerIdxs.peek()] <= cNumber){
                rightSmallerIdxs.pop();
            }
            if(!rightSmallerIdxs.isEmpty()){
                rightIdx[r] = rightSmallerIdxs.peek() +1; //indices start from 0; so add 1
            }else{
                rightIdx[r] = 0;
            }
            rightSmallerIdxs.push(r);
        }

        long max =0;
        for(int k=1;k<n-1; ++k){
            max = Math.max(max, ((long)leftIdx[k])*((long)rightIdx[k]));
        }
        System.out.println(max);
    }

}
