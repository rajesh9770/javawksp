package com.hackerrank.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a directed acyclic graph (DAG) of n nodes labeled from 0 to n - 1,
 * find all possible paths from node 0 to node n - 1 and return them in any order.
 *
 * The graph is given as follows: graph[i] is a list of all nodes you can visit from node i
 * (i.e., there is a directed edge from node i to node graph[i][j]).
 */
public class DAGAllPaths {
    public static List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        int n = graph.length;
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> currDFSPath = new ArrayList<>();
        currDFSPath.add(0);
        DFS(graph, ans, currDFSPath, 0);
        return ans;
    }

    public static void DFS(int[][]g, List<List<Integer>> ans, List<Integer> currPath, int currNode){
        if(currNode == g.length-1){
            ans.add(new ArrayList<>(currPath));
            return;
        }
        for (int i : g[currNode]) {
            currPath.add(i);
            DFS(g, ans, currPath, i);
            currPath.remove(currPath.size()-1);
        }
    }

    public static void main(String[] args) {
        int [][] g = new int[][] {{1,2},{3},{3},{}};
        System.out.println(allPathsSourceTarget(g));
    }
}
