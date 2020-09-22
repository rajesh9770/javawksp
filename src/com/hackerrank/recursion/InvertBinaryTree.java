package com.hackerrank.recursion;

import java.util.LinkedList;
import java.util.Queue;

/**
 * nput:
 *
 *      4
 *    /   \
 *   2     7
 *  / \   / \
 * 1   3 6   9
 * Output:
 *
 *      4
 *    /   \
 *   7     2
 *  / \   / \
 * 9   6 3   1
 */
public class InvertBinaryTree {


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return " " + val ;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4,
                new TreeNode(2, new TreeNode(1), new TreeNode(3)),
                new TreeNode(7, new TreeNode(6), new TreeNode(9)));

        print(root);
        System.out.println("");
        TreeNode invert = new InvertBinaryTree().invert(root);
        print(invert);
    }

    public static void print(TreeNode node) {
        Queue<TreeNode> q = new LinkedList<>();
        q.add(node);
        while (!q.isEmpty()) {
            TreeNode next = q.remove();
            System.out.print(next);
            if (next.left != null) q.add(next.left);
            if (next.right != null) q.add(next.right);
        }
    }
    public TreeNode invert(TreeNode root) {
        if (root == null) return null;

        TreeNode left = root.left;
        TreeNode right = root.right;

        root.left = invert(right);
        root.right = invert(left);

        return root;
    }

}

