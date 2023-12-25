package com.problem;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Rajesh on 5/24/2017.
 */
public class LRU<K,V> {

    public static class Node<K,V>{
        private V data;
        private K key;
        private Node prev, next;

        protected Node(K key, V data){
            this.key = key;
            this.data = data;
            prev=next=null;
        }
    }

    private Map<K,Node<K,V>> cache;
    private Node<K,V> head, tail;
    private int size;

    public LRU(int size){
        cache = new HashMap<>();
        head=tail=null;
        this.size = size;
    }

    public void put(K key, V val){
        if(cache.containsKey(key)){
            Node node = cache.get(key);
            node.data = val;
            evictFromList(node);
            setHeadOfList(node);
            return;
        }else{
            if(cache.size() >= size) {
                cache.remove(tail.key);
                evictFromList(tail);
            }
            Node newNode = new Node(key, val);
            cache.put(key, newNode);
            setHeadOfList(newNode);
        }
    }

    /**
     * Node can be a new node, i.e. it's next and prev can be null.
     * Sets the head of the list to this new node.
     * @param node
     */
    private void setHeadOfList(Node node) {
        node.prev = null;
        node.next = head;

        if(head != null) {
            head.prev = node;
            head = node;
        }else {
            head = tail = node;
        }
    }

    /**
     * Removes node from the list. Assumes that the node is present in the list since it changes head and tail accordingly.
     * @param node
     */
    private void evictFromList(Node node){
        // remove the node from list
        Node prev = node.prev;
        if(prev != null){
            prev.next = node.next;
        }else{//node was the head
            head = node.next;
        }
        Node next = node.next;
        if(next != null){
            next.prev = node.prev;
        }else{//node was the tail
            tail = node.prev;
        }
    }

    public V get(K key){
        if(cache.containsKey(key)){
            Node<K, V> kvNode = cache.get(key);
            evictFromList(kvNode);
            setHeadOfList(kvNode);
            return kvNode.data;
        }else return null;
    }

    public void print(){
        Node t =head;
        while(t!= null){
            System.out.print(t.data + " ");
            t = t.next;
        }
        System.out.println("");
    }

    public static void main(String[] args) {

        StringBuilder buff = new StringBuilder();
        buff.append("TEST_");
        int len = buff.length();

        buff.setLength(len);
        buff.append("key1");
        System.out.println(buff);

        buff.setLength(len);
        buff.append("key2");
        System.out.println(buff);
        if(true) return;

        LRU<Integer, Integer> cache = new LRU<>(2);
        cache.put(1, 1);
        //cache.print();
        cache.put(2, 2);
        //cache.print();
        System.out.println(cache.get(1));       // returns 1
        //cache.print();
        cache.put(3, 3);    // evicts key 2
        System.out.println(cache.get(2));       // returns -1 (not found)
        cache.put(4, 4);    // evicts key 1
        System.out.println(cache.get(1));       // returns -1 (not found)
        System.out.println(cache.get(3));       // returns 3
        System.out.println(cache.get(4));       // returns 4
    }


    class Test{

        Map<String, Node2> map = new HashMap<>();
        Node2 head = null, tail = null;

        class Node2{
            String k,v;
            Node2 prev, next;

            Node2(String k, String v){
                this.k = k;
                this.v = v;
                this.prev = this.next = null;
            }
        }


        void removeKey(Node2 node){
            Node2 prev = node.prev;
            if(prev != null){
                prev.next = node.next;
            }else{
                head = node.next;
            }

            Node2 next = node.next;
            if(next != null){
                next.prev = node.prev;
            }else{
                tail = node.prev;
            }
        }

        void addKey(Node2 node){
            node.prev = null;
            node.next = head;

            if(head != null){
                head.prev = node;
            }else{
                head = tail = node;
            }

        }
    }


}
