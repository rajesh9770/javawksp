package com.hackerrank.graphs;

import java.util.*;

/**
 * Created by Rajesh on 12/10/2017.
 */
public class SnakeLadder {


    public static int BFS(Map<Integer, Integer> ladders, Map<Integer, Integer> snakes){
        LinkedList<Integer> currentLevel = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        currentLevel.add(1);
        visited.add(1);
        int level = 0;
        while(!currentLevel.isEmpty()){
            LinkedList<Integer> nextLevel = new LinkedList<>();
            ++level;
            while(!currentLevel.isEmpty()) {
                int[] neighbors = getNeighbors(currentLevel.removeFirst(), ladders, snakes);
                for(int neighbor: neighbors){
                    if(!visited.contains(neighbor)){
                        nextLevel.add(neighbor);
                        visited.add(neighbor);
                        if(neighbor==100) return level;
                    }
                }
            }
            currentLevel = nextLevel;
        }
        return -1;
    }

    public static int [] getNeighbors(int k, Map<Integer, Integer> ladders, Map<Integer, Integer> snakes){
        int [] ret = new int [Math.min(6, 100-k)];
        for(int i=1; i<=Math.min(6, 100-k); ++i){
            if(ladders.containsKey(k+i)) ret[i-1] = ladders.get(k+i);
            else if(snakes.containsKey(k+i)) ret[i-1] = snakes.get(k+i);
            else ret[i-1] = k+i;
        }
        return ret;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int q = sc.nextInt();
        while(q-->0){
            Map<Integer, Integer> ladders = new HashMap<>();
            int n  = sc.nextInt();
            while(n-->0){
                ladders.put(sc.nextInt(), sc.nextInt());
            }
            Map<Integer, Integer> snakes = new HashMap<>();
            int m  = sc.nextInt();
            while(m-->0){
                snakes.put(sc.nextInt(), sc.nextInt());
            }
            System.out.println(BFS(ladders,snakes));
        }

    }
}
