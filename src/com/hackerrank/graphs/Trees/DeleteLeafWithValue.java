package com.hackerrank.graphs.Trees;

import java.util.*;

/**
 * Given a binary tree root and an integer target, delete all the leaf nodes with value target.
 *
 * Note that once you delete a leaf node with value target, if it's parent node becomes a leaf node
 * and has the value target, it should also be deleted (you need to continue doing that until you can't).
 */
public class DeleteLeafWithValue {

    public class TreeNode {
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
  }


    public TreeNode removeLeafNodes(TreeNode root, int target) {
        if (root == null) return null;

        if (root.left != null) {
            root.left = removeLeafNodes(root.left, target);
        }
        if (root.right != null) {
            root.right = removeLeafNodes(root.right, target);
        }

        if (root.right == null && root.left == null) {
            if (root.val == target) {
                return null;
            }else return root;
        }
        return root;
    }

    public static long test(long n, long p){
        System.out.println(Math.sqrt(n));
        System.out.println(Math.sqrt(n) * Math.sqrt(n));
        List<Long> factors = new ArrayList<>();
        for(int i=1; i<=Math.sqrt(n); ++i){
            if (n%i == 0){
                factors.add((long)i);
                if (n/i != i) {
                    factors.add(n / i);
                }
            }
        }
        if (factors.size()<p)  return 0;
        Collections.sort(factors);
        return factors.get((int)p-1);
    }

    public static void main(String[] args) {
        System.out.println(test(22876792454961L,28));
    }


    public static int test(List<Integer> arr   ) {
        int sum = 0;
        Set<Integer> seen = new HashSet<>();
         for (Integer a: arr) {
             int val = a;
             while(seen.contains(val)) {
                 ++val;
             }
             seen.add(val);
             sum += val;
         }

         return sum;

//        HashMap<Integer,  Integer> freq= new HashMap<Integer,
//                Integer>();
//
//        for(int i:args)
//        {
//            if(freq.containsKey(i))
//                freq.put(i, freq.get(i) + 1);
//            else
//                freq.put(i, 1);
//        }
//
//        // array of unique values taken
//        List<Integer> taken =  new ArrayList<>();
//
//        int ans = 0;
//
//        for (int x = 0; x < 100000; x++)
//        {
//
//            // If number is present
//            // multiple times
//            if (mpp.containsKey(x) && mpp.get(x) >= 2)
//            {
//                taken.add(x * (mpp.get(x)- 1));
//            }
//
//            // If there is no x in the array
//            else if(taken.size() > 0 &&
//                    ((mpp.containsKey(x) &&
//                            mpp.get(x) == 0)||!mpp.containsKey(x)))
//            {
//                ans += x - taken.get(taken.size() - 1);
//                taken.remove(taken.size() - 1);
//            }
//        }
    }
}
