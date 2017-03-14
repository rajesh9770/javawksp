package com.hackerrank;

import java.util.Arrays;
import java.util.Scanner;


/*
 * Given a number of dollars  and a list of dollar values for  distinct coins, 
 * find and print the number of different ways you can make change for  dollars if each coin is available in an infinite quantity.
 */
public class CoinChange {
    int total ;
    int [] coins ;
    
    public CoinChange(int n, int [] c) {
        this.total = n;
        this.coins = c;
    }
    
    private long process() {
        long [][] ways = new long[total+1][coins.length];
        // if total is 0, there is only one way to make change i.e. take no coins
        for(int c=0; c<coins.length; ++c)
            ways[0][c] = 1;
        for(int t=1; t<total+1; ++t){
            ways[t][0] = (t%coins[0]) == 0 ? 1 : 0;
        }

        for(int t=1; t<total+1; ++t){
                for(int c=1; c<coins.length; ++c){
                    if(coins[c] > t){//we can't take coin c
                        ways[t][c] = ways[t][c-1];
                    }
                    else{
                        ways[t][c] = ways[t][c-1] + ways[t-coins[c]][c];
                    }
                }
        }
//        for(int i=0; i<total+1; ++i){
//                for(int j=0; j<coins.length; ++j)
//                    System.out.print(ways[i][j] + "\t");
//                System.out.println("");
//        }
        return ways[total][coins.length-1];
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
        CoinChange driver = new CoinChange(n, coins);
        System.out.println(driver.process());
        
        //245 26
        //16 30 9 17 40 13 42 5 25 49 7 23 1 44 4 11 33 12 27 2 38 24 28 32 14 50
        //64027917156
    }

}
