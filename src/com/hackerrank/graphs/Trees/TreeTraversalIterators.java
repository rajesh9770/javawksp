package com.hackerrank.graphs.Trees;

import java.util.Iterator;
import java.util.Stack;

public class TreeTraversalIterators {

    public static class Node {
        int val;
        String str;
        Node left, right;

        Node(int val){
            this.val = val;
        }
        Node(String str){
            this.str = str;
        }

        @Override
        public String toString() {
            return str != null ? str : ""+val;
        }

        Node setLeft(String str){
            left = new Node(str);
            return left;
        }

        Node setRight(String str){
            right = new Node(str);
            return right;
        }
    }


    void inorder(Node  n)
    {
        if(n == null) return;
        inorder(n.left);
        visit(n);
        inorder(n.right);
    }
    void preorder(Node n)
    {
        if(n == null) return;
        visit(n);
        preorder(n.left);
        preorder(n.right);
    }
    void postorder(Node  n)
    {
        if(n == null) return;
        postorder(n.left);
        postorder(n.right);
        visit(n);
    }

    private void visit(Node n) {
        System.out.print(n);
    }

    //Inorder succesor
    public class InorderSuccesor implements Iterator{
        Stack<Node> stack = new Stack();

        InorderSuccesor(Node root){
            Node tmp = root;
            while(tmp != null){ //An iterator would start with the leftmost node
                stack.push(tmp);
                tmp=tmp.left;
            }
        }
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public Object next() {
            if (stack.isEmpty()) return null;
            Node ret = stack.pop();
            Node tmp = ret.right; //go right and then left
            while(tmp != null){
                stack.push(tmp);
                tmp=tmp.left;
            }
            return ret;
        }
    }


    //Preorder succesor
    public class PreorderSuccesor implements Iterator{
        Stack<Node> stack = new Stack();

        PreorderSuccesor(Node root){
            stack.push(root);//An iterator would start with the root of the tree.
        }
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public Object next() {
            if (stack.isEmpty()) return null;
            Node ret = stack.pop();
            if (ret.right != null) { //insert right node first, since we will visit it after visiting left node.
                stack.push(ret.right);
            }
            if (ret.left != null) {
                stack.push(ret.left);
            }
            return ret;
        }
    }


    //Postorder succesor
    public class PostorderSuccesor implements Iterator{
        Stack<Node> stack = new Stack();

        PostorderSuccesor(Node root){
            stack.push(root);//An iterator would start with the leftmost leaf (not necessarily the leftmost node).
            Node tmp = root.left != null ? root.left : root.right;
            while(tmp != null) {
                stack.push(tmp);
                tmp = tmp.left != null ? tmp.left : tmp.right;
            }
        }
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public Object next() {
            if (stack.isEmpty()) return null;
            Node ret = stack.pop();
            if( !stack.isEmpty()) { //if removed element is the left child of top of stack, then we are visiting a parent, so before that go to the right and find the next left most child.
                Node parent = stack.peek();
                if (parent.left == ret) {
                    Node tmp = parent.right;
                    while (tmp != null) {
                        stack.push(tmp);
                        tmp = tmp.left != null ? tmp.left : tmp.right;
                    }
                }
            }
            return ret;
        }
    }

    public static void main(String[] args) {
        Node root = new Node("A");
        root.setLeft("B").setRight("D").setLeft("E");
        root.setRight("C");
        TreeTraversalIterators treeTraversalIterators = new TreeTraversalIterators();
        treeTraversalIterators.postorder(root);
        System.out.println("");

        PostorderSuccesor postorderSuccesor = treeTraversalIterators.new PostorderSuccesor(root);
        while (postorderSuccesor.hasNext()){
            System.out.print(postorderSuccesor.next());
        }
        System.out.println("");
        root.right.setLeft("F").setRight("G");
        root.right.setRight("H").setLeft("I");
        treeTraversalIterators.postorder(root);
        System.out.println("");

        postorderSuccesor = treeTraversalIterators.new PostorderSuccesor(root);
        while (postorderSuccesor.hasNext()){
            System.out.print(postorderSuccesor.next());
        }
    }
}
