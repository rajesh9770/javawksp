package com.hackerrank.dynamicprog;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rajesh on 10/28/2017.
 */

/**
 *
 * In array of length i, # of contiguous splits are 2^(i-1) for i >=1 and 1 for i = 0.
 *
 * S0= (2^n) -1 = n*1 + (n-1)*1 + (n-2) * 2 + (n-3) * (2^2) + ...+ 3* 2^(n-4)+ 2 * 2^(n-3) + 1*2^(n-2)
 * RHS: Consider an array of size n. Consider a[0] and start counting the number of splits such that a[0] is contained in n size piece, n-1 size piece, ... 1 size piece.
 *
 * Now consider a[1] and start counting the number of splits such that a[1] is contained in n size piece, n-1 size piece, ... 1 size piece.
 * S1= n*1 + (n-1)*[1+1] + (n-2)*[1+2^1] + (n-3)*[2^1+2^2] + ... + 3*[2^(n-5) + 2^(n-4)] + 2*[2^(n-4) + 2*(n-3)] + 1*2^(n-3)
 * See that S1 - S0 = (n-1)*1 + (n-2) *1 + (n-3) *2 + ... + 3* 2^(n-5) + 2* 2^(n-4) - 1 * 2^(n-3)
 *                  = (n-1)*1 + (n-2) *1 + (n-3) *2 + ... + 3* 2^(n-5) + 2* 2^(n-4) + 1 * 2^(n-3) - 1 * 2^(n-3) - 1 * 2^(n-3)
 *                  = [(n-1)*1 + (n-2) *1 + (n-3) *2 + ... + 3* 2^(n-5) + 2* 2^(n-4) + 1 * 2^(n-3)] - 2^(n-2)
 *                  = [2^(n-1) -1] - 2^(n-2)   ## the term inside [] is S0 with n replaced by n-1.
 *                  = 2^(n-2) -1
 *
 *
 * Now consider a[2] and start counting the number of splits such that a[2] is contained in n size piece, n-1 size piece, ... 1 size piece.
 * S2= n*1 + (n-1)*[1+1] + (n-2) * [2+1+2] + (n-3)* [2*1 + 1*2 + 1*2^2] + (n-4) * [2*2 + 1*2^2 + 1*2^3] + (n-5) *[2*2^2 + 1*2^3 + 1*2^4] + ...
 *              3*[2*2^(n-6) + 1*2^(n-5) + 1*2^(n-4)] + 2 *[2*2^(n-5) + 1*2^(n-4)] + 1 * [2^1 * 2^(n-4)]
 * S2-S1 =  (n-2) * 2 + [(n-3) *2 + (n-4) * 2^2 + (n-5) * 2^3 + .... +3* 2^(n-5)]      - 2 * 2^(n-4).
 *       =  (n-2) *2  + [ 2^(n-1) -1  - {(n-1) + (n-2) + 2 * 2^(n-4) + 1 * 2^(n-3) } ] - 2*2^(n-4)
 *       =  2^(n-1) -2 - 3 * 2 ^(n-3)
 *       = 2^(n-3) (2^2-3) -2
 *       = 2^(n-3) - 2.
 */

public class SummingPieces {

    private static final int MOD = 1000000007;
    private long [] partitions; //#of ways to split the array of size n into contiguous pieces
    private int n;
    private long[] windowSizeTimesNumOfPieces;
    private long [] twoPowers;

    public SummingPieces(int n, boolean optimize){
        this.n = n;
        partitions = new long[n+1];
        twoPowers = new long[n+1];
        partitions[0] = 1;
        partitions[1] = twoPowers[0] = 1;
        for(int i=2; i<=n; ++i){
            partitions[i] = partitions[i-1] *2; //2^(i-1) - There are i-1 gaps between i numbers; you can pick or do not pick them.
            partitions[i] %= MOD;
            twoPowers[i-1] = partitions[i];
        }
        twoPowers[n] = (partitions[n] * 2) % MOD;
        windowSizeTimesNumOfPieces = (n%2) == 0 ? new long[n/2] : new long [(n/2)+1];
        if(!optimize) method1();
        else optimizeMethod();
    }

    private void optimizeMethod() {
        windowSizeTimesNumOfPieces[0] = twoPowers[n] - 1  ;  // 2^(n)-1
        for(int index=1; index<windowSizeTimesNumOfPieces.length; ++index){
            // window[i] = window[i-1] + 2^(n-1-i) - 2 ^(i-1)
            windowSizeTimesNumOfPieces[index] = (windowSizeTimesNumOfPieces[index-1] + twoPowers[n-1-index]) % MOD;
            windowSizeTimesNumOfPieces[index] -= twoPowers[index-1];
            windowSizeTimesNumOfPieces[index] = (windowSizeTimesNumOfPieces[index] + MOD) % MOD;
            //System.out.println("index: " + index + " " + windowSizeTimesNumOfPieces[index]);
        }
    }

    public void method1(){
        for(int index=0; index<windowSizeTimesNumOfPieces.length; ++index){
            windowSizeTimesNumOfPieces[index] = 0;
            for(int pieceSize=1; pieceSize<=n; ++pieceSize){
                windowSizeTimesNumOfPieces[index] += (window(index, pieceSize) * pieceSize) % MOD;
                windowSizeTimesNumOfPieces[index] %= MOD;
            }
            //System.out.println("index: " + index + " " + windowSizeTimesNumOfPieces[index]);
        }
    }

    /**
     * Returns number of pieces in which element at index is in a pieces of specified size.
     * @param index
     * @param windowSize
     * @return
     */
    public long window(int index, int windowSize){

        long numberOfSplits = 0; //number of splits containing this index element trapped in Piece of size windowSize.
        for(int leftWindowIdx=Math.max(0,index-windowSize+1); leftWindowIdx<=index; ++leftWindowIdx){
            int rightWindowIdx = leftWindowIdx+windowSize-1;
            if(rightWindowIdx<n){
                int leftArraySize = (leftWindowIdx-1)- 0 +1 ;
                int rightArraySize = n-1-(rightWindowIdx+1)+1;
                //System.out.println("Window:" + leftArraySize + " " + rightArraySize);
                numberOfSplits += (partitions[leftArraySize] * partitions[rightArraySize]) % MOD;
                numberOfSplits %= MOD;
            }
        }

        //System.out.println("Index: " + index + " windowSize: " + windowSize + " splits " + numberOfSplits);
        return numberOfSplits;
    }

    public long getContributionAtIdx(int index){
        if(index< windowSizeTimesNumOfPieces.length) return windowSizeTimesNumOfPieces[index];
        else return windowSizeTimesNumOfPieces[n-index-1];
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        SummingPieces sp = new SummingPieces(n, true);
        long ret =0;
        for(int i = 0; i < n; i++){
            int val = in.nextInt();
            ret = ret + (sp.getContributionAtIdx(i) * val) % MOD ;
            ret %= MOD;
        }
        System.out.println(ret);
    }
}
/**
48
477 392 161 421 245 50 530 889 750 16 545 303 898 785 162 279 677 664 126 149 814 360 334 681 473 293 267 120 825 21 267 301 413 779 73 657 181 602 897 930 969 441 232 218 577 745 848 253

Ans: 868784194 **/
