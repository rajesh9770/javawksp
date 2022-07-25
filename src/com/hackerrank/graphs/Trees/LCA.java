package com.hackerrank.graphs.Trees;



/**
 * Created by Rajesh on 6/5/2018.
 */
public class LCA {

    public static class Node{
        int id;
        Node left, right;

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
        }else if(left.result != null || right.result != null){
            //this means we found either p or q (exactly one of them) in one of the subtree.
            //if other is a root, then root is a ancestor
            if(root.equals(p) || root.equals(q)){
                return new Result(root, true);
            }else{//else return the non null result.
                return left.result != null ? left : right;
            }
        }else{
            return new Result(null, false);
        }
    }
}
