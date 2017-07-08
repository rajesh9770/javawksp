package com.hackerrank;

import java.util.Scanner;

/**
 * Created by Rajesh on 7/7/2017.
 */
public class BasicPractice {

    static int birthdayCakeCandles(int n, int[] ar) {
        int tallest = ar[0];
        int count=1;
        for(int i=1; i<n; ++i){
            if(tallest == ar[i]) ++count;
            else if(tallest < ar[i]) {
                tallest = ar[i];
                count = 1;
            }
        }
        return count;
    }

    public static void mainForBirthDayCakeCandles(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] ar = new int[n];
        for(int ar_i = 0; ar_i < n; ar_i++){
            ar[ar_i] = in.nextInt();
        }
        int result = birthdayCakeCandles(n, ar);
        System.out.println(result);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        String[] grid = new String[n];
        for(int grid_i=0; grid_i < n; grid_i++){
            grid[grid_i] = in.next();
        }

        if(n==1){
            System.out.println(grid[0]);
            return;
        }else if(n==2){
            System.out.println(grid[0]);
            System.out.println(grid[1]);
            return;
        }

        char []  previous = grid[0].toCharArray();
        char []  curr = grid[1].toCharArray();
        System.out.println(previous);

        for(int i=1; i<n-1; ++i){
            char[] next = grid[i+1].toCharArray();
            System.out.print(curr[0]);
            for(int j=1; j<n-1; ++j){
                if (curr[j] > curr[j-1] &&
                        curr[j] > curr[j+1] &&
                        curr[j] > previous[j] &&
                        curr[j] > next[j]
                        ){
                    System.out.print("X");
                }else
                    System.out.print(curr[j]);
            }
            System.out.println(curr[n-1]);
            previous = curr;
            curr = next;
        }
        System.out.println(curr);
    }

}
