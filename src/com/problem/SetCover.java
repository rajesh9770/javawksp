package com.problem;

import java.util.*;

public class SetCover {

    public int[] smallestSufficientTeam(String[] req_skills, List<List<String>> people) {

        Map<String, Integer> skillToInt = new HashMap<>();
        for(int i=0; i<req_skills.length; ++i){
            skillToInt.put(req_skills[i], i);
        }
        List<BitSet> peopleSkills = new ArrayList<>();
        for(List<String> p: people) {
            BitSet skills = new BitSet(req_skills.length);
//            for (String skill: p) {
//                skills.set(skillToInt.get(sk));
//            }
        }
        return null;
    }

    public static void main(String[] args) {

    }
}
