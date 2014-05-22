package mlobanov;

import java.io.PrintStream;
import aiproj.fencemaster.*;

/**
 * 
 * @author Maxim Lobanov (mlobanov) & Rongduan Zhu (rz)
 *
 */

/* MARKING:
 * out of 22
 * 4 - quality of code and comments
 * 4 - correctness and readability of code
 * 		ALL SUBMITTED FILES MUST HAVE MAXIMUM LINE LENGTH OF 79 CHARACTERS
 * 7 - results of testing performance of game playing against other agents that uni provides
 * 7 - creativity of solution
 */

/* COMMENTS FILE:
 *  Describe approach taken in your solution in terms of:
 *  a) your search strategy
 *  b) your evaluation function
 *  c) any creative techniques you have applied such as how you optimised your search strategy or used machine learning
 */

/* STRATEGY:
 * 
 * Create a frame and then connect it to win while blocking opponent loop/high value positions
 * 
 * 
 * Evaluation Function?
 * 		Higher value to positions near stones of your own colour
 * 			Highest bonus for direct neighbour
 * 			Second highest for a stone that forms a virtual connection (can connect to it no matter what)
 * 			Third highest for stone that is 2 stones away from another one of your stones
 */
public class Mlobanov implements Player, Piece {
	private int colour;
	private Board gameBoard;
	private int moveCount = 0;
	private Move opponentLastMove;
	
	/* Constructor */
	public Mlobanov() {
		
	}
	
	/* Function called by referee to initialize the player.
	 *  Return 0 for successful initialization and -1 for failed one.
	 */
	public int init(int n, int p) {
		// initialise player colour
		setColour(p);
		// initialise the board
		int boardTotalCells=3*n*(n-1)+1;
		gameBoard = new Board(n);
		
		
		return 0;
	}
	
	/* Function called by referee to request a move by the player.
	 *  Return object of class Move
	 */
	public Move makeMove() {
		/* distance between two points (x1,y1) and (x2,y2) is:
		 * (abs(x1-x2) + abs(y1-y2) + abs( (x1-y1) - (x2-y2))) / 2
		 */

		int moveRow, moveCol, maxValueRow, maxValueCol, maxValue;
		maxValueRow = maxValueCol = maxValue = -1;
		
		// determine if best move is to swap
		if (makeSwap()) {
			moveRow = opponentLastMove.Row;
			moveCol = opponentLastMove.Col;
		} else {		
			// find best position on board to make move
			
			Cell oneCell;
			for (int i = 0; i < gameBoard.getNumRows(); i++) {
				for (int j = 0; j < gameBoard.getNumRows(); j++) {
					oneCell = gameBoard.getCell(i, j);
					// check if the cell is a valid position and not taken
					/*if (oneCell.isValid() && oneCell.notTaken()) {
						if (oneCell.getValue() > maxValue) {
							maxValue = oneCell.getValue();
							maxValueRow = oneCell.getRow();
							maxValueCol = oneCell.getCol();
				  		}
					}*/	
				}
			}		  
			moveRow = maxValueRow;
			moveCol = maxValueCol;
		}
		
		Move newMove = new Move(colour, false, moveRow, moveCol);
		 
		setMoveCount(getMoveCount() + 1);
		return newMove;
	}
	
	public boolean makeSwap() {
		// check if a swap is possible
		if (getMoveCount() == 1) {
			// swap if opponent's move is on a position that has good value
			int threshold = 5;
			if (gameBoard.getCell(opponentLastMove.Row, 
					opponentLastMove.Col).getValue() > threshold) {

				return true;
			}
		}
		return false;
	}
	
	/* Function called by referee to inform the player about the opponent's move
	 *  Return -1 if the move is illegal otherwise return 0
	 */
	public int opponentMove(Move m) {
		setMoveCount(getMoveCount() + 1);
		// Check if the move is illegal
		
		// Can only swap on second move in the game
		if (getMoveCount() != 2 && m.IsSwap) {
			return -1;
		}
		
		// Can't place a piece on top of another piece or invalid position
		String cellContent = gameBoard.getCell(m.Row, m.Col).getContent(); 
		if (!cellContent.equals(Cell.EMPTY)) {
			return -1;
		}
		
		// update instance variable of opponent's last move
		setOpponentLastMove(m);
		return 0;
	}
	
	/* This function when called by referee should return the winner
	 *  
	 */
	public int getWinner() {
		// Kevin please do this
		// gameBoard.searchForTripod()
		// gameBoard.searchForLoop()
		
		// -1 = INVALID/Non-Terminal State, 0 = EMPTY/DRAW, 1 = WHITE, 2 = BLACK
		return 1;
	}
	
	/* Function called by referee to get the board configuration in String format
	 * from player 
	 */
	public void printBoard(PrintStream output) {
		
	}

	public int getColour() {
		return colour;
	}

	public void setColour(int colour) {
		this.colour = colour;
	}

	public int getMoveCount() {
		return moveCount;
	}

	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}

	public Move getOpponentLastMove() {
		return opponentLastMove;
	}

	public void setOpponentLastMove(Move opponentLastMove) {
		this.opponentLastMove = opponentLastMove;
	}
}