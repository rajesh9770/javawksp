package com.hackerrank.graphs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Rajesh on 12/13/2017.
 */

/**
33 1
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
2 5
 Ans:  0 6442450944

34 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
Ans: 0 17179869184

32 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 4294967296
 */

public class MoneyDemand {

    private static long max = 0;
    static Set<Integer> availNodes = new HashSet<>();
    static long availEdges = 0;
    private static Map<Long, LongWrap> counts = new HashMap<>();
    private static long stackDepth = 0;
    static int money [];
    static boolean graph [][];
    static int n;
    static Map<Integer, LongWrap> edgeCounts = new HashMap<>();
    static List<Integer> nodeOrder;

    public static class LongWrap{
        public long count;
        public LongWrap(long count){
            this.count = count;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        int m = in.nextInt();
        money = new int [n];
        long totalAmount = 0;
        for (int nodes = 0; nodes<n; ++nodes){
            money[nodes] = in.nextInt();
            totalAmount += money[nodes];
            availNodes.add(nodes);
            edgeCounts.put(nodes, new LongWrap(0));
        }
        graph = new boolean[n][n];
        for (int edges = 0; edges<m; ++edges){
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            graph[a][b]=true;
            graph[b][a]=true;
            edgeCounts.getOrDefault(a, new LongWrap(0)).count++;
            edgeCounts.getOrDefault(b, new LongWrap(0)).count++;
            availEdges++;
        }
        nodeOrder = new LinkedList<>(availNodes);
        nodeOrder.sort((o1, o2) -> {
            if(edgeCounts.get(o1).count != edgeCounts.get(o2).count){
                return (int)(edgeCounts.get(o2).count - edgeCounts.get(o1).count);
            }else{
                return o1.compareTo(o2);
            }
        });
        boolean newGraph[][] = new boolean[n][n];
        int newMoney [] = new int[n];
        for(int i=0; i<n; ++i){
            int mapTo = nodeOrder.get(i);
            System.out.println( mapTo + "<=" + i);
            //i is mapped to mapTo
            newMoney[mapTo] = money[i];
            for(int j=0; j<n; ++j){
                newGraph[mapTo][nodeOrder.get(j)] = graph[i][j];
                newGraph[nodeOrder.get(j)][mapTo] = graph[j][i];
            }
        }
        money = newMoney;
        graph = newGraph;

//        for(Integer a: nodeOrder){
//            System.out.println(a + " " + edgeCounts.get(a).count);
//        }
//        nodeOrder.remove(2);
//        edgeCounts.get(6).count = 100;
//        for(Integer a: nodeOrder){
//            System.out.println(a + " " + edgeCounts.get(a).count);
//        }
//        if(true) return;
        counts.put(0L, new LongWrap(1));
        System.out.println(System.currentTimeMillis() - startTime);
        recurse2(availNodes, availEdges, 0, totalAmount, 0);
        System.out.println(max + " " + counts.get(max).count + " " + (System.currentTimeMillis() - startTime));
    }

    private static String printTaken(long taken){
        StringBuilder bl = new StringBuilder("[");

        for(int i=0; i<n; ++i){
            if((taken & (1l<<i)) == 0)
                bl.append("0 ");
            else
                bl.append("1 ");
        }
        bl.append("]");
        return bl.toString();
    }

    private static class Edge{
        int first, second;
        public Edge(int f, int s){
            this.first =f;
            this.second = s;
        }
    }


    private static void recurse2(Set<Integer> availNodes, long availEdges, long total, long availTotal, long taken) {

        //System.out.println("availNodes: "  + availNodes.toString() + " " + availNodes.size() + " " + availEdges);
        if(availNodes.isEmpty() || availTotal + total < max) return;
        if(availEdges == 0){
            int remainingAmount = 0;
            int zeros = 0;
            for(Integer node: availNodes){
                if(money[node] ==  0 ) zeros++;
                else remainingAmount += money[node];
            }
            LongWrap counter = counts.computeIfAbsent(total + remainingAmount, aLong -> new LongWrap(0));
            counter.count += remainingAmount > 0 ? ((1l<<zeros)) : ((1l<<zeros)-1);
            max = Math.max(max, total + remainingAmount);
            //if(total+remainingAmount == 200){
                System.out.println( "zeros: "  + max + " " + counts.get(max).count  + " " + printTaken(taken));
            //    System.out.println("availNodes: "  + availNodes.toString() + " " + availNodes.size() + " " + availEdges);
            //}
            return;
        }

        Iterator<Integer> nodeit = availNodes.iterator();

        while(nodeit.hasNext()){
            Integer nextNode = nodeit.next();
            //in current loop we take this node.
            long newTotal = total + money[nextNode];
            //taken |= (1l<<nextNode);
            long newAvailEdges = availEdges;
            max = Math.max(max, newTotal);
            counts.computeIfAbsent(newTotal, aLong -> new LongWrap(0)).count++;
            System.out.println("new: " + newTotal + " " + counts.get(newTotal).count + " " + printTaken(taken | (1l<<nextNode)));
            //in subsequent loops the current node will not be includes
            nodeit.remove();
            Set<Integer> newAvailNodes = new HashSet<>(availNodes);
            long newAvailTotal = availTotal;
            //newAvailNodes.remove(nextNode);
            newAvailTotal -= money[nextNode];
            for(Integer i: availNodes){
                if(graph[nextNode][i]) {
                    //System.out.println("removing node " + i);
                    newAvailNodes.remove(i);
                    newAvailTotal -= money[i];
                    newAvailEdges--;
                    //in subsequent loops these edges will not be considered, since nextNode is removed and this edge is adjacent to it.
                    availEdges--;
// Uncommenting this gives wrong answeres
//                    for (int j = nextNode + 1; j < graph.length; ++j) {
//                        if (availNodes.contains(j) && graph[i][j]) {
//                            newAvailEdges--;
//                        }
//                    }
                }
            }
            if(!newAvailNodes.isEmpty() && (newTotal+newAvailTotal) >=max )
                recurse2(newAvailNodes, newAvailEdges, newTotal, newAvailTotal, taken | (1l <<nextNode));
        }
    }


//    private static void recurse(Set<Integer> availNodes, long availEdges, long total, long availTotal, long taken) {
//
//        //System.out.println("availNodes: "  + availNodes.toString() + " " + availNodes.size() + " " + availEdges);
//        if(availNodes.isEmpty() || availTotal + total < max) return;
//        if(availEdges == 0){
//            int remainingAmount = 0;
//            int zeros = 0;
//            for(Integer node: availNodes){
//                if(money[node] ==  0 ) zeros++;
//                else remainingAmount += money[node];
//            }
//            LongWrap counter = counts.computeIfAbsent(total + remainingAmount, aLong -> new LongWrap(0));
//            counter.count += remainingAmount > 0 ? ((1l<<zeros)) : ((1l<<zeros)-1);
//            max = Math.max(max, total + remainingAmount);
//            //if(total+remainingAmount == 200){
//            //    System.out.println( "zeros: "  + max + " " + counts.get(max).count  + " " + printTaken(taken));
//            //    System.out.println("availNodes: "  + availNodes.toString() + " " + availNodes.size() + " " + availEdges);
//            //}
//            return;
//        }
//
//        Iterator<Integer> nodeit = availNodes.iterator();
//
//        while(nodeit.hasNext()){
//            Integer nextNode = nodeit.next();
//            //in current loop we take this node.
//            long newTotal = total + money[nextNode];
//            //taken |= (1l<<nextNode);
//            long newAvailEdges = availEdges;
//            max = Math.max(max, newTotal);
//            counts.computeIfAbsent(newTotal, aLong -> new LongWrap(0)).count++;
//            //System.out.println("new: " + max + " " + counts.get(max).count + " " + printTaken(taken | (1l<<nextNode)));
//            //in subsequent loops the current node will not be includes
//            nodeit.remove();
//            Set<Integer> newAvailNodes = new TreeSet<>(availNodes);
//            long newAvailTotal = availTotal;
//            //newAvailNodes.remove(nextNode);
//            newAvailTotal -= money[nextNode];
//            for(int i=nextNode+1; i<graph.length; ++i){
//                if(availNodes.contains(i) && graph[nextNode][i]) {
//                    //System.out.println("removing node " + i);
//                    newAvailNodes.remove(i);
//                    newAvailTotal -= money[i];
//                    newAvailEdges--;
//                    //in subsequent loops these edges will not be considered, since nextNode is removed and this edge is adjacent to it.
//                    availEdges--;
//// Uncommenting this gives wrong answeres
////                    for (int j = nextNode + 1; j < graph.length; ++j) {
////                        if (availNodes.contains(j) && graph[i][j]) {
////                            newAvailEdges--;
////                        }
////                    }
//                }
//            }
//            if(!newAvailNodes.isEmpty() && (newTotal+newAvailTotal) >=max )
//                recurse(newAvailNodes, newAvailEdges, newTotal, newAvailTotal, taken | (1l <<nextNode));
//        }
//    }

//    private static void recurse(int[] money, int[][] graph, boolean [] availableNodes, long total) {
//        Set<Integer> neighbors = new HashSet<>();
//
//       System.out.print("Calling recurse1 " + stackDepth++ + " == ");
////        for(int i=0; i<availableNodes.length; ++i){
////            if(availableNodes[i]){
////                System.out.print(i + " ");
////            }
////        }
//        System.out.println("");
//        for(int i=0; i<availableNodes.length; ++i){
//            if(availableNodes[i]){
//                total += money[i];
//                max = Math.max(max, total);
////                taken |= (1 << i);
////                Set<Long> paths = counts.get(total);
////                if(paths == null){
////                    paths = new HashSet<>();
////                    counts.put(total, paths);
////                }
////                paths.add(taken);
//                availableNodes[i] = false;
//                long remainingAmount = 0;
//                for(int myneighbors=0; myneighbors<money.length; ++myneighbors){
//                    if(graph[i][myneighbors]==1 && availableNodes[myneighbors]){
//                        neighbors.add(myneighbors);
//                        availableNodes[myneighbors] = false;
//                    }
//                    if(availableNodes[myneighbors]) remainingAmount += money[myneighbors];
//                }
//                if(remainingAmount + total >= max)  recurse(money, graph, availableNodes, total);
//                //revert back to original stack
//                total -=money[i];
// //               taken &= ~(1 << i);
//                availableNodes[i] = true;
//                Iterator<Integer> it = neighbors.iterator();
//                while(it.hasNext()){
//                    availableNodes[it.next()] = true;
//                    it.remove();
//                }
//            }
//        }
//        stackDepth--;
//    }
}

/**
#include <bits/stdc++.h>

        using namespace std;

        int a[55][55], c[55];
        int n, m;
        int N;

        int val[(1 << 17)];
        int dp[(1 << 17)];
        int hm[(1 << 17)];
        void rec(const int t, const int N, const int msk, const int cur) {
        if (t == N) {
        if (dp[msk] < val[cur]) {
        dp[msk] = val[cur];
        hm[msk] = 0;
        }
        if (dp[msk] == val[cur]) {
        hm[msk]++;
        }
        return ;
        }
        rec(t + 1, N, msk, cur);
        if ( (1 << t) & msk ) {
        rec(t + 1, N, msk, cur + (1 << t) );
        }
        }

        int best = 0;
        long long ways = 0;

        void go(const int t, const int finish, const int cur, const int cost) {
        if (t == finish) {
        bool ok = true;
        for (int i = N; i < finish; i++) {
        for (int j = N; j < finish; j++) {
        if ( ( (1 << i) & cur) && ( (1 << j) & cur) && a[i][j] == true) {
        ok = false;
        }
        }
        }
        if (ok == false) {
        return ;
        }
        int allow = 0;
        for (int i = 0; i < N; i++) {
        bool ok = true;
        for (int j = N; j < finish; j++) {
        if ( ( (1 << j) & cur) && a[i][j] == true) {
        ok = false;
        }
        }
        if (ok == true) {
        allow += (1 << i);
        }
        }
        int tot_cost = cost + dp[allow];
        if (best < tot_cost) {
        best = tot_cost;
        ways = 0;
        }
        if (best == tot_cost) {
        ways += hm[allow];
        }

        return ;
        }
        go(t + 1, finish, cur, cost);
        go(t + 1, finish, cur + (1 << t), cost + c[t]);
        }

        void brute() {
        int brans = 0;
        int brways = 0;
        for (int msk = 0; msk < (1 << n); msk++) {
        bool ok = true;
        for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
        if ( ( (1 << i) & msk) && ( (1 << j) & msk ) && a[i][j] == true ) {
        ok = false;
        }
        }
        }
        if (ok == false) {
        continue;
        }
        int cost = 0;
        for (int i = 0; i < n; i++) {
        if ( (1 << i) & msk ) {
        cost += c[i];
        }
        }
        if (brans < cost) {
        brans = cost;
        brways = 0;
        }
        if (brans == cost) {
        brways++;
        }
        }
        cerr << brans << " " << brways << endl;
        assert( brans == best && brways == ways );

        }

        int main() {
        cin >> n >> m;
        for (int i = 0; i < n; i++) {
        cin >> c[i];
        }
        for (int i = 0; i < m; i++) {
        int aa, bb;
        cin >> aa >> bb;
        aa--; bb--;
        a[aa][bb] = a[bb][aa] = true;
        }
        N = n/2;
        for (int i = 0; i < (1 << N); i++) {
        bool ok = true;
        int cost = 0;
        for (int j = 0; j < N; j++) {
        if ( ( 1 << j ) & i ) {
        cost += c[j];
        }
        for (int k = 0; k < N; k++) {
        if ( ( (1 << j) & i ) && ( (1 << k) & i ) && a[j][k] == true) {
        ok = false;
        }
        }
        }
        if (ok == false) {
        cost = -1;
        }
        val[i] = cost;
        }
        for (int i = 0; i < (1 << N); i++) {
        rec(0, N, i, 0);
        }
        go(N, n, 0, 0);
        cout << best << " " << ways << endl;
        cerr << best << " " << ways << endl;
        //brute();
        return 0;
        }
 **/