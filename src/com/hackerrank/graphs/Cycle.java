package com.hackerrank.graphs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Cycle {

    static class Graph{
        List<String> nodes;
        Map<String, List<String>> adjList;//directed graph node -> neighbors
    }


    public static void main(String[] args) {
        Graph graph = new Graph();
        findCycle(graph);

    }


    public static boolean findCycle(Graph g){

        Map<String, Character> nodeState = new HashMap<>();
        for(String node: g.nodes){
            if(!nodeState.containsKey(node)  || nodeState.get(node) != 'V'){
                Stack<String> st = new Stack<>();
                st.add(node);
                nodeState.put(node, 'S');
                if(!processNode(g, node, st, nodeState)) return false;
            }
        }
        return true;
    }
    private static boolean processNode(Graph g, String node, Stack<String> st, Map<String, Character> nodeState) {
        List<String> neighbors = g.adjList.get(node);
        for(String neighbor: neighbors){

            if(nodeState.containsKey(neighbor) && nodeState.get(neighbor) =='S'){
                return false;
            }else{
                st.push(neighbor);
                nodeState.put(neighbor, 'S');
                processNode(g, neighbor, st, nodeState);
            }
        }
        st.pop();
        nodeState.put(node, 'V');
        return true;
    }


}
