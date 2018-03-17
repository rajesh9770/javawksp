package com.hackerrank.graphs.Trees;

/**
 * Created by Rajesh on 3/17/2018.
 */

/**
 * Write a function to detect if two trees are isomorphic.
 * Two trees are called isomorphic if one of them can be obtained from other by a series of flips, i.e. by swapping left and right children of a number of nodes.
 * Any number of nodes at any level can have their children swapped. Two empty trees are isomorphic.
 */
public class TreeIsomorphism {

    private static class Node{
        int val;
        Node left, right;
        public Node(int val){
            this.val = val;
            left = right = null;
        }
    }

    private static boolean isIsomorphic(Node root1, Node root2){
        if(root1 == null && root2 == null) return true;
        if(root1 == null && root2 != null) return false;
        if(root2 == null && root1 != null) return false;
        if(root1.val == root1.val){
            return isIsomorphic(root1.left, root2.left) &&
                    isIsomorphic(root1.right, root2.right) ? true :
                    isIsomorphic(root1.left, root2.right) &&
                    isIsomorphic(root1.right, root2.left);
        }
        return  false;
    }

    public static void main(String[] args) {

    }
}
