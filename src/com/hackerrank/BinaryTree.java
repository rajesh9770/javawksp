package com.hackerrank;

import java.util.Iterator;
import java.util.Stack;
import java.util.function.Consumer;

public class BinaryTree<T> {

    public static class Node<T>{
        T data;
        Node<T> left,  right;
        
        Node(T data){
            this.data = data;
            this.left = this.right = null;
        }
        @Override
        public String toString() {
            return data.toString();
        }
    }
    
    private Node<T> root;
    
    public BinaryTree(T data) {
        root = new Node<T> (data);
    }

    public void addLeftChild(Node parent, Node leftChild) {
        parent.left = leftChild;
    }
    
    public void postOrderTraversal(Node <T> root, Consumer<Node<T>> f){
        if (root==null) return;
        //System.out.println("** " + root.toString() + " " + root.left + " " + root.right);
        if (root.left != null) postOrderTraversal(root.left, f);
        if (root.right != null) postOrderTraversal(root.right, f);
        f.accept(root);
    }

    
    public class postTraverseIterator implements Iterator<Node<T>> {

        private Node<T> prev; //need prev to track when we have to right from root
        private Stack<Node<T>> parents;
        
        public postTraverseIterator() {
            prev = null;
            parents  = new Stack<>();
            //start from leftmost leaf (not necessarily left most node)
            if(root != null){
                getLeftMostLeaf(root, parents);
            }
        }
        
        public Node<T> getLeftMostLeaf(Node<T> node, Stack<Node<T>> parents){
            if(node ==null) return null;
            Node<T> tmp = node;
            while(tmp!=null){
                parents.push(tmp);
                if(tmp.left != null) {
                    tmp = tmp.left;
                } else {
                    tmp = tmp.right;
                }
            }
            return parents.peek();
        }
        @Override
        public boolean hasNext() {
            if(prev==root) return false;
            else return true;
        }

        @Override
        public Node<T> next() {
            Node<T> next=null;
            if(prev==null){
                next = parents.pop();
            }else{
                Node<T> parent = parents.peek();
                if(parent.left == prev){ //if prev is left-child of curr, then before giving the curr we have to go right and find the left most leaf.
                    if(parent.right != null){
                        getLeftMostLeaf(parent.right, parents);
                    }
                }
                next = parents.pop();
            }
            prev = next;
            return next;
        }

    }

    public class inorderTraverseIterator implements Iterator<Node<T>> {
        private Stack<Node<T>> parents;

        //An iterator would start with the leftmost node
        public inorderTraverseIterator(){
            parents = new Stack<>();
            getLeftMostNode(root, parents);
        }

        /**
         * Inorder is easier than postorder. Just get the left most node (NOT leaf)
         * @param node
         * @param parents
         * @return
         */
        public Node<T> getLeftMostNode(Node<T> node, Stack<Node<T>> parents){
            if(node ==null) return null;
            Node<T> tmp = node;
            while(tmp!=null){
                parents.push(tmp);
                tmp = tmp.left;
            }
            return parents.peek();
        }

        @Override
        public boolean hasNext() {
            return !parents.empty();
        }

        @Override
        public Node<T> next() {
            Node current = parents.pop();

            if (current.right != null)
                getLeftMostNode(current.right, parents);

            return current;
        }
    }


    public class preorderTraverseIterator implements Iterator<Node<T>> {
        private Stack<Node<T>> parents;

        //An iterator would start at root
        public preorderTraverseIterator(){
            parents = new Stack<>();
            parents.push(root);
        }



        @Override
        public boolean hasNext() {
            return !parents.empty();
        }

        /**
         * Pop one. Do following for every popped item
         *          a) print it
         *          b) push its right child
         *          c) push its left child
         *          Note that right child is pushed first so that left is processed first
         * @return
         */
        @Override
        public Node<T> next() {
            Node current = parents.pop();

            if (current.right != null){
                parents.push(current.right);
            }
            if (current.left != null){
                parents.push(current.left);
            }

            return current;
        }
    }

    public static void main(String[] args) {
        BinaryTree<Integer> tree = new BinaryTree<Integer>(45);
        Node<Integer> left = tree.root.left= new Node<Integer>(25);
        Node<Integer> right = tree.root.right= new Node<Integer>(55);
        right.right = new Node<Integer>(65);
        Node<Integer> leftN = left.left = new Node<Integer>(15);
        right = left.right = new Node<Integer>(35);
        leftN.left = new Node<Integer>(5);
        right.left = new Node<Integer>(77);
        right.right = new Node<Integer>(88);
        
        tree.postOrderTraversal(tree.root, n -> {System.out.println(n.data);});
        System.out.println(" ");
        //5   15 77 88  35  25  65  55  45
        BinaryTree<Integer>.postTraverseIterator it = tree.new postTraverseIterator();
        while(it.hasNext()){
            System.out.print(it.next() + " ");
        }

    }
    

}
