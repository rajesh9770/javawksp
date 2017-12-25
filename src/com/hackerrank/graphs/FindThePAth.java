package com.hackerrank.graphs;


import com.hackerrank.utils.InputReader;
import com.hackerrank.utils.PriorityQueue;
import javafx.util.Pair;

import java.io.*;
import java.util.*;


/**
 * Created by Rajesh on 12/18/2017.
 */
public class FindThePAth {
    static int n, m;
    static int [][] weights;
    static Set<Integer> visited = new HashSet<>();
    static int [] neighRow = {0, 0, -1, 1};
    static int [] neighCol = {-1, 1, 0, 0};

    static long [][][][] d ;

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        long dTime = 0;
        InputReader in = new InputReader(new FileInputStream("FileName"));
        n = in.nextInt();
        m = in.nextInt();

        weights= new int[n][m];
        d = new long[m][][][];

        for(int i=0; i<n; ++i){
            for(int j=0; j<m; ++j){
                weights[i][j] = in.nextInt();
            }
        }

        int q = in.nextInt();
        StringBuilder ans = new StringBuilder();
        while(q-->0){
            int sr = in.nextInt(), sc = in.nextInt();
            int tr = in.nextInt(), tc = in.nextInt();
            //System.out.println( sr + " " + sc + " " + tr + " " + tc + " " );
            if(sc > tc){
                //swap
                int tmp = sr;  sr = tr; tr = tmp;
                tmp = sc;  sc = tc; tc = tmp;
            }
            int L=0;
            int R=m-1;
            long minWeight = Long.MAX_VALUE;
            while (true) {
                //int half = ((R-L+1) >> 1) + L;
                if(L>R) break;
                int half = (R+L)>>>1;
                if (d[half] == null) {
                    //System.out.println("creating "  + half + " [" + L + " " + R + "]");
                    d[half] = new long[n][][];
                    long t = System.currentTimeMillis();
                    for (int i = 0; i < n; ++i) {
                        d[half][i] = dijk(weights, i, half, L, R);
                    }
                    dTime += (System.currentTimeMillis() - t);
                }
                for (int i = 0; i < n; ++i) {
                    long[][] goThru_i = d[half][i];
                    //System.out.println(goThru_i[sr][sc-L] + goThru_i[tr][tc-L]);
                    minWeight = Math.min(minWeight, goThru_i[sr][sc-L] + goThru_i[tr][tc-L]-weights[i][half]);
                }
                if(sc<=half && half<=tc) break;
                if(tc<half){
                    R=half-1;
                }else{
                    L=half+1;
                }
            }
            ans.append(minWeight);
            ans.append("\n");
            //System.out.println(minWeight);
        }
        System.out.println(ans);
        long totalTime = (System.currentTimeMillis() - startTime);
        System.out.println("Time " + totalTime + " "  + dTime + " " + (dTime*100/totalTime));
    }

    public static long [][] dijk(int [] []a, int sr, int sc, int L, int R){
        int rows = a.length;
        int cols = R-L+1;
        long[][] ret = new long[rows][cols];
        HashSet<Integer> visited = new HashSet<>(rows*cols);

        PriorityQueue<Long> pQ = new PriorityQueue<>(rows*cols);
        pQ.add(sr*cols + (sc-L), (long) a[sr][sc]);
        visited.add(sr*cols + (sc-L));

        Pair<Integer, Long> minNode = null;
        while( (minNode=pQ.delMinSafe()) != null){
            int minId = minNode.getKey();
            long minW = minNode.getValue();
            int minr = minId/cols ;
            int minc = minId%cols ; //wrt to new matrix

            ret[minr][minc] = minW;
            //System.out.println(ret[minr][minc]);
            for (int ni = 0; ni < 4; ni++) {
                int nrow = minr + neighRow[ni];
                int ncol = minc + neighCol[ni]; //wrt to new matrix
                int ind = nrow*cols + ncol;

                if (0 <= nrow && nrow < n && 0 <= ncol && ncol < cols) {
                    if (!visited.contains(ind)) {
                        visited.add(ind);
                        pQ.add( ind, minW + a[nrow][ncol+L]);
                    } else {
                        //it might still be in the queue
                        Pair<Integer, Long> neighborNode = pQ.containsSafe(ind);
                        if(neighborNode != null && neighborNode.getValue() > minW + a[nrow][ncol+L]){
                            pQ.decreaseVal (ind, minW + a[nrow][ncol+L]);
                        }
                    }
                }
            }
        }
//        System.err.println("Range: ["+ L + " " + R + "] start point: [" + sr + " " + sc + "]");
//        for(int i=0; i<rows; ++i){
//            for(int j=0; j<cols; ++j){
//                System.err.print(ret[i][j] + " ");
//            }
//            System.err.println();
//        }
        return ret;
    }
}

