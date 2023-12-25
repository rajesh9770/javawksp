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




    public List<String> generateParenthesis2(int n) {
        List<String> res=new ArrayList();
        StringBuilder build=new StringBuilder();
        helper(build,0,0,res,n);
        return res;
    }
    public void helper(StringBuilder build,int op,int cl,List<String> res,int n){
        if (build.length() == 2 * n) {
            res.add(build.toString());
            return;
        }

        //two options; we could either start new parenthesis or close old one.
        if (op < n) { //we could start  a new parenthesis
            build.append("(");
            helper(build, op + 1, cl, res, n);
            build.deleteCharAt(build.length() - 1);
        }

        if (cl < op) { //we can close the previously open parenthesis
            build.append(")");
            helper(build, op, cl + 1, res, n);
            build.deleteCharAt(build.length() - 1);
        }
    }
}
