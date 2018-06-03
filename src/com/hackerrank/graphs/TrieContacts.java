package com.hackerrank.graphs;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rajesh on 6/2/2018.
 */
public class TrieContacts {

    public static class TrieNode{
        private Map<Character, TrieNode> children;
        private boolean isLast;

        private char id;
        private int count;
        public long numOfchildren; //optimization

        public TrieNode(char c) {
            this.id = c;
            this.children = new HashMap<>();
            this.isLast = false;
        }

        public void addWord(char[] word, int startIdx){

            char next = word[startIdx];

            TrieNode child = children.get(next);
            if (child == null){
                child = new TrieNode(next);
                children.put(next, child);
            }
            child.numOfchildren++; // increment this child. This implies root element will have numOfchildren = 0;
            if(startIdx+1<word.length) child.addWord(word, startIdx+1);
            else child.setLast();
        }


        public void setLast() {
            isLast = true;
            count++; //keeps tracks of how many time this word was inserted in the trie.
        }

        public long getNumOfChildren(){
            long ct = isLast ? 1 : 0;
            for(TrieNode child: children.values()){
                ct += child.getNumOfChildren();
            }
            return ct;
        }


    }


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        TrieNode root = new TrieNode('\\');
        for (int nItr = 0; nItr < n; nItr++) {
            String[] opContact = scanner.nextLine().split(" ");

            String op = opContact[0];

            String contact = opContact[1];
            if(op.equals("add")) {
                root.addWord(contact.toCharArray(), 0);
            }else if(op.equals("find")){
                TrieNode node = root;
                for(int i =0; i<contact.length() && node != null; ++i){
                    node = node.children.get(contact.charAt(i));
                }
                if(node == null){
                    System.out.println("0");
                }else{
                    System.out.println(node.numOfchildren);
                }
            }
        }

        scanner.close();
    }
}
/*Testcase:
11
add s
add ss
add sss
add ssss
add sssss
find s
find ss
find sss
find ssss
find sssss
find ssssss
5
4
3
2
1
0
*/