package com.hackerrank.dollar;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * You are an ant tasked with adding n new rooms numbered 0 to n-1 to your colony.
 * You are given the expansion plan as a 0-indexed integer array of length n, prevRoom,
 * where prevRoom[i] indicates that you must build room prevRoom[i] before building room i,
 * and these two rooms must be connected directly. Room 0 is already built, so prevRoom[0] = -1.
 * The expansion plan is given such that once all the rooms are built, every room will be reachable from room 0.
 *
 * You can only build one room at a time, and you can travel freely between rooms you have already built only if they are connected.
 * You can choose to build any room as long as its previous room is already built.
 *
 * Return the number of different orders you can build all the rooms in. Since the answer may be large, return it modulo 109 + 7.
 *
 * Formula is n!/(size_of_subtree_1 * size_of_subtree_2 * size_of_subtree_3 * ...)
 * Ref: https://codeforces.com/blog/entry/75627
 *
 */
public class AntColony {
    public static int MOD = 1_000_000_007;

    public int waysToBuildRooms(int[] prevRoom) {
        int noOfRooms = prevRoom.length;
        ArrayList<Integer> adjList[] = new ArrayList[noOfRooms];

        for(int i=0; i<noOfRooms; ++i){
            adjList[i] = new ArrayList<>();
        }
        for(int i=1; i<noOfRooms; ++i){
            adjList[prevRoom[i]].add(i); //edge from prevRoom[i] -> i
        }
        int[] sizes = new int[noOfRooms];
        Arrays.fill(sizes, 0);//size should be atleast 1
        DFS(adjList, sizes, 0);

        //calculate n!
        int nFactorial =1;
        for(int i=2; i<= noOfRooms; ++i){
            nFactorial = (nFactorial * i) % MOD;
        }
        // calculate denominator (size_of_subtree_1 * size_of_subtree_2 * size_of_subtree_3 * ...)
        long denominator = 1;
        for (int size : sizes) {
            denominator = (denominator * size) % MOD;
        }
        //calculate inverse of denominator modulo MOD
        long inverse = modpow(denominator, MOD-2, MOD);
        System.out.println((inverse * denominator)%MOD);

        return (int) ((inverse * nFactorial) % MOD);
    }

    public static int DFS(ArrayList<Integer>[] edges, int [] sizes, int root){
        //if(sizes[root] != 0) return sizes[root];
        int size = 1;
        for (Integer neighbor : edges[root]) {
            size += DFS(edges, sizes, neighbor);
        }
        sizes[root] = size;
        return size;
    }

    public static int modpow(long base, int exponent, int modulo){
        base = base % modulo;
        long ans = 1;
        while(exponent>0){
            if(exponent %2 ==1){
                ans = (ans * base) % modulo;
            }
            exponent = exponent/2;
            if (exponent>0) {
                base = (base * base) % modulo;
            }
        }
        return (int) ans;
    }

    public static void main(String[] args) {
        int c;
//        c= new AntColony().waysToBuildRooms(new int[]{-1, 0, 1});
//        System.out.println(c);//1
        c = new AntColony().waysToBuildRooms(new int[]{-1,0,0,1,2});
        System.out.println(c);//1

    }
}
