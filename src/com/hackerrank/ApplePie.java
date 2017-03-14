package com.hackerrank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ApplePie {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int testcases = in.nextInt();
        while(testcases-- >0){
            int n = in.nextInt();
            Map<String, Boolean> passwds = new HashMap<String, Boolean>();
            for(int i=0; i<n; ++i){
                passwds.put(in.next(), true);
            }
            //for(String a: passwds) System.out.println(a);
            String password = in.next();
            List<String> words = new ArrayList<String>();
            if(solve(passwds, password, words)) {
                //System.out.println("**********");
                StringBuilder buff = new StringBuilder();
                for(String s: words)
                    buff.append(s + " ");
                
                System.out.println(buff.toString().trim());
            }
            else System.out.println("WRONG PASSWORD");
        }
        in.close();
    }

    private static boolean solve(Map<String, Boolean> passwds, String password, List<String> words) {
        //System.out.println(password);
        if(passwds.containsKey(password) && passwds.get(password)){
            words.add(password);
            return true;
        }
        if(passwds.containsKey(password) && !passwds.get(password)){
            return false;
        }
        for(int i=1; i<password.length(); ++i){
            String prefix = password.substring(0, i);
            String suffix = password.substring(i);
            //System.out.println(prefix + " " + suffix);
            if(passwds.containsKey(prefix) &&passwds.get(prefix)){
                words.add(prefix);
//                StringBuilder buff = new StringBuilder();
//                for(String a: words){
//                    buff.append(a);
//                }
//                passwds.put(buff.toString(), true);
                int idx = words.size();
                if(solve(passwds, suffix, words)) return true;
                else  words.remove(idx-1);
            }
        }
        passwds.put(password, false);
        return false;
    }

}
