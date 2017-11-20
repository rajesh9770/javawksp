package com.hackerrank.stack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by Rajesh on 11/18/2017.
 */
public class Test {

    static long[] maxMin(String[] operations, int[] x) {
        long [] ret = new long[x.length];
        Queue<Integer> max = new PriorityQueue<>();
        Queue<Integer> min = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
        max.add(x[0]);
        min.add(x[0]);
        ret[0] = (x[0]*x[0]);
        for(int i=1; i<x.length; ++i){
            if(operations[i].equals("pop")){
                max.remove(x[i]);
                min.remove(x[i]);
                ret[i] = max.peek() * min.peek();
            }else{
//                int cmin = Math.min(x[i], min.peek());
//                int cmax = Math.max(x[i], max.peek());
                max.add(x[i]);
                min.add(x[i]);
                ret[i] = max.peek() * min.peek();
            }
        }
        return ret;
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        final String fileName = System.getenv("OUTPUT_PATH");
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        long[] res;

        int _operations_size = 0;
        _operations_size = Integer.parseInt(in.nextLine().trim());
        String[] _operations = new String[_operations_size];
        String _operations_item;
        for(int _operations_i = 0; _operations_i < _operations_size; _operations_i++) {
            try {
                _operations_item = in.nextLine();
            } catch (Exception e) {
                _operations_item = null;
            }
            _operations[_operations_i] = _operations_item;
        }


        int _x_size = 0;
        _x_size = Integer.parseInt(in.nextLine().trim());
        int[] _x = new int[_x_size];
        int _x_item;
        for(int _x_i = 0; _x_i < _x_size; _x_i++) {
            _x_item = Integer.parseInt(in.nextLine().trim());
            _x[_x_i] = _x_item;
        }

        res = maxMin(_operations, _x);
        for(int res_i=0; res_i < res.length; res_i++) {
            bw.write(String.valueOf(res[res_i]));
            bw.newLine();
        }

        bw.close();
    }


    public static void main2(String[] args) throws IOException{
        Scanner in = new Scanner(System.in);
        final String fileName = System.getenv("OUTPUT_PATH");
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        int res;

        String[] _friends_nodesm = in.nextLine().split(" ");
        int _friends_nodes = Integer.parseInt(_friends_nodesm[0]);
        int _friends_edges = Integer.parseInt(_friends_nodesm[1]);

        int[] _friends_from = new int[_friends_edges];
        int[] _friends_to = new int[_friends_edges];

        for (int _friends_i = 0; _friends_i < _friends_edges; _friends_i++) {
            String[] _friends_fromv = in.nextLine().split(" ");
            _friends_from[_friends_i] = Integer.parseInt(_friends_fromv[0]);
            _friends_to[_friends_i] = Integer.parseInt(_friends_fromv[1]);
        }

        res = bestTrio(_friends_nodes, _friends_from, _friends_to);
        bw.write(String.valueOf(res));
        bw.newLine();

        bw.close();
    }

    /*testcase :
    5 6
    1 2
    1 3
    2 3
    2 4
    3 4
    5 4
    Ans:2
    */
    static int bestTrio(int friends_nodes, int[] friends_from, int[] friends_to) {

        return 0;
    }

}
