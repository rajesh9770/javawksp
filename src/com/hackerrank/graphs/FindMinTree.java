package com.hackerrank.graphs;

import java.util.*;

public class FindMinTree {
    /**
     * Problem: 310 https://leetcode.com/problems/minimum-height-trees/
     * A tree is an undirected graph in which any two vertices are connected by exactly one path.
     * In other words, any connected graph without simple cycles is a tree.
     *
     * Given a tree of n nodes labelled from 0 to n - 1,
     * and an array of n - 1 edges where edges[i] = [ai, bi] indicates that there is an undirected edge between the two nodes ai and bi in the tree,
     * you can choose any node of the tree as the root.
     * When you select a node x as the root, the result tree has height h.
     * Among all possible rooted trees, those with minimum height (i.e. min(h))  are called minimum height trees (MHTs).
     *
     * Return a list of all MHTs' root labels. You can return the answer in any order.
     *
     * The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.
     * @param n
     * @param edges
     * @return
     */
    public List<Integer> findMinHeightTrees(int n, int[][] edges){
        if (n == 1) return Collections.singletonList(0);

        Set<Integer> adj[] = new Set[n];
        for(int i=0; i<n; ++i){
            adj[i] = new HashSet<>();
        }
        for(int [] edge: edges){
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }
        //Basically, the idea is to eat up all the leaves at the same time, until one or two leaves are left
        List<Integer> leaves = new ArrayList<>();
        for(int i=0; i<n; ++i){
            if(adj[i].size() == 1) leaves.add(i);
        }

        while(leaves.size() > 2){
            List<Integer> newLeaves = new ArrayList<>();
            for (Integer leaf : leaves) {//remove this leaf
                Integer next = adj[leaf].iterator().next();//should have only one, since leaf is a 'leaf'
                adj[next].remove(leaf);
                if(adj[next].size() == 1){
                    newLeaves.add(next);
                }
            }
            leaves = newLeaves;
        }
        return leaves;
    }
}
