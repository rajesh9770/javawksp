package com.hackerrank.utils;

import java.util.LinkedList;

/**
 * Created by Rajesh on 7/29/2017.
 */

public class TrieNode<T> {
    private LinkedList<TrieNode<T>> children;
    private boolean isLast;

    private T id;
    private int count;

    public TrieNode() {
        count = 0;
        children = new LinkedList<TrieNode<T>>();
    }


    public TrieNode(T data) {
        this();
        this.id = data;
    }

    public T getId() {
        return id;
    }

    public void addWord(T[] word, int startIdx) {
        if (word == null || startIdx <0 || startIdx >= word.length) {
            return;
        }

        TrieNode<T> child;
        T nextId = word[startIdx];
        TrieNode<T> t = getChild(nextId);

        if (t == null) {
            child = new TrieNode<>(nextId);
            children.add(child);
        } else {
            child = t;
        }

        if (startIdx+1  < word.length) {
            child.addWord(word, startIdx + 1);
        } else {
            child.setLast(true);
        }
    }

    TrieNode<T> getChild(T c) {
        for (TrieNode t : children) {
            if (t.getId().equals(c)) {
                return t;
            }
        }
        return null;
    }

    public boolean isLast() {
        return isLast;
    }

    public int getCount(){
        return count;
    }
    public void setLast(boolean t) {
        isLast = t;
        count++;
    }
}
