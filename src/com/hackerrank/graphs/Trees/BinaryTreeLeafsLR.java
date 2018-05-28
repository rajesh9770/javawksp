package com.hackerrank.graphs.Trees;

/**
 * Created by Rajesh on 3/11/2018.
 * Remove the leafs of binary tree from Left to right and put it in a doubly linked list.
 */
public class BinaryTreeLeafsLR {

    public  static class Node{
        int data;
        Node left, right;
        public Node(int data){
            this.data = data;
            left = right = null;
        }
    }

    public static class BinaryTree{
        Node root;
        Node head, prev;


        public Node extractLeafList(Node root){
            if(root == null) return  null;
            if(root.left == null && root.right == null){//leaf node
                if(head == null){
                    head = root;
                    prev = root;
                }else{
                    prev.right = root;
                    root.left = prev;
                    prev = root;
                }
                return null;
            }else{
                root.left = extractLeafList(root.left);
                root.right = extractLeafList(root.right);
                return root;
            }
        }


        void inorder(Node node)
        {
            if (node == null)
                return;
            inorder(node.left);
            System.out.print(node.data + " ");
            inorder(node.right);
        }

        public void printDLL(Node head) {
            while(head != null){
                System.out.println(head.data);
                head = head.right;
            }
        }
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.root = new Node(1);
        tree.root.left = new Node(2);
        tree.root.right = new Node(3);

        tree.root.left.left = new Node(4);
        tree.root.left.right = new Node(5);
        tree.root.right.right = new Node(6);
        tree.root.left.left.left = new Node(7);
        tree.root.left.left.right = new Node(8);
        tree.root.right.right.left = new Node(9);
        tree.root.right.right.right = new Node(10);

        System.out.println("Inorder traversal of given tree is : ");
        tree.inorder(tree.root);
        tree.extractLeafList(tree.root);
        System.out.println("");
        System.out.println("Extracted double link list is : ");
        tree.printDLL(tree.head);
        System.out.println("");
        System.out.println("Inorder traversal of modified tree is : ");
        tree.inorder(tree.root);
    }
}
