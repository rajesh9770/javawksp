package com.hackerrank.graphs.Trees;

public class Miscellaneous {

    /**
     * Given a Binary Search Tree (BST) with the root node root, return the minimum difference between the values of any two different nodes in the tree.
     * Hint: In sorted array, the minimum difference is found between two consecutive elements. Inorder traversal of BST prints tree in sorted order.
     */
    public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }
    static Integer res = Integer.MAX_VALUE;

    public static int minDiffInBST(TreeNode root) {
        //Integer res = Integer.MAX_VALUE, pre = null;
        if (root == null) return res;
        if (root.left != null) {
            res = Math.min(res, root.val - root.left.val);
            res = minDiffInBST(root.left);
        }

        if (root.right != null) {
            res = Math.min(res, root.right.val - root.val);
            res = minDiffInBST(root.right);
        }
        return res;

    }

    public static void driverMinDiffBST(){
        TreeNode bstRoot = new TreeNode(4);
        bstRoot.right = new TreeNode(6);
        bstRoot.left = new TreeNode(2);
        bstRoot.left.right = new TreeNode(3);
        bstRoot.left.left = new TreeNode(1);

        System.out.println(minDiffInBST(bstRoot) == 1);
    }




    public static void main(String[] args) {
//        driverMinDiffBST();

    }


    /**
     * Given -
     * 2 sorted integer arrays A (size n) and B (size m)
     * 2 integers x and k
     *
     * Write a function k_closest(A, B, x, k) that returns k integers closest to x across the 2 sorted arrays.
     *
     * Example:
     * A = [1,5,7,9]
     * B = [2,8,16]
     * x = 5
     * k = 4
     *
     * output = [2,5,7,8]
     */
}
