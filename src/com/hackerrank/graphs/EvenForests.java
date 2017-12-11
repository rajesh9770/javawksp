package com.hackerrank.graphs;

import java.util.Scanner;

/**
 * Created by Rajesh on 12/9/2017.
 */
public class EvenForests {

    public static int forests = 0;
    public static boolean[] visited ;

    /**
     * Return the number of nodes in subtree rooted at the root idx after prunning the subtrees that have even nodes.
     * @param graph
     * @param rootidx
     * @return
     */
    public static int DFS(boolean graph[][], int rootidx){
        visited[rootidx] = true;
        int nodes=1; // count the starting node
        for(int i=1; i<graph.length; ++i){
            if(graph[rootidx][i] && visited[i] == false){
                int nodesInSubTree = DFS(graph, i);
                if(nodesInSubTree % 2 == 0) forests++; // do not count this subtree, since it will be prunned.
                else nodes += nodesInSubTree; // this subtree rooted at i will still be part of a tree rooted at rootidx.
            }
        }
        return nodes;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n=sc.nextInt();
        int m=sc.nextInt();
        boolean arr[][]= new boolean [n+1][n+1];
        visited = new boolean[n+1];
        for(int i=0;i<m;i++){
            int a=sc.nextInt();
            int b=sc.nextInt();
            arr[a][b]=true;
            arr[b][a]=true;
        }
        DFS(arr, 1);
        System.out.println(forests);
    }
}
