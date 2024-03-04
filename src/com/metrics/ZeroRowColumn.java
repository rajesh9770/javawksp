package com.metrics;


/**
 * Given an m x n integer matrix matrix, if an element is 0, set its entire row and column to 0's.
 *
 * You must do it in place.
 * Store the information about the column-j in the first a[0][j], except for col-0
 * Store the information about the row-i in the first a[i][0]
 */
public class ZeroRowColumn {
    public void setZeroes(int[][] matrix) {
        boolean firstColZero = false;

        for(int i=0; i<matrix.length; ++i){
            if(matrix[i][0] == 0){
                firstColZero = true;
            }
            for(int j=1; j<matrix[0].length; ++j){
                if(matrix[i][j] ==0){
                    matrix[i][0] = 0; //indicates that i-th row should be zeroed
                    matrix[0][j] = 0; //indicates that j-th col should be zeroed
                }
            }
        }

        for(int i=1; i<matrix.length; ++i) {
            for(int j=1; j<matrix[0].length; ++j){
                if(matrix[i][0] == 0 || matrix[0][j] ==0){
                    matrix[i][j] = 0;
                }
            }
        }
        if(matrix[0][0] == 0){//make the first row zero
            for(int j=0; j<matrix[0].length; ++j){
                matrix[0][j] = 0;
            }
        }
        if(firstColZero){
            for(int i=0; i<matrix.length; ++i){
                matrix[i][0] = 0;
            }
        }

    }

    public static void main(String[] args) {
        int [][] a = new int[][] {{1,2,3,4},{5,0,7,8},{0,10,11,12},{13,14,15,0}};
        new ZeroRowColumn().setZeroes(a);
        System.out.println("");
    }

}
