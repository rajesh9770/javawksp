package com.hackerrank.graphs.Trees;

/**
 * Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.
 * An example is the root-to-leaf path 1->2->3 which represents the number 123.
 *
 * Find the total sum of all root-to-leaf numbers.
 */
public class SumRootOfLeafs {


    /**
     * Definition for a binary tree node.
     */
        public class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
          TreeNode(int x) { val = x; }
        }

    class Solution {
        public int sumNumbers(TreeNode root) {
            int prevLevelNum = 0;
            int [] sum = new int[]{0};
            DFS(root, prevLevelNum, sum);
            return sum[0];
            //return sum(root, 0);
        }

        public void DFS(TreeNode node, int prevLevelNum, int[] sum){
            if(node == null) return;
            prevLevelNum *=10;
            prevLevelNum += node.val;
            if(node.left == null && node.right == null) {
                sum[0] += prevLevelNum;
                return;
            }
            if(node.left != null) DFS(node.left, prevLevelNum, sum);
            if(node.right != null) DFS(node.right, prevLevelNum, sum);
        }

        //Alternative and compact
        public int sum(TreeNode n, int s){
            if (n == null) return 0;
            if (n.right == null && n.left == null) return s*10 + n.val;
            return sum(n.left, s*10 + n.val) + sum(n.right, s*10 + n.val);
        }
    }
}
