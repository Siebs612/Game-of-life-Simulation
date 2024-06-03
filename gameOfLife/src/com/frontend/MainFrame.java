package com.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.backend.Board;
import com.backend.Cell;
/*### Frontend
 * Currently live and all buttons work
 * 
 */
public class MainFrame {
	final int BOARD_SIZE = 30;
	private JFrame main;
	private Board mainBoard;
	JPanel board;
	JButton[][] buttons;
	boolean updateStatus;
	Color liveColor = new Color(34, 139, 34);
	
	public MainFrame() {
		this.updateStatus = false;
		this.mainBoard = new Board(BOARD_SIZE,BOARD_SIZE);
		// frame
		this.main = new JFrame("Game of Life Sim");
		main.setLayout(new FlowLayout());
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setLocationRelativeTo(null);
		main.setBounds(new Rectangle(780,880));
		// upper panel
		GridLayout layout = new GridLayout(BOARD_SIZE,BOARD_SIZE);
		buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
		this.board = new JPanel();
		//board.setPreferredSize(new Dimension(500,500));
		board.setLayout(layout);
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				JButton cell = new JButton();
				cell.setBackground(Color.LIGHT_GRAY);
				cell.setOpaque(true);
                cell.setBorderPainted(false);
                cell.setPreferredSize(new Dimension(25,25));
				cell.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
                        flipButton(cell);
					}
				});
				buttons[i][j] = cell;
				board.add(cell);
			}
		}
		// lower panel
		JPanel buttPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttPane.setPreferredSize(new Dimension(80, 40));
		JButton pause = new JButton("Pause");
		pause.setPreferredSize(new Dimension(100, 60));
		pause.addActionListener(new ActionListener() {
			@Override
			public synchronized void actionPerformed(ActionEvent e) {
				updateStatus = false;
				notifyAll();
				
			}
			
		});
		buttPane.add(pause);
		buttPane.setPreferredSize(new Dimension(500, 200));
		JButton start = new JButton("Start");
		start.setPreferredSize(new Dimension(100,60));
		start.addActionListener(new ActionListener() {
			@Override
			public synchronized void actionPerformed(ActionEvent e) {
				updateStatus = true;
				
				new Thread(() -> update(mainBoard)).start();
			}
			
		});
		buttPane.add(start);
		
		JButton reset = new JButton("Reset");
		reset.setPreferredSize(new Dimension(100,60));
		reset.addActionListener(new ActionListener() {
			@Override
			public synchronized void actionPerformed(ActionEvent e) {
				updateStatus = false;
				notifyAll();
				mainBoard.setBoard(new int[BOARD_SIZE][BOARD_SIZE]);
				for (JButton[] row: buttons) {
					for(JButton button: row) {
						button.setBackground(Color.LIGHT_GRAY);
					}
				}
				
				
			}
			
		});
		buttPane.add(reset);
		buttPane.setMaximumSize(new Dimension(500,120));
		main.add(board);
		main.add(buttPane);
		main.setVisible(true);
	}
	
	public void flipButton(JButton cell) {
		if (cell.getBackground() == Color.LIGHT_GRAY) {
            cell.setBackground(liveColor);
        } else {
            cell.setBackground(Color.LIGHT_GRAY);
        }
	}

	
	private void update(Board currBoard) {
		int[][] userInput = new int[buttons.length][buttons[0].length];
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				if (buttons[i][j].getBackground() == Color.LIGHT_GRAY) {
					userInput[i][j] = 0;
				} else {
					userInput[i][j] = 1;
				}
			}
		}
		currBoard.setBoard(userInput);
		try {
				while(updateStatus) {
					Thread.sleep(150);
					currBoard.updateBoard();
					Cell[][] cellBoard = currBoard.getBoard();
					for (int i =0; i < cellBoard.length; i++) {
						for (int j = 0; j < cellBoard[i].length; j++) {
							boolean cellStatus = cellBoard[i][j].getStatus();
							if (cellStatus) {
								buttons[i][j].setBackground(liveColor);
							} else {
								buttons[i][j].setBackground(Color.LIGHT_GRAY);
							}
						}
					}
				}
			} catch (InterruptedException e) {
				 Thread.currentThread().interrupt();
			}
		}
	}

