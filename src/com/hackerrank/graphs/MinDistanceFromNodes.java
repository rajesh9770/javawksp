package com.hackerrank.graphs;

import java.util.Arrays;

public class MinDistanceFromNodes {
    /**
     * You are given a directed graph of n nodes numbered from 0 to n - 1, where each node has at most one outgoing edge.
     *
     * The graph is represented with a given 0-indexed array edges of size n, indicating that there is a directed edge from node i to node edges[i].
     * If there is no outgoing edge from i, then edges[i] == -1.
     *
     * You are also given two integers node1 and node2.
     *
     * Return the index of the node that can be reached from both node1 and node2, such that the maximum between the distance from node1 to that node,
     * and from node2 to that node is minimized. If there are multiple answers, return the node with the smallest index,
     * and if no possible answer exists, return -1.
     *
     * Note that edges may contain cycles.
     */

    public int closestMeetingNode(int[] edges, int node1, int node2) {
        /**
         * We run DFS independently for node 1 and 2, memoising the distance to each node we reach.
         *
         * Then, we check each node, track and return the index of the one with minimum max distance.
         */
        int[] distance1 = DFS(edges, node1);
        int[] distance2 = DFS(edges, node2);

        int minDist = Integer.MAX_VALUE;
        int ans=-1;
        for(int i=0; i<edges.length; ++i){
            if(Math.min(distance1[i], distance2[i]) >=0 && Math.max(distance1[i], distance2[i]) < minDist){
                minDist = Math.max(distance1[i], distance2[i]);
                ans = i;
            }
        }
        return ans;
    }

    public static int[] DFS(int[] edges, int node){
        int [] distances = new int[edges.length];
        Arrays.fill(distances,-1);
        int dist = 0;
        while(node != -1 && distances[node] == -1){
            distances[node] = dist;
            node = edges[node];
            ++dist;
        }
        return distances;
    }
}
