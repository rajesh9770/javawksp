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

    public static void mainForStrangeCounter(String[] args) {
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

    public static void mainForSaveThePrisoner(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for (int a0 = 0; a0 < t; a0++) {
            long n = in.nextInt();
            long m = in.nextInt();
            long s = in.nextInt();
            //System.out.println(m % n);
            long result = (s - 2) + m % n;
            result +=n;
            result %= n;
            //System.out.println(result%n+1);
            System.out.println(result + 1);
        }
    }
}
