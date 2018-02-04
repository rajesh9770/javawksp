package com.hackerrank.dollar;

import java.util.Scanner;

/**
 * Created by Rajesh on 1/28/2018.
 */
public class PandaPower {

    private static long modpow(long base_value, long exponent, long modulus){
        base_value = base_value % modulus;
        long result = 1;
        while(exponent > 0){
            if (exponent % 2 == 1) result = (result * base_value) % modulus;
            exponent = exponent / 2;
            if(exponent >0 )
                base_value = (base_value * base_value) % modulus;
        }
        return result;
    }

    //Euclid's algorithm
    private static int[] gcd(int dividend, int divisor) {

        int remainder = dividend % divisor;
        if(remainder == 0){
            int [] ret = new int[3];
            ret[0] = divisor;
            ret[1] = 1;  // this is x : g = x * divisor + y * dividend
            ret[2] = 0;  // this is y
            return ret;
        }

        int[] gcd = gcd(divisor, remainder);
        int tmp = gcd[2] - (dividend/divisor)* gcd[1];  // x = y1 - (b/a) * x1
        gcd[2] = gcd[1];                                // y = x1
        gcd[1] = tmp;
        return gcd;
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        while(q-->0){
            int a = in.nextInt();
            int b = in.nextInt();
            int x = in.nextInt();
            if(b<0){
                int[] gcd = gcd(x, a);
                a = gcd[1] % x;
                if(a<0) a += x;
                //System.out.println("Inverse " + a);
                b = -b;
            }
            //System.out.println(a + " " + b + " " + x);
            System.out.println(modpow(a,b,x));
        }
    }
}
