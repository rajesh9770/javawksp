package com.hackerrank;

import java.util.Scanner;

class ConnectedRegion {

	
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int grid[][] = new int[n][m];
        for(int grid_i=0; grid_i < n; grid_i++){
            for(int grid_j=0; grid_j < m; grid_j++){
                grid[grid_i][grid_j] = in.nextInt();
            }
        }
        System.out.println(getBiggestRegion(grid, n, m));

	}

	private static long countCells(int[][] grid, int i, int j){
		if(i<0 || j<0 || i>=grid.length || j >=grid[0].length) return 0;
		if(grid[i][j] == 0 ) return 0;
		long count = 1;
		grid[i][j] = 0;
		
		count += countCells(grid, i, j+1);
		count += countCells(grid, i, j-1);
		count += countCells(grid, i+1, j);
		count += countCells(grid, i+1, j-1);
		count += countCells(grid, i+1, j+1);
		count += countCells(grid, i-1, j);
		count += countCells(grid, i-1, j-1);
		count += countCells(grid, i-1, j+1);
		return count;
	}
	
	private static long getBiggestRegion(int[][] grid, int n, int m) {
		long maxRegion= Long.MIN_VALUE; 
		for(int i=0; i<n; ++i){
			for(int j=0; j<n; ++j){
				long ct = countCells(grid, i, j);
				if(maxRegion < ct) maxRegion = ct;
			}
		}
		return maxRegion;
	}
	/*
	 * 4 4 1 1 0 0 0 1 1 0 0 0 1 0 1 0 0 0  Ans: 5
	 * 7 5 1 1 1 0 1 0 0 1 0 0 1 1 0 1 0  0 1 1 0 0 0 0 0 0 0 0 1 0 0 0 0 0 1 1 0 Ans 9 
	 * 	 */
	

}
