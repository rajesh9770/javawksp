package com.hackerrank.utils;

import java.util.List;

/**
 * Created by Rajesh on 7/29/2017.
 */

public class Trie<T> {

    private TrieNode<T> root;

    public Trie(){
        root = new TrieNode<T>();
    }
    public Trie(List<T[]> list)
    {
        root = new TrieNode<T>();
        for (T[] word : list) {
            root.addWord(word, 0);
        }
    }

    public Trie(T[][] list)
    {
        root = new TrieNode<T>();
        for (T[] word : list) {
            root.addWord(word, 0);
        }
    }

    public void addWord(T[] word){
        root.addWord(word, 0);
    }

    public int contains(T[] prefix, boolean exact)
    {
        TrieNode<T> lastNode = root;
        int i = 0;
        for (i = 0; i < prefix.length; i++) {
            lastNode = lastNode.getChild(prefix[i]);
            if (lastNode == null) {
                return -1;
            }
        }
        if(lastNode.isLast()){
            return lastNode.getCount(); // 1 or higher means exact match
        }else{
            if(exact) return -1;  // -1 means no match
            else return 0; // 0 means it's partial match
        }
    }

    public int contains(T[] prefix) {
        return contains(prefix, false);
    }

}
