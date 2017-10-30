package com.hackerrank.dynamicprog;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rajesh on 10/28/2017.
 */
public class SummingPieces {

    private static final int MOD = 1000000007;
    private long [] partitions; //#of ways to split the array of size n into contiguous pieces
    private int n;
    private long[] windowSizeTimesNumOfPieces;

    public SummingPieces(int n){
        this.n = n;
        partitions = new long[n+1];
        partitions[0] =1;
        partitions[1] =1;
        for(int i=2; i<=n; ++i){
            partitions[i] = partitions[i-1] *2; //2^(i-1) - There are i-1 gaps between i numbers; you can pick or do not pick them.
            partitions[i] %= MOD;
        }
        windowSizeTimesNumOfPieces = (n%2) == 0 ? new long[n/2] : new long [(n/2)+1];
        for(int index=0; index<windowSizeTimesNumOfPieces.length; ++index){
            windowSizeTimesNumOfPieces[index] = 0;
            for(int pieceSize=1; pieceSize<=n; ++pieceSize){
                windowSizeTimesNumOfPieces[index] += (window(index, pieceSize) * pieceSize) % MOD;
                windowSizeTimesNumOfPieces[index] %= MOD;
            }
            System.out.println("index: " + index + " " + windowSizeTimesNumOfPieces[index]);
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
        SummingPieces sp = new SummingPieces(n);
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
