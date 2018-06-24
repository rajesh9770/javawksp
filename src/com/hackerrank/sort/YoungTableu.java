package com.hackerrank.sort;

import java.util.*;

public class YoungTableu {

    public static Random rand = new Random();

    public static long [][] generateSortedYTableu(int rows, int cols){
        long ret [][] = new long [rows][cols];
        for(int i=0; i<rows; ++i){
            //generate sorted rows
            long [] row = new long [cols];
            for(int j=0; j<cols; ++j){
                row[j]=rand.nextLong();
            }
            //sort rows
            Arrays.sort(row);
            for(int j=0; j<cols; ++j){
                ret[i][j]=row[j];
            }
        }

        //sort colums
        for(int j=0; j<cols; ++j){
            long [] col = new long [rows];
            for(int i=0; i<rows; ++i) col[i] = ret[i][j];
            Arrays.sort(col);
            for(int i=0; i<rows; ++i) ret[i][j] = col[i];
        }
        return ret;
    }

    public static int [][] generateSortedYTableu(int rows, int cols, int bound){
        int ret [][] = new int [rows][cols];
        for(int i=0; i<rows; ++i){
            //generate sorted rows
            int [] row = new int [cols];
            for(int j=0; j<cols; ++j){
                row[j]=rand.nextInt(bound);
            }
            //sort rows
            Arrays.sort(row);
            for(int j=0; j<cols; ++j){
                ret[i][j]=row[j];
            }
        }

        //sort colums
        for(int j=0; j<cols; ++j){
            int [] col = new int [rows];
            for(int i=0; i<rows; ++i) col[i] = ret[i][j];
            Arrays.sort(col);
            for(int i=0; i<rows; ++i) ret[i][j] = col[i];
        }
        return ret;
    }

    /**
     * O(n^2 * (n+n)) = O(n^3) on input of size n^2.
     * On size of input n, it's O(n^1.5)
     * @param yTableu
     * @return
     */
    private static int [] sort(int [][] yTableu){
        int [] sorted = new int[yTableu.length * yTableu[0].length];
        for(int i=0; i<yTableu.length * yTableu[0].length; ++i){
            sorted[i] = extractMin(yTableu);
        }
        return sorted;
    }

    /**
     *
     */
    private static class Location implements Comparable<Location>{
        @Override
        public int compareTo(Location location) {
            return this.val - location.val;
        }

        public int val, row, col;
        public Location(int v, int r, int c){
            this.val = v;
            this.row = r;
            this.col = c;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(val);
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Location && ((Location) o).col == col && ((Location) o).row == row;
        }
    }

    private static int [] sort2(int [][] yTableu){
        int [] sorted = new int[yTableu.length * yTableu[0].length];
        PriorityQueue<Location> queue = new PriorityQueue<>();
        boolean [][] visited = new boolean[yTableu.length][yTableu[0].length];
        for(int i=0; i<yTableu.length; ++i){
            for(int j=0; j<yTableu[0].length; ++j)
                visited[i][j] = false;
        }
        queue.add(new Location(yTableu[0][0], 0, 0));
        visited[0][0] = true;

        int idx = 0;
        while(!queue.isEmpty()){
            Location max = queue.remove();
            sorted[idx++] = max.val;
            if(isValidRow(yTableu,max.row+1) && !visited[max.row+1][max.col]) {
                queue.add(new Location(yTableu[max.row + 1][max.col], max.row + 1, max.col));
                visited[max.row+1][max.col] = true;
            }
            if(isValidColumn(yTableu,max.col+1) && !visited[max.row][max.col+1]) {
                queue.add(new Location(yTableu[max.row][max.col + 1], max.row, max.col + 1));
                visited[max.row][max.col+1] = true;
            }
        }
        return sorted;
    }


    private static boolean isValidColumn(int [][] yT, int c){
        return c>=0 && c<yT[0].length;
    }

    private static boolean isValidRow(int [][] yT, int r){
        return r>=0 && r<yT.length;
    }

    /**
     * Y [i, j] = ∞ but Y [i + 1..m, j..n] is a Young tableau and Y [i..m, j + 1..n] is a Yong tableau.
     * We recursively exchange Y [i, j] and min(Y [i + 1, j], Y [j + 1, j]) until we obtain a Young tableau.
     * O(n+n)
     * @param yT
     * @param r
     * @param c
     */
    private static void youngify(int [][] yT, int r, int c){
        //pick the smallest from adjacent lower row or right col
        int smallRow = r, smallCol = c;
        if(isValidColumn(yT, c+1) && yT[r][c+1] < yT[smallRow][smallCol]){
                smallCol = c+1;
                smallRow = r;
        }
        if(isValidRow(yT, r+1) && yT[r+1][c] < yT[smallRow][smallCol]){
            smallCol = c;
            smallRow = r+1;
        }
        if(smallRow != r || smallCol != c){
            int tmp = yT[r][c];
            yT[r][c] = yT[smallRow][smallCol];
            yT[smallRow][smallCol] = tmp;
            youngify(yT, smallRow, smallCol);
        }
    }

    /**
     * Since the minimum element is always in Y [0, 0], EXTRACT-MIN can return the element
     * stored in Y [0, 0] and set Y [0, 0] = ∞ to indicate that the element does not exist anymore. However,
     * this might put the Young tableau in an inconsistent state, namely, Y [0, 0] = ∞ and Y is not empty.
     * O(n+n)
     * @param yT
     */
    private static int extractMin(int [][] yT){
        int min = yT[0][0];

        yT[0][0] = Integer.MAX_VALUE;
        youngify(yT, 0, 0);
        return min;
    }

    private static int extractMax(int [][] yT){
        int maxRow = yT.length-1;
        int maxCol = yT[0].length-1;
        int min = yT[maxRow][maxCol];

        yT[maxRow][maxCol] = Integer.MIN_VALUE;
        youngifyUpward(yT, maxRow, maxCol);
        return min;
    }

    private static void youngifyUpward(int[][] yT, int r, int c) {
        int biggestRow = r;
        int biggestCol = c;

        if(isValidRow(yT,r-1) && yT[r-1][c] > yT[biggestRow][biggestCol] ){
            biggestRow = r-1;
            biggestCol = c;
        }
        if(isValidColumn(yT,c-1) && yT[r][c-1] > yT[biggestRow][biggestCol] ){
            biggestRow = r;
            biggestCol = c-1;
        }
        if(biggestRow != r || biggestCol != c){
            int tmp = yT[r][c];
            yT[r][c] = yT[biggestRow][biggestCol];
            yT[biggestRow][biggestCol] = tmp;
            youngifyUpward(yT, biggestRow, biggestCol);
        }
    }

    private static void printYT(int [][] yTableu){
        System.out.println(" Young Tableu ");
        for(int i=0; i<yTableu.length; ++i){
            for(int j=0; j<yTableu[0].length; ++j){
                if(yTableu[i][j] == Integer.MAX_VALUE)
                    System.out.print(" inf ");
                else if(yTableu[i][j] == Integer.MIN_VALUE)
                    System.out.print("-inf ");
                else
                    System.out.print(yTableu[i][j] + " ");
            }
            System.out.println(" ");
        }
    }

    public static void main(String[] args) {
//        Set<Location> set = new HashSet<>();
//        set.add(new Location(19, 1, 2));
//        set.add(new Location(19, 1, 3));
//
//        System.out.println(set.contains(new Location(19, 1, 2)));
//        System.out.println(set.contains(new Location(19, 1, 3)));
        performance();
//        int[][] yTableu = generateSortedYTableu(3, 3, 100);
//        //long[][] yTableu = generateSortedYTableu(3,3);
//        printYT(yTableu);
//
//        //extract top 3 elements
//        System.out.println("Sorted:");
//        for(int i=0; i<3; ++i){
//            int max = extractMax(yTableu);
//            System.out.print(max + " ");
//        }
//        System.out.println("");
//
//        printYT(yTableu);
//
//        yTableu = generateSortedYTableu(3, 3, 100);
//        int[] sorted = sort(yTableu);
//        for(int s: sorted){
//            System.out.print(s + " ");
//        }

//        int [][]  yTableu = generateSortedYTableu(5, 5, 100);
//        printYT(yTableu);
//        int [] sorted = sort2(yTableu);
//        for(int s: sorted){
//            System.out.print(s + " ");
//        }
    }

    private static void performance(){
        int[][] yTableu = generateSortedYTableu(1000, 1000, 10000);
        long timeS = System.currentTimeMillis();
        int[] sorted = sort(yTableu);
        System.out.println(System.currentTimeMillis()-timeS);

        yTableu = generateSortedYTableu(1000, 1000, 10000);
        int [] unsorted = new int [yTableu.length * yTableu[0].length];
        int idx=0;
        for(int i=0; i<yTableu.length; ++i) {
            for(int j=0; j<yTableu[0].length; ++j){
                unsorted[idx++] = yTableu[i][j];
            }
        }
        timeS = System.currentTimeMillis();
        sorted = sort2(yTableu);
        System.out.println(System.currentTimeMillis()-timeS);

        timeS = System.currentTimeMillis();
        Arrays.sort(unsorted);
        System.out.println(System.currentTimeMillis()-timeS);

        System.out.println(Arrays.equals(sorted, unsorted));
    }
}
