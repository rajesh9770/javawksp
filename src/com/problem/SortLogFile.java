package com.problem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * Sort log file. First token in a line is a id. Rest is followed either by list of words or list of numbers.
 * Do not sort later. Sort the former, using lexicographical order.
 */
public class SortLogFile {

    public static class LogLine implements Iterable<String>, Comparable<LogLine>
    {
        String line;
        String [] tokens;
        public LogLine(String line){
            this.line = line;
            this.tokens = line.split(" ");
        }

        public LogLine(String line, String [] tokens){
            this.line = line;
            this.tokens = tokens;
        }

        @Override
        public Iterator<String> iterator() {
            return new Iterator<String>() {
                int idx = 0;
                StringBuilder buff = new StringBuilder();
                @Override
                public boolean hasNext() {
                    return line.length()>idx;
                }

                @Override
                public String next() {
                    buff.setLength(0);
                    while(idx<line.length()){
                        char c = line.charAt(idx++);
                        if(c != ' ') {
                            buff.append(c);
                        }
                        else break;
                    }
                    return buff.toString();
                }
            };
        }

        @Override
        public int compareTo(LogLine o) {
            for(int i=1; i< this.tokens.length && i < o.tokens.length; ++i){
                int rank = this.tokens[i].compareTo(o.tokens[i]);
                if(rank != 0) return rank;
            }
            int rank = this.tokens.length - o.tokens.length;
            if(rank != 0) return rank;
            return this.tokens[0].compareTo(o.tokens[0]);
        }
    }

    public static List<String> reorderLines(int logFileSize, List<String> logLines){

        List<LogLine> numLines = new ArrayList<>();
        TreeSet<LogLine> strLines = new TreeSet<>();

        for(int i=0; i<logFileSize; ++i){
            String logLine = logLines.get(i);
            String[] toks = logLine.split(" ");

            try{
                Integer.parseInt(toks[1]);
                numLines.add(new LogLine(logLine, toks));
            }catch (NumberFormatException e ){
                strLines.add(new LogLine(logLine,toks));
            }
        }


        List<String> ret = new ArrayList<>();
        Iterator<LogLine> it = strLines.iterator();
        while(it.hasNext()){
            LogLine next = it.next();
            ret.add(next.line);
        }
        it = numLines.iterator();
        while(it.hasNext()){
            LogLine next = it.next();
            ret.add(next.line);
        }
        return ret;
    }


    public static void main(String[] args) {
        //test1();
        //test2();
        test3();

    }

    private static void test1(){
        List<String> list = new ArrayList<>();
        list.add("[x01 1 2 2]");
        list.add("[x02 1 1 2]");
        list.add("[x03 ab ac ad]");
        list.add("[x04 ab ca ad]");
        list.add("[x04 Ab ca ad]");


        List<String> strs = reorderLines(5, list);
        for(String str : strs){
            System.out.println(str);
        }
    }

    private static void test2(){
        List<String> list = new ArrayList<>();
        list.add("[mi2 jog mid pet]");
        list.add("[wz3 34 54 398]");
        list.add("[a1 alps cow bar]");

        list.add("[x4 45 21 7]");
        //list.add("[x04 Ab ca ad]");


        List<String> strs = reorderLines(list.size(), list);
        for(String str : strs){
            System.out.println(str);
        }
    }

    private static void test3(){
        List<String> list = new ArrayList<>();

        list.add("[b4 xi me nu]");
        list.add("[br8 eat nim did]");
        list.add("[r1 box ape bit]");
        list.add("[w1 has uni gry]");

        list.add("[t2 13 121 98]");
        list.add("[f3 52 54 31]");


        //list.add("[x04 Ab ca ad]");


        List<String> strs = reorderLines(list.size(), list);
        for(String str : strs){
            System.out.println(str);
        }
    }
}
