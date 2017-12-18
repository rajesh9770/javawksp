package com.hackerrank.graphs;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rajesh on 12/11/2017.
 */
public class SpecialKruskal {

    public static class Component{
        int parent;
        int rank;
        public Component(int parent, int rank){
            this.parent = parent;
            this.rank = rank;
        }
    }

    public static class Edge implements Comparable{
        int start, end;
        int cost;
        public Edge(int start, int end, int cost){
            this.start = start;
            this.end = end;
            this.cost = cost;
        }

        @Override
        public int compareTo(Object o) {
            if(o instanceof Edge) return (cost- ((Edge) o).cost);
            else throw new ClassCastException();
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int nodes_num = in.nextInt();
        int edges_num = in.nextInt();
        Edge [] edges = new Edge[edges_num];
        for(int i=0; i<edges_num; i++){
            edges[i] = new Edge(in.nextInt(), in.nextInt(), in.nextInt());
        }
        Component comps[] = new Component[nodes_num+1]; //comp[i] points to component of node i
        //initially each node is it's own component.
        for(int i=1; i<=nodes_num; ++i){
            comps[i] = new Component(i, 1);
        }
        Arrays.sort(edges);
        int edge = 0;
        long totalCost = 0;
        while(edge<edges.length){
            Edge theNextEdge = findTheNextEdge(edge, edges);
            edge++;
            Component first = findComponent(theNextEdge.start, comps);
            Component second = findComponent(theNextEdge.end, comps);
            if(first.parent == second.parent) continue; //start and end belongs to the same component
            totalCost += theNextEdge.cost;
            Component highRank, lowerRank;
            if(first.rank <second.rank){
                highRank = second;
                lowerRank = first;
            }else{
                highRank = first;
                lowerRank = second;
            }
            lowerRank.parent = highRank.parent; //merge the lowerRank with higherRank
            highRank.rank += lowerRank.rank;
        }
        System.out.println(totalCost);
    }

    private static Component findComponent(int start, Component[] comps) {
        if(comps[start].parent == start) return comps[start];
        return findComponent(comps[start].parent, comps);
    }

    //TODO do this in comparable
    private static Edge findTheNextEdge(int edge, Edge[] edges) {
        int i = edge;
        int cost = edges[edge].cost;
        int selectedEdge = edge;
        long currentModCost = edges[edge].start + edges[edge].cost + edges[edge].end;
        for(++i; i<edges.length && edges[i].cost == cost; ++i){
            if(currentModCost > edges[i].start + edges[i].cost + edges[i].end){
                selectedEdge = i;
                currentModCost = edges[i].start + edges[i].cost + edges[i].end;
            }
        }
        //swap the current head to the selected edge
        if(edge != selectedEdge){
            Edge temp = edges[edge];
            edges[edge] = edges[selectedEdge];
            edges[selectedEdge] = temp;
        }
        return edges[edge];
    }
}
