package com.hackerrank.greedy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rajesh on 6/5/2018.
 */
public class GreedyFlorist {

    // Complete the getMinimumCost function below.
    static int getMinimumCost(int n, int k, int[] c) {
        Arrays.sort(c);
        for(int i=0; i<c.length/2; ++i){
            int tmp = c[i];
            c[i] = c[c.length-i-1];
            c[c.length-i-1] = tmp;
        }
        int cost = 0;
        for(int i = 0; i<n; ++i){
            cost += (i/k + 1) *c[i];

        }
        return cost;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nk = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nk[0]);

        int k = Integer.parseInt(nk[1]);

        int[] c = new int[n];

        String[] cItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int cItem = Integer.parseInt(cItems[i]);
            c[i] = cItem;
        }

        int minimumCost = getMinimumCost(n, k, c);

        bufferedWriter.write(String.valueOf(minimumCost));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
