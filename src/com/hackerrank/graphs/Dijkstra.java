package com.hackerrank.graphs;



import javafx.util.Pair;

import java.util.*;

public class Dijkstra {
    /**
     * You are given an undirected weighted graph of n nodes (0-indexed),
     * represented by an edge list where edges[i] = [a, b] is an undirected edge connecting the nodes a and b
     * with a probability of success of traversing that edge succProb[i].
     *
     * Given two nodes start and end, find the path with the maximum probability of success to go from start to end and return its success probability.
     *
     * If there is no path from start to end, return 0. Your answer will be accepted if it differs from the correct answer by at most 1e-5.
     * @param n
     * @param edges
     * @param succProb
     * @param start_node
     * @param end_node
     * @return
     */
    public static double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
        List<Pair<Integer, Double>>[] adjList = new List[n];
        for(int i=0; i<n; ++i){
            adjList[i] = new ArrayList<>();
        }

        for(int i=0; i<edges.length; ++i){
            int[] edge = edges[i];
            //add an edge between (edge0, edge1) with probablity succProb[i]
            adjList[edge[0]].add(new Pair(edge[1], succProb[i]));
            adjList[edge[1]].add(new Pair(edge[0], succProb[i]));
        }
        //max heap; since we want maximum probability. Also need to store the probability in this queue, since we need to sort on probability
        PriorityQueue<Pair<Integer, Double>> priorityQueue = new PriorityQueue<>(new Comparator<Pair<Integer, Double>>() {
            @Override
            public int compare(Pair<Integer, Double> o1, Pair<Integer, Double> o2) {
                return ((Double) (o2.getValue())).compareTo((Double) (o1.getValue()));
            }
        });

        double probs[] = new double[n];
        Arrays.fill(probs, 0d);

        priorityQueue.add(new Pair(start_node, 1d));
        probs[start_node] = 1d;

        //boolean explored[] = new boolean[n];

        while(!priorityQueue.isEmpty()){
            Pair<Integer, Double> parent = priorityQueue.poll();
            if(parent.getKey() == end_node) return parent.getValue();
            if(parent.getValue() < probs[parent.getKey()]) continue;
            for (Pair<Integer, Double> child : adjList[parent.getKey()]) {
                //find the new probability to this child node
                Double prob =   parent.getValue() * child.getValue();
                if(prob > probs[child.getKey()]){//if new prob is higher only then add to the queue
                    probs[child.getKey()] = prob;
                    priorityQueue.add(new Pair(child.getKey(), prob)); // there is a chance that the same node exists in the queue,
                    // but that will have lower probability. Instead of removing it from the queue, when it comes to front of the list
                    // it will not be processed.
                }
            }
        }
        return 0d;
    }

    public static void main1(String[] args) {

//        PriorityQueue<Pair<Integer, Double>> priorityQueue = new PriorityQueue<>(new Comparator<Pair<Integer, Double>>() {
//            @Override
//            public int compare(Pair<Integer, Double> o1, Pair<Integer, Double> o2) {
//                return ((Double) (o2.getValue())).compareTo((Double) (o1.getValue()));
//            }
//        });
//        priorityQueue.add(new Pair<>(1, .2));
//        priorityQueue.add(new Pair<>(2, .25));
//        priorityQueue.add(new Pair<>(3, .50));
//        priorityQueue.add(new Pair<>(4, .1));
//        while (!priorityQueue.isEmpty()){
//            System.out.println(priorityQueue.poll());
//        }
        int n = 3;
        int [][] edges = new int [][] {{0,1},{1,2},{0,2}};
        double [] probs  = new double[]{0.5,0.5,0.2};
        int start = 0;
        int end  = 2;
        double v = maxProbability(n, edges, probs, start, end);

    }

    public static void main(String[] args) {
        int [][] edges = new int [][] {{0,1,1000000000},{0,3,1000000000},{1,3,1000000000},{1,2,1000000000},{1,5,1000000000},{3,4,1000000000},{4,5,1000000000},{2,5,1000000000}};
        int ct = countPaths(6, edges);
        System.out.println(ct);
    }

    /**
     * You are in a city that consists of n intersections numbered from 0 to n - 1 with bi-directional
     * roads between some intersections.
     * The inputs are generated such that you can reach any intersection from any other intersection
     * and that there is at most one road between any two intersections.
     *
     * You are given an integer n and a 2D integer array roads where roads[i] = [ui, vi, timei]
     * means that there is a road between intersections ui and vi that takes timei minutes to travel.
     * You want to know in how many ways you can travel from intersection 0 to intersection n - 1
     * in the shortest amount of time.
     *
     * Return the number of ways you can arrive at your destination in the shortest amount of time.
     * Since the answer may be large, return it modulo 109 + 7.
     *
     *
     */

    public static int countPaths(int n, int[][] roads) {
        List<Pair<Integer, Long>>[] adjList = new List[n];
        for(int i=0; i<n; ++i){
            adjList[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            adjList[road[0]].add(new Pair(road[1], (long)(road[2])));
            adjList[road[1]].add(new Pair(road[0], (long)(road[2])));
        }

        //min heap - minimum is at the top of the heap
        PriorityQueue<Pair<Integer, Long>> priorityQueue
                = new PriorityQueue(new Comparator<Pair<Integer, Long>>() {
            @Override
            public int compare(Pair<Integer, Long> o1, Pair<Integer, Long> o2) {
                return ((Long) (o1.getValue())).compareTo((Long) (o2.getValue()));
            }
        });

        priorityQueue.add(new Pair<>(0, 0L));
        long [] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;
        int [] ways = new int[n];
        Arrays.fill(ways, 0);
        ways[0] =1;
        while (!priorityQueue.isEmpty()){
            Pair<Integer, Long> next = priorityQueue.poll();
            int parentNode = next.getKey();
            long distRecordedWhenAddedToQ= next.getValue();
            if(distRecordedWhenAddedToQ> dist[parentNode]) continue; // d can only be d >= dist[parentNode], since we record the min dist in dist[parentNode] immediately.
            for (Pair<Integer, Long> neighbor : adjList[parentNode]) {
                int neighborNode = neighbor.getKey();
                Long value = neighbor.getValue();
                long distToNeighbor = distRecordedWhenAddedToQ + value;
                if (distToNeighbor < dist[neighborNode]) {//we found path with low dist
                    dist[neighbor.getKey()] = distToNeighbor;
                    priorityQueue.add(new Pair<>(neighborNode, distToNeighbor));
                    ways[neighborNode] = ways[parentNode]; //since this is the first time we discovered the low dist path, set the ways
                } else if (distToNeighbor == dist[neighbor.getKey()]) {
                    ways[neighborNode] = (ways[neighborNode] + ways[parentNode]) % 1_000_000_007; //we found more ways to get to this node
                }
            }
        }
        return ways[n-1];
    }


}
