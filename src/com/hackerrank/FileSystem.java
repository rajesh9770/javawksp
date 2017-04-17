package com.hackerrank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class FileSystem {

	private Node root = new Node("/", ""); 
	
	public static class Node{
        String id;
        String data;
        Map<String, Node> children;
        boolean dir;

        public Node(String id, String data) {
            this.data = data;
            this.id = id;
            children = new HashMap();
            dir = true;
        }

        public Node addEdge(Node child) {
            return children.put(child.id, child);
        }
        
        @Override
        public boolean equals(Object obj) {
        	if(obj instanceof Node){
        		return id.equals(((Node)obj).id);
        	}
        	return false;
        }
        
        @Override
        public int hashCode() {        	
        	return id.hashCode();
        }
        
        @Override
        public String toString() {        	
        	return id + " " + data;
        }
	}
	
	public List<String> ls(String path) throws Exception {
	    String[] split = path.split("/");
	    List<String> ret = new ArrayList<String>();
	    Node current = root;
	    for(int i=1; i<split.length; ++i){
	    	Node node = current.children.get(split[i]);
	    	if(node == null) throw new Exception("invalid path");
	    	current = node;
	    }
	    
	    //System.out.println(current.id);
	    for(Node files: current.children.values()){
	    	ret.add(files.id);
	    }
	    if(!current.dir) ret.add(current.id);
	    else{
	    	for(Node files: current.children.values()){
		    	ret.add(files.id);
		    }
	    }
	    return ret;
	}
	
	
	public void mkdirP(String dirPath) {
		String[] split = dirPath.split("/");
		//for(String t: split) System.err.println(dirPath + " -< "+ t);
		Node current = root;
	    for(int i=1; i<split.length; ++i){
	    	Node node = current.children.get(split[i]);
	    	if(node == null){
	    		node = new Node(split[i], "");
	    		node.dir = true;
	    		//System.out.println("adding " + split[i] + " under " + current );
	    		current.children.put(split[i], node);
	    	}
	    	current = node;
	    }
	}
	
	public void addFileWithContent(String filePath, String content) {
		String[] split = filePath.split("/");
		Node current = root;
	    for(int i=1; i<split.length; ++i){
	    	Node node = current.children.get(split[i]);
	    	if(node == null){
	    		node = new Node(split[i], i==split.length-1 ? content : "");
	    		current.children.put(split[i], node);
	    	}
	    	current = node;
	    }    
	    current.dir = false;
	}
	 
	public String getFileContent(String filePath) throws Exception {
		String[] split = filePath.split("/");
	    
	    Node current = root;
	    for(int i=1; i<split.length; ++i){
	    	Node node = current.children.get(split[i]);
	    	if(node == null) throw new Exception("invalid path");
	    	current = node;
	    }
	    return current.data;
	}
	
	public static void main(String[] args) throws Exception {
		// assumption: all path starts with / and do not end with /
	    FileSystem fs = new FileSystem();

	    // should print []
	    System.out.println(fs.ls("/"));

	    fs.mkdirP("/a/b/c");
	    fs.addFileWithContent("/a/b/c/d", "hello world");

	    // should print [a]
	    System.out.println(fs.ls("/"));

	    // should print [d]
	    System.out.println(fs.ls("/a/b/c"));

	    // should print [d]
	    System.out.println(fs.ls("/a/b/c/d"));

	    // should print hello world
	    System.out.println(fs.getFileContent("/a/b/c/d"));

	}

}
