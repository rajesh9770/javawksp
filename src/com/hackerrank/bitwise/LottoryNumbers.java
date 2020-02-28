package com.hackerrank.bitwise;


import java.util.Scanner;

/**
Given an array on numbers. Find number of pairs that contains all digits.
For example, if there are  distinct tickets with ticket ID 12308 and 56479, is a winning pair, since
 if we concatenate 12308 and 56479 we get get a number that has all digits 0-9.
**/
public class LottoryNumbers {



    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        //System.out.println(_0 + " " + _9);
        //if(true) return;
        long[] frequency = new long[1024];
        int binary;
        int size = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");   //Only to solve an input related error of scanner.
        long  counter = 0l;

        for (int i = 0; i < size; i++) {
            String ticketsItem = scanner.nextLine();
            binary = binaryEquivalent(ticketsItem);
            System.out.println( ticketsItem + " " + binary);
            frequency[binary] = frequency[binary] + 1l;
        }
        for (int i = 0; i < 1023; i++) {
            if(frequency[i]==0)
                continue;
            for(int j=i+1;j<1024;j++) {
                if((i|j)==1023)
                    counter += frequency[i]*frequency[j];
            }
        }
        counter += frequency[1023]*(frequency[1023]-1)/2;
        System.out.println(counter);
        scanner.close();
    }

    private static int binaryEquivalent(String ticketsItem) {
        int k=0;
        for (int i = 0; i < ticketsItem.length(); i++) {
            k ^= (1 << (ticketsItem.charAt(i) - '0'));
        }
        return k;
    }

}
