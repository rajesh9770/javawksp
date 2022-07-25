package com.hackerrank.graphs;

//## 980 Unique Paths III

/**
 * On a 2-dimensional grid, there are 4 types of squares:
 *
 * 1 represents the starting square.  There is exactly one starting square.
 * 2 represents the ending square.  There is exactly one ending square.
 * 0 represents empty squares we can walk over.
 * -1 represents obstacles that we cannot walk over.
 * Return the number of 4-directional walks from the starting square to the ending square, that walk over every non-obstacle square exactly once.
 */
public class UniqPaths {

    public int uniquePathsIII(int[][] grid) {
        int src_x=0, src_y=0;
        int dest_x, dest_y;
        int emptycells = 0;

        for (int i=0; i<grid.length; ++i){
            for (int j=0; j<grid[0].length; ++j){
                if (grid[i][j] == 0) {
                    emptycells++;
                }else if (grid[i][j] == 1) {
                    src_x = i;
                    src_y = j;
                }else if (grid[i][j] == 2) {
                    dest_x = i;
                    dest_y = j;
                }
            }
        }
        int dfs = DFS(grid, src_x, src_y, emptycells);
        System.out.println(dfs);
        return dfs;

    }

    public int DFS(int[][]grid, int x, int y, int emptyCells){
        System.out.println(x + " " + y );
        if(grid[x][y] == 2){
            if(emptyCells==0) return 1;
            else return 0;
        }
        int origVal = grid[x][y];
        if (origVal == 0) {
            grid[x][y] = -2;
            --emptyCells;
        }
        int count = 0;

        if(y-1>=0 && (grid[x][y-1] == 0 || grid[x][y-1] == 2)) {
            count += DFS(grid, x, y - 1, emptyCells);
        }
        if(x-1>=0 && ((grid[x-1][y] == 0 || grid[x-1][y] == 2))){
            count += DFS(grid, x-1, y, emptyCells);
        }
        if(y+1<grid[0].length && ((grid[x][y+1] == 0 || grid[x][y+1] == 2))){
            count += DFS(grid, x, y + 1, emptyCells);
        }
        if(x+1<grid.length && ((grid[x+1][y] == 0 || grid[x+1][y] == 2))){
            count += DFS(grid, x+1, y, emptyCells);
        }
        //you need to put state back to the original state when this call was made,
        //so that other sibling recursive calls will see the original state - not the ones that are modified by this calls.
        if (origVal == 0) grid[x][y] = 0;

        return count;
    }

    public static void main(String[] args) {
        int [][]grid = new int[][]{{1,0,0,0},{0,0,0,0},{0,0,2,-1}};

        new UniqPaths().uniquePathsIII(grid);

    }
}
