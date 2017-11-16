package com.hackerrank.dynamicprog;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by Rajesh on 11/12/2017.
 */
public class Fradulent {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int d = in.nextInt();
        int mid1 = (d+1)/2;
        int mid2 = (d+2)/2;

        int [] expenditures = new int [201];

        for(int i=0; i<201; ++i){
            expenditures[i] = 0;
        }
        Queue<Integer> dWindow = new LinkedList<Integer>();
        int alerts = 0;
        //read first d expenditures
        int i=1;
        for(; i<=d; ++i){
            int expenditure = in.nextInt();
            expenditures[expenditure]++;
            dWindow.add(expenditure);
        }
        for(; i<=n; ++i){
            int expenditure = in.nextInt();
            long twiceMedian = findTwiceMedian(expenditures, mid1, mid2);
            if(twiceMedian <=  expenditure) alerts++;
            expenditures[expenditure]++;
            dWindow.add(expenditure);
            Integer remove = dWindow.remove();
            expenditures[remove]--;
        }

        System.out.println(alerts);
    }

    private static long findTwiceMedian(int[] expenditures, int mid1, int mid2) {
        int total = 0;
        long sum = 0;
        boolean mid1Found = false;
        boolean mid2Found = false;
        for(int i=0; i<201; ++i){
            total += expenditures[i];
            if(!mid1Found && total >= mid1) {
                sum += i;
                mid1Found = true;
            }
            if(!mid2Found && total >= mid2) {
                sum += i;
                mid2Found = true;
                return sum;
            }
        }
        return 0;
    }
}
