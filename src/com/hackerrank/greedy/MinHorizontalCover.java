package com.hackerrank.greedy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Rajesh on 6/5/2018.
 * CA is a county with a number of evenly spaced cities along a line.
 * The distance between adjacent cities is 1 unit.
 * There is an energy infrastructure project planning meeting, and the government needs to know the
 * fewest number of power plants needed to provide electricity to the entire list of cities.
 * Determine that number. If it cannot be done, return -1.
 * You are given a list of city data. Cities that may contain a power plant have been labeled 1.
 * Others not suitable for building a plant are labeled 0.
 * Given a distribution range of k, find the lowest number of plants that must be built such that all cities are served.
 * The distribution range limits supply to cities where distance is less than k.
 */
public class MinHorizontalCover {

    // Complete the pylons function below.
    static int pylons(int k, int[] arr) {
        //for each city find the power plant that covers it and is farthest right
        int [] span = new int [arr.length];
        for(int i=0; i<arr.length; ++i) span[i] = -1;
        for(int i=0; i<arr.length; ++i){
            if(arr[i] == 1){
                span[i] = Math.max(span[i], i);
                //forward
                for(int j=1; j<k && i+j<arr.length; ++j) span[i+j] = Math.max(span[i+j], i);
                //backward
                for(int j=1; j<k && i-j>=0; ++j) span[i-j] = Math.max(span[i-j], i);
            }
        }
        int nextPowerPlant = 0;
        int count = 0;
        while(nextPowerPlant<arr.length){
            ++count;
            int farthestRightPowerPlant = span[nextPowerPlant]; //power plant that covers curr power plant and is farthest right (does not mean it to the right of curr power plant)
            if(farthestRightPowerPlant == -1) return -1;
            nextPowerPlant = farthestRightPowerPlant + k; //that power plant will cover k-1 cities right of it. So look at the k-th city next.
        }
        return  count;
    }


    static int pylons2(int k, int[] arr) {
        int curr = 0;
        int count = 0;
        int PreviousSelectedPowerPlant = -1; //previous selected is -1.

        while(curr<arr.length){
            //find the farthest right power plant which is strictly on right of previous selected power plant.
            int nextPowerPlant = Math.min(curr+k-1, arr.length-1);
            for(; PreviousSelectedPowerPlant<nextPowerPlant; --nextPowerPlant){
                if(arr[nextPowerPlant] ==1){
                    break;
                }
            }
            if(nextPowerPlant <= PreviousSelectedPowerPlant){
                return -1;
            }
            ++count;
            PreviousSelectedPowerPlant = nextPowerPlant;
            curr = nextPowerPlant + k-1 + 1;  // all in-between nextPowerPlant,..., nextPowerPlant + (k-1) are covered.
        }
        return count;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        String[] nk = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nk[0]);

        int k = Integer.parseInt(nk[1]);

        int[] arr = new int[n];

        String[] arrItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int arrItem = Integer.parseInt(arrItems[i]);
            arr[i] = arrItem;
        }

        int result = pylons2(k, arr);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
