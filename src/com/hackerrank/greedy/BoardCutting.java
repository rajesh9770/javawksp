package com.hackerrank.greedy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Created by Rajesh on 6/5/2018.
 */
public class BoardCutting {

    public static class Pair<K, V>{
        public K key;
        public V val;
        public Pair(K k , V v){
            this.key = k;
            this.val = v;
        }
    }

    // Complete the boardCutting function below.
    static long boardCutting(int[] cost_y, int[] cost_x) {
        long MODULO = 1000000007l;
        PriorityQueue<Pair<Integer, Character>> costs = new PriorityQueue<>(10, new Comparator<Pair<Integer, Character>>() {
            @Override
            public int compare(Pair<Integer, Character> o1, Pair<Integer, Character> o2) {
                return o2.key-o1.key;
            }
        });

        for(int y: cost_y) costs.add(new Pair<>(y, 'y'));
        for(int x: cost_x) costs.add(new Pair<>(x, 'x'));
        long cutsAlongX = 0;
        long cutsAlongY = 0;
        long cost = 0;
        while(!costs.isEmpty()){
            Pair<Integer, Character> minCostCut = costs.poll();
            if(minCostCut.val == 'x'){
                ++cutsAlongX;
                cost += ((((cutsAlongY+1)%MODULO) * (minCostCut.key % MODULO)) %MODULO);
            }else{
                ++cutsAlongY;
                cost += (((cutsAlongX+1)*(minCostCut.key%MODULO)) %MODULO);
            }
            cost %= MODULO;
        }
        return cost;

    }



    private static void test(Pair<Integer, Character> [] sorted){

        int horizontalCuts = 0, verticalCuts = 0;
        int minCost = 0;
        for(Pair<Integer, Character> a: sorted){
            if(a.val == 'x'){//vertical cut
                ++verticalCuts;
                minCost += (horizontalCuts+1) * a.key;
            }else{
                ++horizontalCuts;
                minCost += (verticalCuts+1) * a.key;
            }

        }
    }
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            String[] mn = scanner.nextLine().split(" ");

            int m = Integer.parseInt(mn[0]);

            int n = Integer.parseInt(mn[1]);

            int[] cost_y = new int[m-1];

            String[] cost_yItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < m-1; i++) {
                int cost_yItem = Integer.parseInt(cost_yItems[i]);
                cost_y[i] = cost_yItem;
            }

            int[] cost_x = new int[n-1];

            String[] cost_xItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n-1; i++) {
                int cost_xItem = Integer.parseInt(cost_xItems[i]);
                cost_x[i] = cost_xItem;
            }

            long result = boardCutting(cost_y, cost_x);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}
