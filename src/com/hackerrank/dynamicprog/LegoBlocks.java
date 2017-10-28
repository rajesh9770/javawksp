package com.hackerrank.dynamicprog;

import java.util.Scanner;

/**
 * Created by Rajesh on 10/28/2017.
 */
public class LegoBlocks {

    long MOD = 1000000007;
    long [] rowCombinations; //for each width upto w, fixed height =1
    long [] totals; //for each width upto w, fixed height =h
    long [] badCombinations; // for each width upto w, fixed height =h
    int height ;
    int width ;

    public static long modpow(long base_value, long exponent, long modulus){
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

    public LegoBlocks(int h, int w) {
        height = h;
        width = w;
        rowCombinations = new long[Math.max(width+1, 4)];
        totals = new long[width+1];
        badCombinations = new long[width+1];
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for(int i = 0; i < q; i++){
            int h = in.nextInt();
            int w = in.nextInt();
            LegoBlocks legoBlocks = new LegoBlocks(h,w);
            legoBlocks.buildLego();
        }
    }

    private void buildLego() {
            buildSingleRowCombinations();
            buildHRowsCombinations();
            buildBadCombinations();
            System.out.println((totals[width]-badCombinations[width]+MOD) % MOD);
    }

    /**
     * Bad combinations are those that can be split vertically into more than one vertical split without
     * cutting any lego block i.e that vertical split with divide the original combination into two halves of lower width.
     * Each such bad combination will have a left most vertical split where leftmost combination of width w1 (< width) will be a good combination.
     */
    private void buildBadCombinations() {
        badCombinations[0]=0;
        badCombinations[1]=0;
        for(int i=2;i<=width; ++i){
            badCombinations[i] =0;
            // 0 1 2 ....i
            //leftmost split can occur after 1 or 2, ..., i-1
            //corresponding widths of leftmost block will be 1, 2 , ... i-1
            for(int leftmostSplit =1; leftmostSplit<i; ++leftmostSplit){
                int widthOfLeftMostGoodBlock= leftmostSplit;
                badCombinations[i] += ( (totals[widthOfLeftMostGoodBlock]-badCombinations[widthOfLeftMostGoodBlock]) * (totals[i-widthOfLeftMostGoodBlock]) ) % MOD;
                badCombinations[i] %= MOD;
            }
        }
    }

    private void buildHRowsCombinations() {
        for(int i=0; i<=width; ++i){
            // for h-rows with width i, there are (rowWithWidth_i_Combinations ^ h) possibilities
            totals[i] = modpow(rowCombinations[i], height, MOD);
        }
    }

    private void buildSingleRowCombinations() {
        rowCombinations[0]=1;
        rowCombinations[1]=1;
        rowCombinations[2]=2;
        rowCombinations[3]=4;
        for(int i=4; i<=width; ++i){
            //last lego on the row could be of width 1, 2, 3, or 4.
            rowCombinations[i]  = rowCombinations[i-1];
            rowCombinations[i] += rowCombinations[i-2];
            rowCombinations[i] %= MOD;
            rowCombinations[i] += rowCombinations[i-3];
            rowCombinations[i] %= MOD;
            rowCombinations[i] += rowCombinations[i-4];
            rowCombinations[i] %= MOD;
        }
    }
}
