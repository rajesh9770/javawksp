package com.hackerrank.dynamicprog;


import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Rajesh on 10/3/2017.
 * Given a sequence of words, and a limit on the number of characters that can be put in one line (line width). Put line breaks in the given sequence such that the lines are printed neatly.
 * Assume that the length of each word is smaller than the line width.
 * The extra spaces includes spaces put at the end of every line except the last one.
 * The problem is to minimize the following total cost.
 * Cost of a line = (Number of extra spaces in the line)^3
 * Total Cost = Sum of costs for all lines
 * http://www.geeksforgeeks.org/dynamic-programming-set-18-word-wrap/
 */
public class WordWrap {

    private int [] words;
    private int lineLength;

    public WordWrap(int [] words, int lineLength){
        this.words = words;
        this.lineLength = lineLength;
    }

    private void solve() {
        // for i and j find the extra spaces on a line after putting words from i to j in a single line
        // here i, j runs from 0 to N-1
        int len = words.length;
        int extraspaces [][] = new int[len][len];
        for(int i=0; i<len; ++i){
            extraspaces[i][i] = lineLength - words[i];
            for(int j=i+1; j<len; ++j){
                extraspaces[i][j] = extraspaces[i][j-1]  - (words[j] + 1); // 1 for space after previous word words[j-1]
                //extraspaces can be negative if words can't fit on a line. This prevents them from being considered in the optimal solution.
            }
        }
        // for i and j find the cost of putting words from i to j in a single line
        // here i, j runs from 0 to N-1
        int cost [][] = new int[len][len];
        for(int i=0; i<len; ++i){
            for(int j=i; j<len; ++j) {
                if (extraspaces[i][j] < 0) cost[i][j] = Integer.MAX_VALUE;
                else if (j == len - 1){
                    cost[i][j] = 0; // last word is on last line - no cost here.
                    // This prevents arrangement of the last line from influencing the sum being minimized.
                }
                else {
                    cost[i][j] = extraspaces[i][j] * extraspaces[i][j];
                }
            }
        }


        // for i from 0 to N-1, find the cost of putting words from 0 to i.
        // if last line contains word from i to N-1, then cost is C = c[i-1] + cost[i][N-1]
        // optimize this by finding the i that minimizes this i.e. C = min { c[i-1] + cost[i][N-1] }
        // where i runs from 0 to N-2

        /**
         * To keep track of what words go on what lines, we can keep a parallel p table that
         points to where each c value came from. When minCost[i] is computed, if minCost[i] is based
         on the value of minCost[j-1], set p[i] to j. Then after minCost[len-1] is computed, we can trace
         the pointers to see where to break the lines. The last line starts at word p[len-1] and
         goes through word len-1. The previous line starts at word p[p[len-1]] and goes through
         word p[len-1]-1, etc.
         ***/

        int minCost[] = new int[len];
        int positions[] = new int[len];
        minCost[0] = cost[0][0];
        positions[0] = 0;
        for(int i=1; i<len; ++i){
           minCost[i] = cost[0][i];
           positions[i] = 0;
           for(int j=1; j<=i; ++j){
               if(cost[j][i] < Integer.MAX_VALUE) {
                   if(minCost[j - 1] + cost[j][i] < minCost[i]){
                       minCost[i] = minCost[j - 1] + cost[j][i];
                       positions[i] = j;
                   }
               }
           }
        }
//        for(int a: minCost)
//            System.out.println(a);
        System.out.println("Min cost: " + minCost[len-1]);

        int last = len;
        do{
            --last;
            System.out.println(positions[last] + " to " + last);
            last = positions[last];
        }while(last != 0);

    }
    public static void main(String[] args) {
        String str = "Geeks for Geeks presents word wrap problem";
        String[] split = str.split(" ");
        int [] wordLengths = Arrays.stream(split).mapToInt(String::length).toArray();
        for(int a: wordLengths)
            System.out.print(a + " ");
        System.out.println("");
        WordWrap ww = new WordWrap( wordLengths, 15);
        ww.solve();
    }
}