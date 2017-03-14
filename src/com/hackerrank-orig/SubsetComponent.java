package com.hackerrank;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.math.*;

public class SubsetComponent {

    Graph<Integer> graph;
    public static class Graph<T>{
        List<Node<T>> nodes;

        public Graph(){
            nodes = new ArrayList<SubsetComponent.Graph.Node<T>>();
        }

        public Node<T> addNode(T data){
            Node<T> node = new Node<T>(data);
            nodes.add(node);
            return node;
        }

        public boolean addEdge(Node<T> i, Node<T> j){
            return i.addAdjNode(j) &&j.addAdjNode(i);
        }
        
        public void removeEdge(Node<T> i, Node<T> j){
            i.removeAdjNode(j);
            j.removeAdjNode(i);
        }
        
        public boolean addEdge(int i, int j) {
            return addEdge(nodes.get(i), nodes.get(j));
        }
        
        public void removeEdge(int i, int j) {
            removeEdge(nodes.get(i), nodes.get(j));
        }
        
        public void DFS(Node<T> node, Set<Node<T>> visited, List<Node<T>> span){
            visited.add(node);
            for(Node<T> neighbors : node.adj){
                if(!visited.contains(neighbors)){
                    DFS(neighbors, visited, span);
                }
            }
            span.add(node);
        }

        public List<List<Node<T>>> findConnectedComponents(){
            Set<Node<T>> visited = new HashSet<SubsetComponent.Graph.Node<T>>();
            List<List<Node<T>>> ret = new ArrayList<List<Node<T>>>();
            for(Node<T> node : nodes){
                if(!visited.contains(node)){
                    List<Node<T>> span = new ArrayList<SubsetComponent.Graph.Node<T>>();
                    ret.add(span);
                    DFS(node, visited,span);
                }
            }
            return ret;
        }
        
        private static class Node<T>{
            T data;
            Set<Node<T>> adj;
            int idx; //idx within nodes array

            public Node(T data) {
                this.data = data;
                adj = new HashSet<Node<T>>();
            }

            @Override
            public boolean equals(Object obj) {
                if(obj instanceof Node<?> && ((Node<?>)obj).data.equals(this.data)){
                    return true;
                }
                return false;
            }

            @Override
            public int hashCode() {
                return data.hashCode();
            }

            public boolean addAdjNode(Node<T> adjNode){
                return this.adj.add(adjNode);
            }

            public void removeAdjNode(Node<T> adjNode){
                this.adj.remove(adjNode);
            }

            public boolean isNeighbor(Node<T> other){
                return adj.contains(other);
            }
        }
    }

    public SubsetComponent(int n) {
        this.graph = new Graph<Integer>();
        for(int i=0; i<n; ++i){
            this.graph.addNode(i);
        }
    }

    public <E extends BitSet> long powerSet(E[] input, E[] output, int startIdx, int outIdx){
        long connectedComps = 0;
        for(int i=startIdx; i<input.length; ++i){
            output[outIdx] = input[i];
            List<Integer> newEdges = process(output, outIdx);
            List<List<com.hackerrank.SubsetComponent.Graph.Node<Integer>>> findConnectedComponents = graph.findConnectedComponents();
            connectedComps += findConnectedComponents.size();
            if(i+1 < input.length)
                  connectedComps += powerSet(input, output, i+1, outIdx+1);
            reset(newEdges);
        }
        return connectedComps;
    }

    private void reset(List<Integer> newEdges) {
        for(int i=1; i<newEdges.size(); ++i)
            graph.removeEdge(newEdges.get(0), newEdges.get(i));
    }

    private <E extends BitSet> List<Integer> process(E[] output, int outIdx) {
        E newlyAdded = output[outIdx];
        //System.out.println(newlyAdded);
        int startIdx = newlyAdded.nextSetBit(0);
        List<Integer> newEdges = new ArrayList<Integer>();
        newEdges.add(startIdx);
        for (int i = newlyAdded.nextSetBit(startIdx+1); i >= 0; i = newlyAdded.nextSetBit(i+1)) {
            if(graph.addEdge(startIdx, i)){
                newEdges.add(i);
            }
        }
        return newEdges;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        BitSet[] bSet = new BitSet[n];
        SubsetComponent driver = new SubsetComponent(64);
        long timeStart = System.currentTimeMillis();
        for(int i=0; i<n; ++i){
            BigInteger a = new BigInteger(in.next());
            bSet[i] = new BitSet();
            for(int k =0; k <64; ++k){
                if(a.testBit(k)) bSet[i].set(k);
            }
            //System.out.println(" a " + a + " " + bSet[i] );
        }
        in.close();
        System.out.println(">" + (System.currentTimeMillis()-timeStart));
        long powerSet = 64+ driver.powerSet(bSet, new BitSet[n], 0, 0);
        System.out.println(">" + (System.currentTimeMillis()-timeStart));
        System.out.println(powerSet);
    }
    
    public static void main2(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        SubsetComponent driver = new SubsetComponent(n);
        while(m-->0){
            int i = in.nextInt();
            int j = in.nextInt();
            driver.graph.addEdge(i, j);
        }
        in.close();
//        List<List<com.hackerrank.SubsetComponent.Graph.Node<Integer>>> groups = driver.graph.findConnectedComponents();
//        if(groups.size() <2){
//            System.out.println("0");
//            return;
//        }
//        long pairs = groups.get(0).size() * groups.get(1).size();
//        long sum = groups.get(0).size() + groups.get(1).size();
//        for(int i=2; i<groups.size(); ++i){
//            pairs += sum * groups.get(i).size();
//            sum += groups.get(i).size();
//        }
//        System.out.println(pairs);
    }

}
