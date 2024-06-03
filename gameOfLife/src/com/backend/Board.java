package com.backend;

public class Board {
	Cell[][] mainBoard;
	
	public Board(int width, int height) {
		this.mainBoard = new Cell[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				mainBoard[i][j] = new Cell(false);
			}
		}
		/*This next section is only for demo. The main program
		 * will use JButtons
		mainBoard[startX][startY] = new Cell(true);
		int up = startX+1;
		int down = startX-1;
		int left = startY-1;
		int right = startY+1;
		int[] input1 = {up,down};
		int[] input2 = {left,right};
		for (int input: input1) {
			try {
				mainBoard[input][startY] = new Cell(true);
			} catch (IndexOutOfBoundsException e) {}
		}
		for (int input: input2) {
			try {
				mainBoard[startX][input] = new Cell(true);
			} catch (IndexOutOfBoundsException e) {}
		}
		 */
		// end demo section
	}
	
	public synchronized void updateBoard() {
		for (int i = 0; i < mainBoard.length; i++) {
			for (int j = 0; j < mainBoard[i].length; j++) {
				mainBoard[i][j].updateNeighbors(mainBoard, i, j);
			}
		}
		for (int i = 0; i < mainBoard.length; i++) {
			for (int j = 0; j < mainBoard[i].length; j++) {
				mainBoard[i][j].tick();
			}
		}
	}
	public synchronized boolean setBoard(int[][] newBoard) {
		if (newBoard.length != mainBoard.length || newBoard[0].length != mainBoard[0].length) {
			return false;
		}
		try {
			for(int i = 0; i < newBoard.length; i++) {
				for (int j = 0; j < newBoard[i].length; j++) {
					if (newBoard[i][j] == 0) {
						mainBoard[i][j].setStatus(false);
					} else {
						mainBoard[i][j].setStatus(true);
					}
				}
			}
		} catch(Exception e)  {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public synchronized Cell[][] getBoard() {
		return mainBoard;
	}
	
	public synchronized void printBoard() {
		for (int i = 0; i <mainBoard.length;i++) {
			System.out.println("");
			for (int j = 0; j < mainBoard[i].length; j++) {
				System.out.printf("%d ", mainBoard[i][j].getStatus()? 1:0);
			}
		}
	}
}
