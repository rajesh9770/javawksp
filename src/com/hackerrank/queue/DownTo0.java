package com.hackerrank.queue;

import java.util.*;

public class DownTo0 {

    public static class Node{
        int val;
        int depth;
        public Node(int v, int d){
            val = v;
            depth = d;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();

        while(q-->0){
            int n = in.nextInt();
            if(n==0){
                System.out.println("0");
                continue;
            }

            Queue<Node> bfs = new LinkedList<>();
            bfs.add(new Node(n, 0));
            Set<Integer> visited = new HashSet<>();
            visited.add(n);
            while(!bfs.isEmpty()){
                Node node = bfs.remove();
                if(node.val -1 == 0) {
                    System.out.println(node.depth + 1);
                    break;
                }
                if(!visited.contains(node.val-1)) {
                    bfs.add(new Node(node.val - 1, node.depth + 1));
                    visited.add(node.val-1);
                }
                //of the two proper divisors, one of the divisor is atmost sqrt(n).
                for(int i= ((int) Math.sqrt(node.val)); i>=2; --i){
                    if(node.val % i == 0 ) {
                        if(!visited.contains(node.val /i)) {//if node is already visited, then it's been at a lower depth.
                            bfs.add(new Node(node.val / i, node.depth + 1));
                            visited.add(node.val/i);
                        }
                    }
                }
            }
        }
    }
}
