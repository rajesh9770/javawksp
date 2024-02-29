package com.hackerrank.dynamicprog;

/**
 *https://www.geeksforgeeks.org/optimal-strategy-for-a-game-dp-31/?ref=leftbar-rightbar
 * https://www.geeksforgeeks.org/minimum-insertions-to-form-a-palindrome-dp-28/
 */
/**
 * Consider a row of n coins of values v1 . . . vn, where n is even.
 * We play a game against an opponent by alternating turns.
 * In each turn, a player selects either the first or last coin from the row,
 * removes it from the row permanently, and receives the value of the coin.
 * Determine the maximum possible amount of money we can definitely win if we move first.
 *
 * Note: The opponent is as clever as the user.
 *
 * Let us understand the problem with few examples:
 *
 * 5, 3, 7, 10 : The user collects maximum value as 15(10 + 5)
 * 8, 15, 3, 7 : The user collects maximum value as 22(7 + 15)
 *
 *
 */

/**
 * Approach: As both the players are equally strong, both will try to reduce the possibility of winning of each other.
 * Now let’s see how the opponent can achieve this.
 *
 * There are two choices:
 *
 * The user chooses the ‘ith’ coin with value ‘Vi’: The opponent either chooses (i+1)th coin or jth coin.
 * The opponent intends to choose the coin which leaves the user with minimum value.
 * i.e. The user can collect the value Vi + min(F(i+2, j), F(i+1, j-1) ).
 * coinGame1
 * The user chooses the ‘jth’ coin with value ‘Vj’: The opponent either chooses ‘ith’ coin or ‘(j-1)th’ coin.
 * The opponent intends to choose the coin which leaves the user with minimum value,
 * i.e. the user can collect the value Vj + min(F(i+1, j-1), F(i, j-2) ).
 * coinGame2
 * F(i, j) represents the maximum value the user
 * can collect from i'th coin to j'th coin.
 *
 * F(i, j) = Max(Vi + min(F(i+2, j), F(i+1, j-1) ),
 *               Vj + min(F(i+1, j-1), F(i, j-2) ))
 * As user wants to maximise the number of coins.
 *
 * Base Cases
 *     F(i, j) = Vi           If j == i
 *     F(i, j) = max(Vi, Vj)  If j == i + 1
 */
public class CoinGame {


    // Driver program
    public static void main(String[] args)
    {
        int arr1[] = { 8, 15, 3, 7 };
        int n = arr1.length;
        System.out.println( "" + optimalStrategyOfGame(arr1, n));

        int arr2[] = { 2, 2, 2, 2 };
        n = arr2.length;
        System.out.println( "" + optimalStrategyOfGame(arr2, n));

        int arr3[] = { 20, 30, 2, 2, 2, 10 };
        n = arr3.length;
        System.out.println( "" + optimalStrategyOfGame(arr3, n));
    }

    private static int optimalStrategyOfGame(int[] arr, int n) {
        int [][] table = new int[n][n];
        for(int i=0; i<n; ++i) {
            table[i][i] = arr[i];
        }
        for(int i=0; i<n-1; ++i) {
            table[i][i+1] = Math.max(arr[i], arr[i+1]);
        }

        for(int k=2; k<n; ++k){
            for(int i=0, j=k; j<n; ++i, ++j){
                //F(i, j) = Max(Vi + min(F(i+2, j), F(i+1, j-1) ),
                //              Vj + min(F(i+1, j-1), F(i, j-2) ))
                int x = i+2 <= j ? table[i+2][j] : 0;
                int y = i+1 <= j-1 ? table[i+1][j-1] : 0;
                int z = i <= j-2 ? table[i][j-2] : 0;
                table[i][j] = Math.max(arr[i] + Math.min(x,y),
                        arr[j] + Math.min(y,z));
            }
        }
        return table[0][n-1];
    }
}
