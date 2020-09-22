package com.hackerrank.dynamicprog;

import java.util.Arrays;
import java.util.Scanner;


/*
 * Let D={d1, d2, ..., dk} be a finite set of distinct coin denominations
 * Each denomination is available in unlimited quantity.
 * Make change for n cents, using a minimum total number of coins.
 * Assume that d1 = 1 so that there is always a solution
 */
public class CoinChangeMinimumCoins {
    int total ;
    int [] coins ;

    public CoinChangeMinimumCoins(int n, int [] c) {
        this.total = n;
        this.coins = c;
    }
    
    private long process() {
        long [][] minCoins = new long[total+1][coins.length];
        // if total is 0, there is only one way to make change i.e. take no coins
        for(int c=0; c<coins.length; ++c)
            minCoins[0][c] = 0;
        // for total t, if the first coin value divides t, then # of first coins required is t/coins[0],
        // otherwise it's not possible
        for(int t=1; t<total+1; ++t){
            minCoins[t][0] = (t%coins[0]) == 0 ? t/coins[0] : Integer.MAX_VALUE;
        }

        for(int t=1; t<total+1; ++t){
                for(int c=1; c<coins.length; ++c){
                    if(coins[c] > t){//we can't take coin c
                        minCoins[t][c] = minCoins[t][c-1];
                    }
                    else{
                        //
                        minCoins[t][c] = Math.min(minCoins[t][c-1] , 1 +  minCoins[t-coins[c]][c]);
                    }
                }
        }

        return minCoins[total][coins.length-1];
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int coins[] = new int[m];
        for(int coins_i=0; coins_i < m; coins_i++){
            coins[coins_i] = in.nextInt();
        }
        Arrays.sort(coins);
        CoinChangeMinimumCoins driver = new CoinChangeMinimumCoins(n, coins);
        System.out.println(driver.process());

    }


    //Part 2
    //Given a number, Write an algorithm to find out minimum numbers required whose square is equal to the number.
    /**
    Given Number: 12

    Numbers whose sum of squares are equal to 12.

            1^2+1^2+1^2+1^2+1^2+1^2+1^2+1^2+1^2+1^2+1^2+1^2 = 12

            2^2+2^2+2^2 = 12

            3^2+1^2+1^2+1^2 = 12

     Answer: 3 numbers (2,2,2)
     Integer part of square root of 12 is : 3. So 1,2,3 are the numbers whose square sum can be made to 12.
     Instead of sum it's square sum. That's the change from coin change problem.
    **/

    private long minNumSquare(long N) {
        int numbers [] = new int [(int) Math.sqrt(N)];
        for (int i=0; i<numbers.length; ++i) numbers[i] = i+1; // [1, 2 ..., sqrt(N)] X [ 0, 1, 2, .... N]
        long [][] minCoins = new long[total+1][numbers.length];
        //if total is 0, there is only one way to make sum i.e. take no numbers
        for(int c=0; c<numbers.length; ++c)
            minCoins[0][c] = 0;
        // in this case first number is 1, so it's always possible to make sum.
        for(int t=1; t<N+1; ++t){
            minCoins[t][0] = t; // 1^2 + 1^2 + ... + 1^2 = t
        }

        for(int t=1; t<N+1; ++t){
            for(int c=1; c<numbers.length; ++c){
                if(numbers[c] * numbers[c] > t){//we can't take number c
                    minCoins[t][c] = minCoins[t][c-1];
                } else {
                    minCoins[t][c] = Math.min(minCoins[t][c - 1], 1 + minCoins[t - numbers[c] * numbers[c]][c]);
                }
            }
        }

        return minCoins[total][coins.length-1];
    }
}
