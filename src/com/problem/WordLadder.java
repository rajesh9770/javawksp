package com.problem;

import java.util.*;


public class WordLadder {

    public static List<String> ladderLength(String beginWord, String endWord, List<String> wordList) {
        Map<String, List<String>> neighborList = preProcess(wordList);
        HashSet<String> visited = new HashSet<>();
        visited.add(beginWord);

        List<String> levelNodes = new ArrayList<>();
        levelNodes.add(beginWord);

        Map<String, String> parents = new HashMap<>();
        parents.put(beginWord, null);
        boolean found = false;
        while(! levelNodes.isEmpty() && !found){
            List<String> nextNeighbors = new ArrayList<>();

            for(String neighbor : levelNodes){
                if(neighbor.equalsIgnoreCase(endWord)) {
                    found = true;
                    break;
                }
                getNeighbors(neighbor, neighborList).forEach(newNeighbor ->
                        {
                            if(!parents.containsKey(newNeighbor)) {
                                parents.put(newNeighbor, neighbor);
                                nextNeighbors.add(newNeighbor);
                            }
                        }
                );
            }
            levelNodes = nextNeighbors;
        }
        List<String> ladder = new ArrayList<>();
        if(found) {
            String node = endWord;
            while(parents.containsKey(node)){
                ladder.add(node);
                node = parents.get(node);
            }
        }else{
            ladder.add("Not Possible");
        }
        return ladder;
    }

    private static Map<String, List<String>> preProcess(List<String> wordList) {
        Map<String, List<String>> neighbors = new HashMap<>();
        wordList.forEach(word ->{
            for(int i=0; i<word.length(); ++i){

                String oneoffNeighbour = word.substring(0, i) + "*" + word.substring(i + 1);
                List<String> neighbor = neighbors.get(oneoffNeighbour);
                if(neighbor == null) {
                    neighbor = new ArrayList<>();
                    neighbors.put(oneoffNeighbour, neighbor);
                }
                neighbor.add(word);
            }
        }
        );
        return neighbors;
    }

    private static List<String> getNeighbors(String word, Map<String, List<String>> neighborList) {
        List<String> ret = new ArrayList<>();
        for(int i=0; i<word.length(); ++i){
            List<String> neighbors = neighborList.get(word.substring(0, i) + "*" + word.substring(i + 1));
            if(neighbors != null) ret.addAll(neighbors);
        }
        return ret;
    }


    public static void main(String[] args) {
            test1();
            test2();
    }

    public static void test1(){
        List <String> wordList = new ArrayList<>();
        wordList.add("hot");
        wordList.add("dot");
        wordList.add("dog");
        wordList.add("lot");
        wordList.add("log");
        wordList.add("cog");
        List<String> strings = ladderLength("hit", "cog", wordList);
        strings.forEach(w->
                System.out.printf("%s ", w)
        );
        System.out.println();

    }

    public static void test2(){
        List <String> wordList = new ArrayList<>();
        wordList.add("hot");
        wordList.add("dot");
        wordList.add("dog");
        wordList.add("lot");
        wordList.add("log");
        //wordList.add("cog");
        List<String> strings = ladderLength("hit", "cog", wordList);
        strings.forEach(w->
                System.out.printf("%s ", w)
        );
        System.out.println();
    }
}
