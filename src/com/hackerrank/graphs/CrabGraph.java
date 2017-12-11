package com.hackerrank.graphs;

import com.hackerrank.utils.FordFulkerson;

import java.util.Scanner;

/**
 * Created by Rajesh on 11/22/2017.
 */
public class CrabGraph {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int testcases = in.nextInt();

        while(testcases-->0){
            int n = in.nextInt();
            int t = in.nextInt();
            int e = in.nextInt();
            int [][] graph = new int [2*n+2][2*n+2]; //0 is source, 2, 4, .... 2*n are original vertices,
            // 1,3, ..., 2n-1 are their avatars and 2*n+1 is target
            int [] degrees = new int[n+1];
            while(e-->0){
                int u = in.nextInt();
                int v = in.nextInt();
                degrees[u]++; degrees[v]++;
                graph[2*u][2*v-1] = 1; //add an edge between 2u and 2v-1
                graph[2*v][2*u-1] = 1; //add an edge between 2v and 2u-1
            }
            //connect source to even vertices with capacity Min(t, degree(node))
            for(int i=1; i<=n; ++i){
                graph[0][2*i] = Math.min(t, degrees[i]);
            }
            //connect odd vertices to target with capacity 1
            for(int i=1; i<=n; ++i){
                graph[2*i-1][2*n+1] = 1;
            }
//            for(int i=0; i<=2*n+1; ++i){
//                if(i==0){
//                    System.out.printf("%4s", "X");
//                    for(int j=0; j<=2*n+1; ++j)
//                        System.out.printf("%4d", j);
//                    System.out.println();
//                }
//                System.out.printf("%4d", i);
//                for(int j=0; j<=2*n+1; ++j){
//                    System.out.printf("%4d", graph[i][j]);
//                }
//                System.out.println();
//            }
//            System.out.println();
            FordFulkerson ff = new FordFulkerson(2*n+2);
            int flow = ff.fordFulkerson(graph, 0, 2 * n + 1);
            System.out.println(flow);
        }


    }
}
