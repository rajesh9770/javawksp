package com.hackerrank;

import java.util.Scanner;

//DFS Connected Cell in a Grid. Find number of regions in the biggest Component
//Another problem: find number of connected regions.
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
        //System.out.println(getBiggestRegion(grid, n, m));
        System.out.println(getBiggestConnectedComponent(grid, n, m));

	}

	private static long getBiggestConnectedComponent(int[][] grid, int n, int m) {
		long maxRegion= Long.MIN_VALUE;
		boolean [][] explored = new boolean[n][m];
		for(int i=0; i<n; ++i){
			for(int j=0; j<m; ++j){
				explored[i][j] = false;
			}
		}
		for(int i=0; i<n; ++i){
			for(int j=0; j<m; ++j){
				if(!explored[i][j] && grid[i][j] == 1) {
					long ct = DFS(grid, explored, i, j);
					if(maxRegion < ct) maxRegion = ct;
				}
			}
		}
		return maxRegion;
	}

	private static long DFS(int[][] grid, boolean [][] explored, int i, int j) {
		explored[i][j] = true;
		int n = grid.length;
		int m = grid[0].length;
		long componentSize = 1;
	
		if(i-1>=0 && j-1>=0 && !explored[i-1][j-1] && grid[i-1][j-1]==1)
			componentSize +=DFS(grid, explored, i-1, j-1);
		if(i-1>=0 && !explored[i-1][j] && grid[i-1][j]==1)
			componentSize +=DFS(grid, explored, i-1, j);
		if(i-1>=0 && j+1 <m && !explored[i-1][j+1]  && grid[i-1][j+1]==1)
			componentSize +=DFS(grid, explored, i-1, j+1);
		if(j-1>=0 && !explored[i][j-1] && grid[i][j-1]==1)
			componentSize +=DFS(grid, explored, i, j-1);		
		if(j+1 <m && !explored[i][j+1] && grid[i][j+1]==1)
			componentSize +=DFS(grid, explored, i, j+1);
		if(i+1<n && j-1>=0 && !explored[i+1][j-1] && grid[i+1][j-1]==1)
			componentSize +=DFS(grid, explored, i+1, j-1);
		if(i+1<n && !explored[i+1][j] && grid[i+1][j]==1)
			componentSize +=DFS(grid, explored, i+1, j);
		if(i+1<n && j+1 <m && !explored[i+1][j+1] && grid[i+1][j+1]==1)
			componentSize +=DFS(grid, explored, i+1, j+1);
		
		return componentSize;
		
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

	/**
	 * Finds the number of connected components in the grid.
	 * @param grid
	 * @param n
	 * @param m
	 * @return
	 */
	private static long findNumberOfConnectedComps(int [][] grid, int n, int m){
		long numOfConnectedComps = 0;
		for(int i=0; i<n; ++i){
			for(int j=0; j<m; ++j){
				if(grid[i][j] ==1){
					++numOfConnectedComps;
					mark(grid, n, m, i, j);
				}
			}
		}
		return numOfConnectedComps;
	}

	/**
	 * Marks the island that starts at (i,j)-th position as done.
	 * @param grid
	 * @param n
	 * @param m
	 * @param i
	 * @param j
	 */
	private static void mark(int[][] grid, int n, int m, int i, int j) {
		if(i>=0 && i<n && j>=0 && j<m && grid[i][j] ==1){
			grid[i][j] = 0;
			mark(grid, n, m, i, j+1);
			mark(grid, n, m, i, j-1);
			mark(grid, n, m, i+1, j);
			mark(grid, n, m, i-1, j);

			mark(grid, n, m, i-1, j+1);
			mark(grid, n, m, i-1, j-1);
			mark(grid, n, m, i+1, j+1);
			mark(grid, n, m, i+1, j-1);
		}
	}

}
