package com.backend;

public class Cell {
	private boolean status;
	private int[] neighbors;
	
	public Cell(boolean status) {
		this.status = status;
		neighbors = new int[9];
	}
	
	public synchronized boolean getStatus() {
		return status;
	}
	public synchronized void setStatus(boolean status) {
		this.status = status;
	}
	
	public synchronized int[] getNeighbors() {
		return neighbors;
	}
	public synchronized void setNeigbors(int[] neighbors) {
		this.neighbors = neighbors.clone();
	}
	
	public synchronized boolean tick() {
        int liveNeighbors = 0;
        for (int neighbor : neighbors) {
            if (neighbor == 1) {
                liveNeighbors++;
            }
        }

        if (status) {
            if (liveNeighbors < 2 || liveNeighbors > 3) {
                status = false;
            }
        } else {
            if (liveNeighbors == 3) {
                status = true;
            }
        }
        return status;
    }
	
	   public synchronized void updateNeighbors(Cell[][] board, int x, int y) {
	        int[][] positions = {
	            {-1, 1}, {0, 1}, {1, 1},  // above row
	            {-1, 0},         {1, 0},  // same row
	            {-1, -1}, {0, -1}, {1, -1} // below row
	        };

	        for (int i = 0; i < positions.length; i++) {
	            int nx = x + positions[i][0];
	            int ny = y + positions[i][1];
	            if (nx >= 0 && nx < board.length && ny >= 0 && ny < board[0].length) {
	                neighbors[i] = board[nx][ny].getStatus() ? 1 : 0;
	            } else {
	                neighbors[i] = 0; // out of bounds cells are considered dead
	            }
	        }
	    }
	}
