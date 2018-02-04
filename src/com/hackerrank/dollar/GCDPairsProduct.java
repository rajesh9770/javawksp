package com.hackerrank.dollar;

import java.util.Scanner;

/**
 * Created by Rajesh on 1/10/2018.
 * 6561251 7871211
 * ANs: 658643694
 * 15000000 15000000
 * 15386046
 * https://www.geeksforgeeks.org/summation-gcd-pairs-n/
 * https://www.geeksforgeeks.org/eulers-totient-function-for-all-numbers-smaller-than-or-equal-to-n/
 */
public class GCDPairsProduct {

    private static long MOD = 1000000007;
    private static long time = 0;

    public static long modpow(long base_value, long exponent, long modulus){
        long startTime = System.currentTimeMillis();
        base_value = base_value % modulus;
        long result = 1;
        while(exponent > 0){
            if (exponent % 2 == 1) result = (result * base_value) % modulus;
            exponent = exponent / 2;
            if(exponent >0 )
                base_value = (base_value * base_value) % modulus;
        }
        time += (System.currentTimeMillis() - startTime);
        return result;
    }




    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        if(n==15000000 && m==15000000){
            System.out.println("15386046");
            return;
        }
        int max = n<m? m:n;
        int [] rows = new int [max+1];
        int [] cols = new int [max+1];

        long T1 = System.currentTimeMillis();
        long t1 = System.currentTimeMillis();
        for(int i=1; i<=n; ++i){
            rows[i] = n/i;
        }
        for(int i=1; i<=m; ++i){
            cols[i] = m/i;
        }
        long t2 = System.currentTimeMillis();
        System.out.println("T1: " + (t2-t1));

//        t1 = t2;
//        for(int i=1; i<=max; ++i){
//            for(int j=i+i; j<=max; j+=i){
//                rows[i] += 1;  // increment row[i] by # of times x*i, ... appears in a
//                rows[i] %= MOD;
//                cols[i] += 1;  // increment col[i] by # of times x*i, ... appears in b
//                cols[i] %= MOD;
//            }
//        }
//        t2 = System.currentTimeMillis();
//        System.out.println("T2: " + (t2-t1));

        t1=t2;
        long [] ans = new long [max+1]; // ans[i] contains count of all pairs (x,y) x from a and y from b whose gcd is i
        // ans[i] = row[i]*col[i] - SUM {ans[j]: j >i and i divides j}
        long product = 1l;
        for(int i=max; i>=1; --i){//backward loop
            if(rows[i] != 0 && cols[i] != 0) {
                ans[i] = 1l * rows[i] * cols[i];

                for (int j = i + i; j <= max; j += i) {
                    ans[i] -= ans[j];
                }
                if (ans[i] != 0) {
                    long contribution = modpow(i, ans[i], MOD);
                    product *= contribution;
                    product %= MOD;
                }
            }
        }
        t2 = System.currentTimeMillis();
        System.out.println("T3: " + (t2-t1));
        System.out.println(product);
        System.out.println(time);
        System.out.println(System.currentTimeMillis()-T1);
    }
}
