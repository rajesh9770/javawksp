package com.hackerrank.stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Rajesh on 6/1/2018.
 */
public class BalancedParentheses {

    /**
     * Input:
     3
     {[()]}
     {[(])}
     {{[[(())]]}}
     */
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        Map<Character, Character> matchingChar = new HashMap<>();
        matchingChar.put('{', '}');
        matchingChar.put('[', ']');
        matchingChar.put('(', ')');
        for (int tItr = 0; tItr < t; tItr++) {
            String expression = scanner.nextLine();
            Stack<Character> st = new Stack<>();
            boolean matchFound = true;
            for(int i=0; i<expression.length() && matchFound; ++i){
                char c = expression.charAt(i);

                switch (c){
                    case '{' :
                    case '[' :
                    case '(' :
                        st.push(c);
                        break;
                    case '}' :
                    case ']' :
                    case ')' :
                        if(!st.isEmpty() && st.peek() == matchingChar.get(c)) st.pop();
                        else matchFound = false;
                        break;
                    default:
                        System.out.println("Unknown character " + c);
                }
                if(!matchFound) break;
            }
            //both need to be check matchFound and stack should be empty
            // consider [[(()) and (())[
            System.out.println(matchFound && st.isEmpty() ? "YES" : "NO");
        }
        scanner.close();
    }
}
