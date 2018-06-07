package com.hackerrank.graphs.Trees;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Rajesh on 6/6/2018.
 * A binary tree and a number k are given. Print every path in the tree with sum of the nodes in the path as k.
 A path can start from any node and end at any node and must be downward only, i.e. they need not be root node and leaf node; and negative numbers can also be there in the tree.
 */
public class PartialPathSumK {


    public static class Node{
        int id;
        Node left, right;

        public Node(int id){
            this.id = id;
            this.left = this.right = null;
        }
    }

    public static void findPath(Node node, int k, List<Node> path){
        if(node==null) return;
        path.add(node);  //add a new node
        printPath(path, k); //if you want to just count path, replace print path by suitable function
        findPath(node.left, k, path);
        findPath(node.right, k, path);
        path.remove(path.size()-1); //remove a new node, so that when we go to node's sibling, node is not in path.
    }

    /**
     * prints paths that ends in the given node and whose sum adds up to k.
     * @param path
     * @param k
     */
    private static void printPath(List<Node> path, int k) {
        long pathSum = 0;
        for(int i=path.size()-1; i>=0; --i){
            pathSum += path.get(i).id;
            if(pathSum == k){
                print(path, i, path.size()-1);
            }
        }
    }

    private static void print(List<Node> path, int start, int end) {

        for(int i=start; i<=end; ++i)
            System.out.print(path.get(i).id + " ");
        System.out.print("\n");
    }


    public static void main(String[] args) {
        /**
                                                    10
                                               5         -3
                                            3      2         11
                                          3   -2      1
        **/
        Node root = new Node(10);
        root.right = new Node(-3);
        root.right.right = new Node(11);

        Node l1 = root.left = new Node(5);
        l1.right = new Node(2);
        l1.right.right = new Node(1);

        l1.left = new Node(3);
        l1.left.right = new Node(-2);
        l1.left.left = new Node(3);

        int k = 7;
        List<Node> path = new ArrayList<>();
        findPath(root, 7, path); //O(n^2) in worst, O(nlogn) if tree is balanced.

    }
}
