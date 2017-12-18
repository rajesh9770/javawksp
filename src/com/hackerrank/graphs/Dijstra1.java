package com.hackerrank.graphs;

import java.io.*;
import java.util.*;

/**
 * Created by Rajesh on 12/17/2017.
 */
public class Dijstra1 {

    private static Map<Integer, Integer>[] adjacentLists;

    private static class Edge implements Comparable{
        int node;
        int r;

        public Edge(int node, int cost){
            this.node=node;
            this.r = cost;
        }
        @Override
        public int compareTo(Object o) {
            return this.r - ((Edge)o).r;
        }

        @Override
        public boolean equals(Object obj) {
            return ((Edge)obj).node == this.node;
        }
    }
    public static void main(String[] args) throws FileNotFoundException {

        InputReader in = new InputReader(System.in);

        int t = in.nextInt();
        for(int a0 = 0; a0 < t; a0++){
            long startTime = System.currentTimeMillis();
            int n = in.nextInt();
            adjacentLists = new Map[n];
            for(int node=0; node<n; ++node){
                adjacentLists[node] = new HashMap<>();
            }

            int m = in.nextInt();
            for(int a1 = 0; a1 < m; a1++){
                int x = in.nextInt()-1;
                int y = in.nextInt()-1;
                int r = in.nextInt();
                if(adjacentLists[x].containsKey(y)) r = Math.min(adjacentLists[x].get(y), r);
                adjacentLists[x].put(y, r);
                adjacentLists[y].put(x, r);
            }
            System.out.println("Input: "  + (System.currentTimeMillis()-startTime));

//            for(int i=0; i<n; ++i){
//                System.out.println(i + ": ");
//                for(Map.Entry<Integer, Integer> neighbors : adjacentLists[i].entrySet()){
//                    System.out.println("\t" + neighbors.getKey() + " " + neighbors.getValue());
//                }
//            }

            int s = in.nextInt()-1;
            int [] costs = new int[n];
            boolean [] visited = new boolean[n];
            costs[s] = 0;
            visited[s] = true; //set it true after we explored all the neighbors
            PriorityQueue<Edge> q = new PriorityQueue<>();
            for(Map.Entry<Integer, Integer> neighbors : adjacentLists[s].entrySet()) {
                if(!visited[neighbors.getKey()])
                    //System.out.println("Adding value of node " + neighbors.getKey() + " " + neighbors.getValue());
                    q.add(new Edge(neighbors.getKey(), neighbors.getValue()));
            }
            while(!q.isEmpty()){
                Edge next = q.poll();
                costs[next.node] = next.r;
                //System.out.println("examining" + next.node);
                if(!visited[next.node]) {
                    for (Map.Entry<Integer, Integer> neighbors : adjacentLists[next.node].entrySet()) {
                        int newNeighbor = neighbors.getKey();
                        if (!visited[newNeighbor]) {
                            Iterator<Edge> it = q.iterator();
                            Edge e = null;
                            while (it.hasNext()) {
                                Edge next1 = it.next();
                                if (next1.node == newNeighbor) {
                                    e = next1;
                                    it.remove();
                                    break;
                                }
                            }
                            if (e != null) {
                                e.r = Math.min(e.r, costs[next.node] + neighbors.getValue());
                                //System.out.println("Updating value of node " + newNeighbor + " " + e.r);
                            } else {
                                //System.out.println("Adding value of node " + newNeighbor + " " + (costs[next.node] + neighbors.getValue()));
                                e = new Edge(newNeighbor, costs[next.node] + neighbors.getValue());
                            }
                            q.add(e);
                        }
                    }
                    visited[next.node] = true; //set it true after we explored all the neighbors
                }
            }
            for(int i=0; i<n; ++i){
                if(i==s) continue;
                if(!visited[i]) System.out.print("-1 ");
                else System.out.print(costs[i] + " " );
            }
            System.out.println("");
            System.out.println("Output: "  + (System.currentTimeMillis()-startTime));
        }
    }


    private static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024*10];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public int nextInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }


        public boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }
        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
        }

    }
}
