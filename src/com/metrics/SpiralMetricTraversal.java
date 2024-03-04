package com.metrics;

import java.util.ArrayList;
import java.util.List;

public class SpiralMetricTraversal {
    /**
     * https://leetcode.com/problems/spiral-matrix/
     * Given an m x n matrix, return all elements of the matrix in spiral order.
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        boolean right = true, left=false;//row direction
        boolean down = false, up=false;//column direction
        int leftMostCol = 0, rightMostCol = matrix[0].length-1;
        int bottomRow=matrix.length-1, topRow = 0;

        List<Integer> ans = new ArrayList<>();
        int i=0, j=0;
        while(topRow <=i && i<=bottomRow && leftMostCol<=j && j<=rightMostCol){
            ans.add(matrix[i][j]);
            if(right){
                if(j<rightMostCol){
                    j++;
                }else{//change the direction, start going down
                    i++; //next row
                    //we covered the i-th row from top
                    topRow++;
                    right = false;
                    down = true;
                }
            }else if(down){
                if(i<bottomRow){
                    ++i;
                }else{//change direction, start going left
                    j--; //prev col
                    //we covered the j-th column from right
                    rightMostCol--;
                    down = false;
                    left = true;
                }
            }else  if(left){
                if(j>leftMostCol){
                    j--;
                }else{//change direction, start going up
                    i--;//prev row
                    //we covered the i-th row from bottom
                    bottomRow--;
                    left = false;
                    up = true;
                }
            } else if(up){
                if(i>topRow){
                    i--;
                }else{//change direction, start going right
                    j++;
                    //we covered the j-th column from left
                    leftMostCol++;
                    up=false;
                    right = true;
                }
            }

        }
        return ans;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][] {{1,2,3},{4,5,6},{7,8,9}};
        System.out.println(new SpiralMetricTraversal().spiralOrder(matrix));
    }

    public List<Integer> spiralOrder2(int[][] matrix){
        List<Integer> res = new ArrayList<Integer>();

        if (matrix.length == 0) {
            return res;
        }

        int rowBegin = 0;
        int rowEnd = matrix.length-1;
        int colBegin = 0;
        int colEnd = matrix[0].length - 1;

        while (rowBegin <= rowEnd && colBegin <= colEnd) {
            // Traverse Right
            for (int j = colBegin; j <= colEnd; j ++) {
                res.add(matrix[rowBegin][j]);
            }
            rowBegin++;

            // Traverse Down
            for (int j = rowBegin; j <= rowEnd; j ++) {
                res.add(matrix[j][colEnd]);
            }
            colEnd--;

            if (rowBegin <= rowEnd) {
                // Traverse Left
                for (int j = colEnd; j >= colBegin; j --) {
                    res.add(matrix[rowEnd][j]);
                }
            }
            rowEnd--;

            if (colBegin <= colEnd) {
                // Traver Up
                for (int i = rowEnd; i >= rowBegin; i --) {
                    res.add(matrix[i][colBegin]);
                }
            }
            colBegin ++;
        }

        return res;
    }
}
