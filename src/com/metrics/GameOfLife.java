package com.metrics;

/**
 * According to Wikipedia's article: "The Game of Life, also known simply as Life,
 * is a cellular automaton devised by the British mathematician John Horton Conway in 1970."
 *
 * The board is made up of an m x n grid of cells, where each cell has an initial state:
 * live (represented by a 1) or dead (represented by a 0).
 * Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four rules
 * (taken from the above Wikipedia article):
 *
 * Any live cell with fewer than two live neighbors dies as if caused by under-population.
 * Any live cell with two or three live neighbors lives on to the next generation.    -- set the second bit to 1
 * Any live cell with more than three live neighbors dies, as if by over-population.
 * Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.   -- set the second bit to 1
 * The next state is created by applying the above rules simultaneously to every cell in the current state,
 * where births and deaths occur simultaneously. Given the current state of the m x n grid board, return the next state.
 */
public class GameOfLife {
    public void gameOfLife(int[][] board) {
        int m = board.length, n = board[0].length;
        for (int i=0; i<m; ++i) {
            for (int j=0; j<n; ++j) {
                int count = -board[i][j];
                for (int I=Math.max(i-1, 0); I<Math.min(i+2, m); ++I){
                    for (int J=Math.max(j-1, 0); J<Math.min(j+2, n); ++J) {
                        count += board[I][J] & 1; //need to look at the last bit only, since we are changing the second bit.
                    }
                }
                if (count == 3 || (count == 2 && (board[i][j] == 1))) {// this condition can be written as if((count | board[i][j]) == 3)
                    //if count is 3, then it survives either way. If count is 2, it survives only if it is currently alive.
                    board[i][j] |= 2; //set the second bit
                }
            }
        }
        for (int i=0; i<m; ++i)
            for (int j=0; j<n; ++j)
                board[i][j] >>= 1;
    }

}
