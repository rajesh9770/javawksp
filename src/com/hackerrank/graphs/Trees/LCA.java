package com.hackerrank.graphs.Trees;


import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Rajesh on 6/5/2018.
 */
public class LCA {

    public static class Node{
        int id;
        Node left, right;

        public Node(int i){
            id = i;
        }
        @Override
        public boolean equals(Object obj) {
            if(obj instanceof  Node)
                return id == ((Node)obj).id;
            else return false;
        }
    }
    public static class Result{
        Node result;
        boolean isAncestor;
        public Result(Node result, boolean isAncestor){
            this.result = result;
            this.isAncestor = isAncestor;
        }
    }

    public static Node findLCA(Node root, Node p, Node q){
        Result res = helper(root, p, q);
        if(res.isAncestor) return res.result;
        return null;
    }

    public static Result helper(Node root, Node p, Node q){
        if(root == null) return new Result(null, false);
        if(root.equals(p) && root.equals(q)) return new Result(root, true); //if both nodes are equal to the root, then it's the root

        Result left = helper(root.left, p, q);
        if(left.isAncestor) return left;
        Result right = helper(root.right, p, q);
        if(right.isAncestor) return right;

        if(left.result != null && right.result != null){
            return new Result(root, true);
        }else if(root.equals(p) || root.equals(q)){
            //this means we found either p or q (exactly one of them) in one of the subtree.
            //if other is a root, then root is a ancestor
            return new Result(root, (left.result != null || right.result != null) );
        }else{
            return new Result(left.result != null ? left.result : right.result, false);
        }
    }

    public static void main(String[] args) {
        Node root = new Node(1);

        Node level1_left = new Node(10);
        root.left = level1_left;
        Node level1_right = new Node(20);
        root.right = level1_right;

        Node level2_left = new Node(100);
        level1_right.left = level2_left;
        Node level2_right = new Node(110);
        level1_right.right = level2_right;

        Node lca = findLCA(root, root, level2_right);
        System.out.println(lca.id);

    }


    // Returns -1 if x doesn't exist in tree. Else
    // returns distance of x from root
    static int findDistanceRecursively(Node root, Node x)
    {
        // Base case
        if (root == null)
            return -1;

        // Initialize distance
        int dist = -1;

        // Check if x is present at root or in left
        // subtree or right subtree.
        if ((root.equals(x)) ||
                (dist = findDistanceRecursively(root.left, x)) >= 0 ||
                (dist = findDistanceRecursively(root.right, x)) >= 0)
            return dist + 1;

        return dist;
    }

    // Returns -1 if x doesn't exist in the tree. Else returns the distance of x from the root
    static int findDistanceIteratively(Node root, Node x) {
        if (root == null) {
            return -1;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        int dist = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();//read only the next level
            for (int i = 0; i < size; i++) {
                Node curr = queue.poll();
                if (curr.equals(x)) {
                    return dist;
                }
                if (curr.left != null) {
                    queue.add(curr.left);
                }
                if (curr.right != null) {
                    queue.add(curr.right);
                }
            }
            dist++;
        }

        return -1;
    }
}
