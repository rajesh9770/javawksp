package com.hackerrank.recursion;

import java.util.ArrayList;
import java.util.List;

public class ValidSetOfParenthesis {

    public static void main(String[] args) {
        List<String> strings = new ValidSetOfParenthesis().generateParenthesis(3);
        System.out.println(strings);
    }

    public List<String> generateParenthesis(int n) {
        List<String> ret = new ArrayList<>();
        setParenthesis(n, n, ret, new char[2*n], 0);
        return ret;
    }

    public void setParenthesis(int open, int close, List<String> ret, char[] buffer, int bufferIdx) {
        if( open == 0 && close == 0){
            ret.add(new String(buffer));
        } else if (open > 0) {
            buffer[bufferIdx] = '(';
            setParenthesis(open-1, close, ret, buffer, bufferIdx+1);
            if (open<close) {
                buffer[bufferIdx] = ')';
                setParenthesis(open, close-1, ret, buffer, bufferIdx+1);
            }
        } else if(close>0) {
            buffer[bufferIdx] = ')';
            setParenthesis(open, close-1, ret, buffer, bufferIdx+1);
        }
    }
}
