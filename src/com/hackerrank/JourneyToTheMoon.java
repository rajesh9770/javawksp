package com.hackerrank;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;




public class JourneyToTheMoon {

    public static class Node {
        public Node(int i) {
            this.val = i;
            this.parent = this;
            this.rank = 0;
        }
        int val;
        Node parent;
        int rank;
        @Override
        public int hashCode() {
            return val;
        }
    }

    Node[] astronomers;
    public JourneyToTheMoon(int n) {
        this.astronomers = new Node[n];
        for(int i=0; i<n; ++i)
            astronomers[i] = new Node(i);
    }

    private Node find(Node node){
        if(node.parent == node) return node;
        node.parent = find(node.parent);
        return node.parent;
    }

    public Node find(int i){
        return find(astronomers[i]);
    }
    
    public void union(Node node1, Node node2){
        Node smaller = node1.rank < node2.rank ? node1 : node2;
        Node bigger = node1.rank < node2.rank ? node2 : node1;
        smaller.parent = bigger;
        bigger.rank += (smaller.rank + 1);
    }
    
    public Map<Integer, List<Integer>> makeUnionFind(){
        Set<Integer> nodes = new HashSet<Integer>();
        Map<Integer, List<Integer>> ret = new HashMap<Integer, List<Integer>>(); 

        for(Node node: astronomers){
            if(nodes.contains(node.val)) continue;
            List<Integer> parents = new ArrayList<Integer>();
            while(node.parent != node){
                System.out.println("Adding " + node.val);
                if(nodes.add(node.val))  parents.add(node.val);
                node = node.parent;
            }
            Node leader = node;
            nodes.add(leader.val);
            System.out.println("Adding leader " + node.val);
            List<Integer> list = ret.get(leader.val);
            if(list == null){
                list = new ArrayList<Integer>();
                System.out.println("creating a list for " + leader.val + " " + parents);
                ret.put(leader.val, list);
            }else{
                System.out.println("adding in list for " + leader.val + " " + parents);
            }
            list.addAll(parents);
        }
        return ret;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        JourneyToTheMoon driver = new JourneyToTheMoon(n);
        //long timer = System.currentTimeMillis();
        Graph<Integer, Integer> astronts = new Graph<>();
        for(int i=0;i<n; ++i) astronts.addNode(new Graph.Node<Integer, Integer>(i, i));
        while(m-->0){
            int i = in.nextInt();
            int j = in.nextInt();
            astronts.addEdge(astronts.get(i), astronts.get(j));
            astronts.addEdge(astronts.get(j), astronts.get(i));
        }
        Map<Integer, Boolean> explored = new HashMap<>();
        for(int i=0;i<n; ++i) explored.put(i, false);
        ArrayList<Long> compSizes = new ArrayList<>();
        for(int i=0;i<n; ++i) {
        	if(!explored.get(i)) {
				compSizes.add(astronts.DFS_CompSize(astronts.get(i), explored));
			}
        }
        if(compSizes.size() == 1) System.out.println("0");
        else{
	        long pairs = compSizes.get(0) * compSizes.get(1);;
	        long sum = compSizes.get(0) + compSizes.get(1);
	        for(int i=2; i<compSizes.size(); ++i){
	               pairs += sum * compSizes.get(i);
	               sum += compSizes.get(i);
	        }
	        System.out.println(pairs);
        }
        in.close();
    }
    
    public static void mainUsingUnionFind(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        JourneyToTheMoon driver = new JourneyToTheMoon(n);
        long timer = System.currentTimeMillis();
        while(m-->0){
            int i = in.nextInt();
            int j = in.nextInt();
            Node iRep = driver.find(i);
            Node jRep = driver.find(j);
            driver.union(iRep, jRep);
        }
        System.out.println(System.currentTimeMillis()-timer);
        Map<Integer, List<Integer>> makeUnionFind = driver.makeUnionFind();
        //System.out.println(System.currentTimeMillis()-timer);
        List<Integer> groupSize = new ArrayList<Integer>();
        for(Map.Entry<Integer, List<Integer>> keyVals : makeUnionFind.entrySet()){
            System.out.print(" " +keyVals.getKey() + " ");
            for(Integer vals : keyVals.getValue()){
                System.out.print(vals + " ");
            }
            System.out.println(" ");
            groupSize.add(keyVals.getValue().size() + 1);
        }
        //System.out.println(System.currentTimeMillis()-timer);
        if(groupSize.size() <2){
            System.out.println("0");
            return;
        }
        long pairs = groupSize.get(0) * groupSize.get(1);;
        long sum = groupSize.get(0) + groupSize.get(1);
        for(int i=2; i<groupSize.size(); ++i){
               pairs += sum * groupSize.get(i);
               sum += groupSize.get(i);
//            for(int j=i+1; j<groupSize.size(); ++j){
//                pairs += groupSize.get(i) * groupSize.get(j);
//            }
        }
        //System.out.println(System.currentTimeMillis()-timer);
        System.out.println(pairs);
    }

    /**
     * 100000 2 1 2 3 4 Ans: 4999949998

     */
}


