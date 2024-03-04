package com.metrics;

/**
 * https://leetcode.com/problems/rotate-image/
 * You are given an n x n 2D matrix representing an image, rotate the image by 90 degrees (clockwise).
 *
 * You have to rotate the image in-place, which means you have to modify the input 2D matrix directly.
 * DO NOT allocate another 2D matrix and do the rotation.
 *   1Col      1Row        lastcol         lastRow     1col
 * (i,0) -> (0, n-1-i) -> (n-1-i, n-1) -> (n-1, i) -> (i, 0)
 *
 * You flip (i, j) to (j, i) and then if landing in top or bottom row change the column index to n-1 - (column index)
 * (i,j) - >  (j, i) - > (j, n-1-i)
 *
 * If land on first or last column change the col index to n-1-(col index)
 *
 * (i, j) -> (j, i) -> (j, n-1-i)
 *
 * So in either case formula is (i, j) -> (j, i) -> (j, n-1-i)
 * First is the
 * (i, j) -> (j, i)
 * transposition and
 * the second is
 * (j, i) -> (j, n-1-i)
 * horizontal reflection about vertical line x = n/2
 *
 * Note: 180 degree rotation would be another 90 degree from here
 * (i, j) -> (j, i) -> (j, n-1-i) -> (n-1-i, j) -> (n-1-i, n-1-j)
 *
 * 270 degree rotation:  This is anticlockwise.
 * (i, j) -> (n-1-j, i)
 *
 * 360 degree:
 * (i,j) -> (i, j)
 *
 * */
public class RotateMetrics {
    public static void rotate(int[][] matrix) {
        transpose(matrix, matrix.length);
//        print(matrix);
        reflectHorizontally(matrix, matrix.length);
//        print(matrix);
    }

    public static void transpose(int [][] matrix, int n){
        for(int i=0; i< n; ++i){
            for(int j=i+1; j<n; ++j){
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
    }

    public static void reflectHorizontally(int [][] matrix, int n){
        for(int j=0; j< n/2; ++j){
            for(int i=0; i<n; ++i){
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[i][n-1-j];
                matrix[i][n-1-j] = tmp;
            }
        }
    }

    public static void antiClockwiseRotation(int [][] matrics){
        //first reflect then transpose
        reflectHorizontally(matrics, matrics.length);
        transpose(matrics, matrics.length);
    }

    public static void print(int[] [] matrix){
        for (int[] rows : matrix) {
            for (int row : rows) {
                System.out.print( row + " ");
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        int n = 100;

        //int [][] a = new int[][] {{1,2,3},{4,5,6},{7,8,9}};
        //int [][] b = new int[][] {{1,2,3},{4,5,6},{7,8,9}};
        int [][] a = new int[n][n];
        int [][] b = new int[n][n];
        for(int i=0; i<a.length; ++i) {
            for (int j = 0; j < b.length; ++j) {
                a[i][j] = b[i][j] = (int) (Math.random() * 10 * n);
            }
        }

        rotate(a);
        rotate(a);
        rotate(a);

        antiClockwiseRotation(b);
        System.out.println(equals(a, b));
    }

    public static boolean equals(int [][] a , int [][] b){
        for(int i=0; i<a.length; ++i){
            for(int j=0; j<b.length; ++j){
                if(a[i][j] != b[i][j]) return false;
            }
        }
        return true;
    }
}
