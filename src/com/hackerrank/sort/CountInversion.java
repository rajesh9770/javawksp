package com.hackerrank.sort;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Rajesh on 6/1/2018.
 */
public class CountInversion {


    // Complete the countInversions function below.
    static long countInversions(int[] arr) {
        return mergeSort(arr, 0, arr.length-1);
    }

    private static long mergeSort(int[] arr, int start, int end) {

        if(start >= end) return 0;
        int mid = (start+end)/2;
        long c1 = mergeSort(arr, start, mid);
        long c2 = mergeSort(arr, mid+1, end);
        long c3 = merge(arr, start, mid, end);
        return c1 + c2 + c3;
    }

    /**
     * merges arr[start, mid] and arr[mid+1, end]; both parts are sorted
     * @param arr
     * @param start
     * @param mid
     * @param end
     * @return the number of inversions during the merge phase.
     */
    private static long merge(int[] arr, int start, int mid, int end) {
        int i = start;
        int j = mid+1;
        long inversions = 0;
        int [] temp  = new int [end-start+1]; //store the sorted result here
        int idx = 0;
        while(i<=mid && j<=end){
            if(arr[i] <= arr[j]) {
                temp[idx++] = arr[i];
                ++i;
            }
            else{
                temp[idx++] = arr[j];
                j++;
                inversions += (mid-i+1); // all the elements in right of i i.e. a[i] ... a[mid], from the first array are greater than arr[j]
            }
        }
        //Copy the remaining elements of left subarray (if there are any) to temp
        while(i<=mid) temp[idx++] = arr[i++];

        //Copy the remaining elements of right subarray (if there are any) to temp
        while(j<=end) temp[idx++] = arr[j++];

        //copy back the sorted result back into arr
        for(int k=start; k<=end; ++k){
            arr[k] = temp[k-start];
        }
        return inversions;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] arr = new int[n];

            String[] arrItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int arrItem = Integer.parseInt(arrItems[i]);
                arr[i] = arrItem;
            }

            long result = countInversions(arr);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}
