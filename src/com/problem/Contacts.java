package com.problem;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'contacts' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts 2D_STRING_ARRAY queries as parameter.
     */

    public static List<Integer> contacts(List<List<String>> queries) {
        Trie trie = new Trie();
        List<Integer> ret = new ArrayList<>();
        for (List<String> query : queries) {
            if(query.size() == 2){
                String cmd = query.get(0);
                if(cmd.equals("add")) {
                    trie.addWord(query.get(1));
                }else if(cmd.equals("find")){
                    ret.add(trie.findPartial(query.get(1)));
                }
            }

        }
        return ret;
    }

}

class Trie {
    TrieNode root;


    Trie(){
        root = new TrieNode();
    }

    void addWord(String word){
        root.addWord(word.toCharArray(), 0);
    }

    int findPartial(String word){
        //return root.findPartial(word.toCharArray(), 0);
        char[] chars = word.toCharArray();
        TrieNode curr = null;
        for (char aChar : chars) {
            curr = curr.children[aChar-'a'];
            if(curr == null){
                return 0;
            }
        }
        return curr.size;
    }
}

class TrieNode {
    TrieNode[] children ;
    Set<Integer> childIdx;
    boolean matches;
    int size;



    TrieNode(){
        this.children = new TrieNode[26];
        childIdx = new HashSet<>();
        this.matches = false;
        this.size = 0;
        for(int i=0; i<26; ++i){
            children[i] = null;
        }
    }

    void addWord(char[] word, int startIdx){
        if(word != null && word.length > startIdx){
            int charCode = word[startIdx] - 'a';
            TrieNode child = children[charCode];
            if(child == null) {
                child = children[charCode] = new TrieNode();
                childIdx.add(charCode);
            }
            child.size++;
            if(word.length == startIdx+1) {
                child.matches = true;
            }else {
                child.addWord(word, startIdx + 1);
            }
        }
    }

    int findPartial(char[] word, int startIdx){
        if(word != null && word.length > startIdx){
            int charCode = word[startIdx] - 'a';
            if(children[charCode] != null){
                return children[charCode].findPartial(word, startIdx+1);
            }else{
                return 0;
            }
        }
        return countAllChildren();
    }

    private int countAllChildren() {
        return size;
//        int total = this.matches ? 1 : 0;
//        if(childIdx.size()>0) {
//            for (Integer idx : childIdx) {
//                TrieNode child = children[idx];
//                if (child != null) {
//                    total += child.countAllChildren();
//                }
//            }
//        }
//        return total ;
    }

}

public class Contacts {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int queriesRows = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<String>> queries = new ArrayList<>();

        IntStream.range(0, queriesRows).forEach(i -> {
            try {
                queries.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> result = Result.contacts(queries);

        bufferedWriter.write(
                result.stream()
                        .map(Object::toString)
                        .collect(joining("\n"))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
