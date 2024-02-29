package com.hackerrank.graphs;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class Cycle {

    static class Graph{
        List<String> nodes;
        Map<String, List<String>> adjList;//directed graph node -> neighbors
    }


//    public static void main(String[] args) {
//        Graph graph = new Graph();
//        findCycle(graph);
//
//    }


    public static boolean findCycle(Graph g){

        Map<String, Character> nodeState = new HashMap<>();
        for(String node: g.nodes){
            if(!nodeState.containsKey(node)  || nodeState.get(node) != 'V'){
                //V is for visited, S for started
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

    public boolean containsCycle(Graph g){
        Map<String, Character> visitedStatus = new HashMap<>();
        for(String nodeId: g.nodes){
            if(!visitedStatus.containsKey(nodeId)){
                DFS(g, nodeId, visitedStatus);
            }
        }
        return false;
    }

    private boolean DFS(Graph g, String nodeId, Map<String, Character> visitedStatus) {
        List<String> children = g.adjList.get(nodeId);
        visitedStatus.put(nodeId, 'S'); //started visiting this node

        for (String child : children) {
            if (visitedStatus.containsKey(child) && visitedStatus.get(child) == 'S'){
                return true;
            }
            if(DFS(g, child, visitedStatus)) return true;
        }
        visitedStatus.put(nodeId, 'V'); //done processing nodeId
        return false;
    }


    /*
    There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1.
    You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.
    For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
    Return true if you can finish all courses. Otherwise, return false.
    */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        //build adj list
        Map<Integer, List<Integer>> adjList = buildAdjList(numCourses, prerequisites);
        Map<Integer, Character> visitedStatus = new HashMap<>();
        for(int i=0; i< numCourses; ++i){
            visitedStatus.put(i, 'W'); //white in the beginning
        }
        for (Integer course : adjList.keySet()) {
            if(visitedStatus.get(course) == 'W'){
                //System.out.println("Calling on " + course);
                boolean dfs = DFS(adjList, course, visitedStatus);
                if(!dfs) return false;
            }
        }

        return true;
    }

    private Map<Integer, List<Integer>> buildAdjList(int n, int[][] prerequisites) {
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        for(int i=0; i<n; ++i){
            adjList.put(i, new ArrayList<>());
        }
        for(int i=0; i< prerequisites.length; ++i){
            // (a, b),  b -> a  , b should come before a
            int a = prerequisites[i][0];
            int b = prerequisites[i][1];
            //System.out.println("adding child " + a + " to " + b);
            adjList.get(b).add(a);
        }

        return adjList;
    }

    public boolean DFS(Map<Integer, List<Integer>> adjList, int nodeId, Map<Integer, Character> visitedStatus) {
        visitedStatus.put(nodeId, 'G');//Grey when DFS starts
        //System.out.println("Calling on " + nodeId);
        for (Integer follower : adjList.get(nodeId)) {
            if (visitedStatus.containsKey(follower) && visitedStatus.get(follower) == 'G') { //cycle when currently traversing node found
                return false; //there is cycle
            }
            if(visitedStatus.get(follower) == 'W') {
                boolean dfs = DFS(adjList, follower, visitedStatus);
                if (!dfs) return false;
            }
        }

        visitedStatus.put(nodeId, 'B'); //black when DFS finished
        System.out.print(nodeId  + " ");
        return true;
    }

    public static void main(String[] args) {
        int noOfCourses = 100;
        int [][] dependencies = new int[][]{{1, 0}, {2, 0}, {2, 1}, {3, 1}, {3, 2}, {4, 2}, {4, 3}, {5, 3}, {5, 4}, {6, 4}, {6, 5}, {7, 5}, {7, 6}, {8, 6}, {8, 7}, {9, 7}, {9, 8}, {10, 8}, {10, 9}, {11, 9}, {11, 10}, {12, 10}, {12, 11}, {13, 11}, {13, 12}, {14, 12}, {14, 13}, {15, 13}, {15, 14}, {16, 14}, {16, 15}, {17, 15}, {17, 16}, {18, 16}, {18, 17}, {19, 17}, {19, 18}, {20, 18}, {20, 19}, {21, 19}, {21, 20}, {22, 20}, {22, 21}, {23, 21}, {23, 22}, {24, 22}, {24, 23}, {25, 23}, {25, 24}, {26, 24}, {26, 25}, {27, 25}, {27, 26}, {28, 26}, {28, 27}, {29, 27}, {29, 28}, {30, 28}, {30, 29}, {31, 29}, {31, 30}, {32, 30}, {32, 31}, {33, 31}, {33, 32}, {34, 32}, {34, 33}, {35, 33}, {35, 34}, {36, 34}, {36, 35}, {37, 35}, {37, 36}, {38, 36}, {38, 37}, {39, 37}, {39, 38}, {40, 38}, {40, 39}, {41, 39}, {41, 40}, {42, 40}, {42, 41}, {43, 41}, {43, 42}, {44, 42}, {44, 43}, {45, 43}, {45, 44}, {46, 44}, {46, 45}, {47, 45}, {47, 46}, {48, 46}, {48, 47}, {49, 47}, {49, 48}, {50, 48}, {50, 49}, {51, 49}, {51, 50}, {52, 50}, {52, 51}, {53, 51}, {53, 52}, {54, 52}, {54, 53}, {55, 53}, {55, 54}, {56, 54}, {56, 55}, {57, 55}, {57, 56}, {58, 56}, {58, 57}, {59, 57}, {59, 58}, {60, 58}, {60, 59}, {61, 59}, {61, 60}, {62, 60}, {62, 61}, {63, 61}, {63, 62}, {64, 62}, {64, 63}, {65, 63}, {65, 64}, {66, 64}, {66, 65}, {67, 65}, {67, 66}, {68, 66}, {68, 67}, {69, 67}, {69, 68}, {70, 68}, {70, 69}, {71, 69}, {71, 70}, {72, 70}, {72, 71}, {73, 71}, {73, 72}, {74, 72}, {74, 73}, {75, 73}, {75, 74}, {76, 74}, {76, 75}, {77, 75}, {77, 76}, {78, 76}, {78, 77}, {79, 77}, {79, 78}, {80, 78}, {80, 79}, {81, 79}, {81, 80}, {82, 80}, {82, 81}, {83, 81}, {83, 82}, {84, 82}, {84, 83}, {85, 83}, {85, 84}, {86, 84}, {86, 85}, {87, 85}, {87, 86}, {88, 86}, {88, 87}, {89, 87}, {89, 88}, {90, 88}, {90, 89}, {91, 89}, {91, 90}, {92, 90}, {92, 91}, {93, 91}, {93, 92}, {94, 92}, {94, 93}, {95, 93}, {95, 94}, {96, 94}, {96, 95}, {97, 95}, {97, 96}, {98, 96}, {98, 97}, {99, 97}};
        boolean b = new Cycle().canFinishUsingKahnAlgo(100, dependencies) ;
        System.out.println("\nFinal: " + b + "Ans " + true );

        boolean b1 = new Cycle().canFinish(100, dependencies);
        System.out.println("--");
        System.out.println("Using DFS: " + b1);

    }


    public boolean canFinishUsingKahnAlgo(int numCourses, int[][] prerequisites){
        Pair<int[], Map<Integer, List<Integer>>> preprocess = buildIncomingCounts(numCourses, prerequisites);
        int[] incomingEdgeCounts = preprocess.getKey();
        Map<Integer, List<Integer>> adjList = preprocess.getValue();
//        System.out.println(Arrays.toString(incomingEdgeCounts));

        Queue<Integer> parentCourse = new LinkedList<>();
        for(int i=0; i< numCourses; ++i){
            if(incomingEdgeCounts[i] ==0) parentCourse.add(i);
        }
        List<Integer> topoSort = new LinkedList<>();
        while(!parentCourse.isEmpty()){
            Integer parent = parentCourse.remove();
            topoSort.add(parent);
            List<Integer> children = adjList.get(parent);
            for (Integer child : children) {
                if(--incomingEdgeCounts[child] == 0) parentCourse.add(child);
            }
        }
        if(topoSort.size() != numCourses) return false;
        System.out.println(topoSort);
        return true;
    }



    private Pair<int[], Map<Integer, List<Integer>>> buildIncomingCounts(int n, int[][] prerequisites) {
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        for(int i=0; i<n; ++i){
            adjList.put(i, new ArrayList<>());
        }

        int [] counts = new int[n];
        for(int i=0; i< n; ++i){
            counts[i] =0;
        }
        for(int i=0; i< prerequisites.length; ++i){
            // (a, b),  b -> a  , b should come before a
            int a = prerequisites[i][0];
            int b = prerequisites[i][1];
            adjList.get(b).add(a);
            //System.out.println("adding child " + a + " to " + b);
            counts[a]++;
        }

        return Pair.of(counts, adjList);
    }

    private static class IncomingEdgeCount implements Comparable{
        int id;
        int count;

        IncomingEdgeCount(int id, int count){
            this.id = id;
            this.count = count;
        }

        @Override
        public int compareTo(Object o) {
            if(o  instanceof IncomingEdgeCount){
                return count - ((IncomingEdgeCount) o).count ;
            }
            return 0;
        }

        @Override
        public String toString() {
            return "IncomingEdgeCount{" +
                    "id=" + id +
                    ", count=" + count +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IncomingEdgeCount that = (IncomingEdgeCount) o;
            return id == that.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
