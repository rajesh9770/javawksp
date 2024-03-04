package com.metrics;

import java.util.HashSet;
import java.util.Set;

public class ValidSudoku {
    /**
     * Determine if a 9 x 9 Sudoku board is valid. Only the filled cells need to be validated according to the following rules:
     *
     * Each row must contain the digits 1-9 without repetition.
     * Each column must contain the digits 1-9 without repetition.
     * Each of the nine 3 x 3 sub-boxes of the grid must contain the digits 1-9 without repetition.
     */
    public boolean isValidSudoku(char[][] board) {
        Set<String> seen = new HashSet<>();
        for(int i=0; i<board.length; ++i) {
            for (int j = 0; j < board[0].length; ++j) {
                char c = board[i][j];
                if(c != '.'){
                    if(!seen.add("row--"+ i + "--" + c)) return false;
                    if(!seen.add("col--"+ j + "--" + c)) return false;
                    if(!seen.add((i/3) + "--" +  (j/3) + "--" + c)) return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        char [][] board = new char[][] {{'5','3','.','.','7','.','.','.','.'},{'6','.','.','1','9','5','.','.','.'},{'.','9','8','.','.','.','.','6','.'},{'8','.','.','.','6','.','.','.','3'},{'4','.','.','8','.','3','.','.','1'},{'7','.','.','.','2','.','.','.','6'},{'.','6','.','.','.','.','2','8','.'},{'.','.','.','4','1','9','.','.','5'},{'.','.','.','.','8','.','.','7','9'}};
        System.out.println(new ValidSudoku().isValidSudoku(board));
    }
}
