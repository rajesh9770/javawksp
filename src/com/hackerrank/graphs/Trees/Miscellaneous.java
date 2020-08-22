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
    static Integer res = Integer.MAX_VALUE, pre = null;

    public static int minDiffInBST(TreeNode root) {
        //Integer res = Integer.MAX_VALUE, pre = null;

            if (root.left != null) minDiffInBST(root.left);
            if (pre != null) res = Math.min(res, root.val - pre);
            pre = root.val;
            if (root.right != null) minDiffInBST(root.right);
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
        driverMinDiffBST();
    }
}
