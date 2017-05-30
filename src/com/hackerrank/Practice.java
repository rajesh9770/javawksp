package com.hackerrank;

import java.util.Scanner;

/**
 * Created by Rajesh on 5/29/2017.
 */
public class Practice {

    public static void mainForLadder(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        for(int i=0; i<n; ++i){
            for(int j=0; j<n; ++j){
                if(j>n-2-i) System.out.print("#");
                else System.out.print(" ");
            }
            System.out.println("");
        }
    }

    public static void mainForConsecutiveOnesInBinary(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int longOnes = 0;
        while(n>0){
            n &= (n>>1);
            ++longOnes;
        }
        System.out.println(longOnes);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long n = in.nextLong();
        long counter = 3;
        long groupSize = 3;
        while(n>counter){
            groupSize *= 2;
            counter += groupSize;
        }
        //counter maps to 1, counter-1 maps to 2, ...
        System.out.println(counter-n+1);
    }
}

