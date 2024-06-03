package com.main;
import javax.swing.SwingUtilities;
import com.frontend.MainFrame;

/*Conway's Game of Life
 * 
 * @author: Paul Siebenaler
 * @date: 05/30/24
 * @version: 1.1
 * 
 * 
 * @note:
 * This program is designed to recreate the famous game of life simulation.
 * 
 * Each cell abides by these 4 rules*
 * 		1. Any live cell with fewer than two live neighbors dies, as if by underpopulation.
 * 		2. Any live cell with two or three live neighbors lives on to the next generation.
 * 		3. Any live cell with more than three live neighbors dies, as if by overpopulation.
 * 		4. Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 *
 * *https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
 * 
 * 		
 * @note 05/30
 * Current 1.0 version is live and active
 * 
 * @note 06/03
 * In MainFrame i added a global BOARD_SIZE so you can quickly alter the the size.
 */
public class Main {
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                new MainFrame();
	            }
	        });
	    }
	}


