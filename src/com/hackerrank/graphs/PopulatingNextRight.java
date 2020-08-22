package com.hackerrank.graphs;

/**
 * You are given a binary tree where all leaves are on the same level, and every parent has two children. The binary tree has the following definition:
 *
 * struct Node {
 *   int val;
 *   Node *left;
 *   Node *right;
 *   Node *next;
 * }
 * Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
 *
 * Initially, all next pointers are set to NULL.
 */
public class PopulatingNextRight {

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    //BFS - consider queue in BFS as a list
    public Node connect(Node root) {
        Node prevHeadQ = root; //previous level list's head
        while(prevHeadQ != null){
            //first find the head of next level
            Node currLevelHead = null;
            Node currLevelNode = null;
            while(prevHeadQ != null){
                if(prevHeadQ.left != null){
                    if(currLevelHead == null){
                        currLevelHead = prevHeadQ.left;
                        currLevelNode = prevHeadQ.left;
                    }else{
                        currLevelNode.next = prevHeadQ.left;
                        currLevelNode = currLevelNode.next;
                    }
                }
                if(prevHeadQ.right != null){
                    if(currLevelHead == null){
                        currLevelHead = prevHeadQ.right;
                        currLevelNode = prevHeadQ.right;
                    }else{
                        currLevelNode.next = prevHeadQ.right;
                        currLevelNode = currLevelNode.next;
                    }
                }
                prevHeadQ = prevHeadQ.next;
            }
            prevHeadQ = currLevelHead;
        }
        return root;
    }
}
