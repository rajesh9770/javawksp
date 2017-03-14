package com.hackerrank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

//https://github.com/derekhh/HackerRank/blob/master/cut-the-tree.cpp
public class Graph<T,E> {

    private Map<E, Node<T,E>> nodes;

    public static class Node<T, E>{
        E id;
        T data;
        Collection<Node<T,E>> adjList;

        public Node(T data, E id) {
            this.data = data;
            this.id = id;
            adjList = new ArrayList<Node<T,E>>();
        }

        public boolean addEdge(Node<T,E> child) {
            return adjList.add(child);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Node<?, ?> && id.equals(((Node<?, ?>)obj).id)){
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }

    public Graph() {
        nodes = new HashMap<E, Node<T, E>>();
    }

    public Node<T, E> addNode(Node<T,E> node) {
        return nodes.put(node.id, node);
    }

    public boolean addEdge(Node<T,E> p, Node<T,E> c) {
        return p.addEdge(c);
    }

    public Node<T, E> get(E id) {
        return nodes.get(id);
    }
    
    public long DFS(Node<Integer,E> node, Map<E, Boolean> explored, Map<E, Long> treeSum){
        long ret = 0;
        explored.put(node.id, true);
        
        for(Node<Integer,E> adNode:node.adjList){
            Boolean exp = explored.get(adNode.id);
            if(exp == null || !exp)
                ret += DFS(adNode, explored, treeSum);
        }
        ret += node.data;
        treeSum.put(node.id, ret);
        return ret;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        Graph<Integer, Integer> g = new Graph<>();
        long sum = 0l;
        for(int i=0; i<N; ++i){
            int wgt= in.nextInt(); 
            g.addNode(new Node<Integer, Integer>(wgt, i+1));
            sum += wgt;
        }
        for(int i=0; i<N-1; ++i){
            int node1 = in.nextInt();
            int node2 = in.nextInt();
            g.addEdge(g.get(node1), g.get(node2));
            g.addEdge(g.get(node2), g.get(node1));
        }
        Map<Integer, Boolean> explored = new HashMap<Integer, Boolean>();
        Map<Integer, Long> sums = new HashMap<Integer, Long>();
        long rootSum = g.DFS(g.get(1), explored, sums);
        if(rootSum != sum) System.out.println("rootSum: " + rootSum + " " + sum);
        
        long diff = Long.MAX_VALUE;
        for(int i=2; i<=N; ++i){
            diff = Math.min(Math.abs(sum- sums.get(i) -sums.get(i)), diff); 
        }
        System.out.println(diff);
        in.close();
    }
}