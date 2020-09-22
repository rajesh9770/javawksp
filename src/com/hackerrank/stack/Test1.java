package com.hackerrank.stack;


import java.util.PriorityQueue;

/**
 * Created by Rajesh on 11/18/2017.
 */
public class Test1 {

    public static class Node {

        int val;
        Node left, right;
        int geValCount;
    }

    public static Node delete(Node head, int val) {
        if (head != null){
            if(head.val == val){
                --head.geValCount;
                if (head.geValCount == 0) return null;
            }else if (head.val < val) {
                head.geValCount--;
                head.right = delete(head.right, val);
            }else {
                head.left = delete(head.left, val);
            }
            return head;
        }else return null;
    }

    public static void main(String[] args) {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        q.add(1);
        q.add(1);
        System.out.println(q.size());

    }
}
