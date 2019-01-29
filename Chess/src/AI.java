import java.util.ArrayList;
import java.util.Arrays;

public class AI {
	/* NECESSARY IMPLEMENTATIONS:
	 * - automatic switch to queen in Board class
	 * - solution to moves arraylists having size of zero and causing out of bounds exception
	*/
	
	/* PROBLEMS:
	 * - getBestEnemyMove is recursive and never stops resulting in a stackoverflow error
	 * - if checkmate is achieved before final move prediction then things could get messy with predictions after *
	 * - AI doesn't remove king in check moves from list of possible moves but shouldn't be 
	 * 	a problem b/c it will always try to avoid having king in check
	 * - getHighest and getLowest could cause problems when arraylist size is 0
	*/
	
	/* THOTS:
	 * - simpler recursive method to replace getBestMove would be to use the moves stored in the 
	 * arrayList to transformBoard and append the best enemy move to it. This is possible since unlike 
	 * the Checkers AI there can only be one set of 4 digit moves per turn. 
	 * - new algorithm needed for predicting the human player's move if I want to avoid adding
	 * a large amount of if statements that will deteriorate the quality of the AI as well as the
	 * readability and understandability of the code
	 * - move combinations that end in checkmate(aka no more moves) should be removed and placed in
	 * separate list that can either: A) be added back to final list later(pretty good idea, i know)
	 * B) if resulting in player checkmate can either be added to separate list for manipulation
	 * post-algorithm or algorithm aborted and that first move in move combination played by AI 
	 * C) don't remove checkmates because all that 
	 * 
	 * 
	*/
	
	/* Desired Prediction rate: 6
	 * Actual : 4
	 * Desired Enemy prediction rate: 4/5
	 * Actual : 4
	*/
	
	private final int P_VALUE = 2;
	private final int R_VALUE = 20;
	private final int B_VALUE = 15;
	private final int K_VALUE = 15;
	private final int Q_VALUE = 40;
	private final int KING_VALUE = 10000;
	
	private static AI instance;
	
	public static AI getInstance() {
		if(instance == null) {
			instance = new AI();
		}
		
		return instance;
	}

	public void think() {

		Type[][] board = copy(Board.getInstance().getB());
		
		ArrayList<String> firstMoves = ValidMoves.getValidMoves(board, true);
		
		for(int i = 0; i < firstMoves.size(); i++) {
			firstMoves.set(i, firstMoves.get(i) + getBestEnemyMove(transformBoard(board, firstMoves.get(i))));
		}
		
		ArrayList<String> secondMoves = new ArrayList<String>();
		for(String move : firstMoves) {
			
			ArrayList<String> temp = ValidMoves.getValidMoves(transformBoard(board, move), true);
			for(String m : temp) {
				secondMoves.add(m + getBestEnemyMove(transformBoard(board, m)));
			}
		}
		
		//find piece with highest value
		ArrayList<Type[][]> finalBoards = new ArrayList<Type[][]>();
		for(String move : secondMoves) {
			finalBoards.add(transformBoard(board, move));
		}
		
		ArrayList<Integer> boardValues = new ArrayList<Integer>();
		for(Type[][] b : finalBoards) {
			boardValues.add(getBoardValue(b));
		}
		
		Board.getInstance().AIBoardChange(secondMoves.get(getLowest(boardValues)).substring(0, 4));
	
		//call boardChange
		
		
		
	}//end of think()
	
	private String getBestEnemyMove(Type[][] board) { //specilalized in returning best move of player two
		
		
		System.out.println("FIRST MOVES");
		ArrayList<String> firstMoves = ValidMoves.getValidMoves(board, false);
		for(int i = 0; i < firstMoves.size(); i++) {
			firstMoves.set(i, firstMoves.get(i) + getBestMove(transformBoard(board, firstMoves.get(i)), true, 5));//MAX: 5
		}
		
		System.out.println("SECOND MOVES");
		ArrayList<String> secondMoves = new ArrayList<String>();
		for(String move : firstMoves) {
			
			ArrayList<String> temp = ValidMoves.getValidMoves(transformBoard(board, move), false);
			for(String m : temp) {
				secondMoves.add(move + m + getBestMove(transformBoard(board, move + m), true, 5));
			}
		}
		
		ArrayList<Type[][]> finalBoards = new ArrayList<Type[][]>();
		for(String move : secondMoves) {
			finalBoards.add(transformBoard(board, move));
		}
		
		ArrayList<Integer> boardValues = new ArrayList<Integer>();
		for(Type[][] b : finalBoards) {
			boardValues.add(getBoardValue(b));
		}
		
		return secondMoves.get(getLowest(boardValues)).substring(0, 4);
	}
	
	//unspecialized class which returns the best move for any team
	private String getBestMove(Type[][] board, boolean team, int counter) {
		//theoretically...... this method is supposed to return the best move by recursively calling itself everytime 
		//but recursing less each time to avoid infinite recursion
		
		System.out.println(counter);
		counter--;
		
		ArrayList<String> firstMoves = ValidMoves.getValidMoves(board, team);
		if(counter == 1) {
			System.out.println("first counter");
			return getBest(firstMoves, board, team);
		}
		
		for(int i = 0; i < firstMoves.size(); i++) {
			firstMoves.set(i, firstMoves.get(i) + getBestMove(transformBoard(board, firstMoves.get(i)), !team, counter));
		}
		System.out.println("firstMoves");
		if(counter == 2) {
			System.out.println("second counter");
			return getBest(firstMoves, board, team);
		}
		
		System.out.println("secondMoves");
		ArrayList<String> secondMoves = new ArrayList<String>();
		for(String firstMove : firstMoves) {
			
			ArrayList<String> temp = ValidMoves.getValidMoves(transformBoard(board, firstMove), team);
			for(String nextMove : temp) {
				secondMoves.add(firstMove + nextMove + getBestMove(transformBoard(board, firstMove + nextMove), !team, counter));
			}
		}
		if(counter == 3) {
			System.out.println("third counter");
			return getBest(secondMoves, board, team);
		}
		
		for(int i = 0; i < secondMoves.size(); i++) {
			secondMoves.set(i, secondMoves.get(i) + getBestMove(transformBoard(board, secondMoves.get(i)), !team, counter));
		}
		
		System.out.println("fourth counter");
		return getBest(secondMoves, board, team);		
		
	}
	
	private String getBest(ArrayList<String> finalMoves, Type[][] board, boolean team) {
		
		if(finalMoves.size() == 0) {//if statement
			System.out.println("moves array is zero size");
			return "0000";
		}
		
		ArrayList<Type[][]> finalBoards = new ArrayList<Type[][]>();
		for(String move : finalMoves) {
			finalBoards.add(transformBoard(board, move));
		}
		
		ArrayList<Integer> boardValues = new ArrayList<Integer>();
		for(Type[][] b : finalBoards) {
			boardValues.add(getBoardValue(b));
		}
		
		if(team) {
			return finalMoves.get(getHighest(boardValues)).substring(0, 4);
		}else {
			return finalMoves.get(getLowest(boardValues)).substring(0, 4);
		}
	}
	
	private int getBoardValue(Type[][] b) {
	
		int value = 0;
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				
				Type piece = b[x][y];//optimization

				if (piece == null) {
					continue;
				}

				switch (piece) {

				case PAWN1:
					value += P_VALUE;
					break;
				case PAWN2:
					value -= P_VALUE;
					break;
				case ROOK1:
					value += R_VALUE;
					break;
				case ROOK2:
					value -= R_VALUE;
					break;
				case BISHOP1:
					value += B_VALUE;
					break;
				case BISHOP2:
					value -= B_VALUE;
					break;
				case KNIGHT1:
					value += K_VALUE;
					break;
				case KNIGHT2:
					value -= K_VALUE;					
					break;
				case QUEEN1:
					value += Q_VALUE;
					break;
				case QUEEN2:
					value -= Q_VALUE;
					break;
				case KING1:
					value += KING_VALUE;
					break;
				case KING2:
					value -= KING_VALUE;
					break;

				}

			} // end of inner for
		} // end of outer for		
		
		return value;
	}
	
	private int getHighest(ArrayList<Integer> a) {
		
		int max = a.get(0);
		ArrayList<Integer> arraySpot = new ArrayList<Integer>();
		arraySpot.add(0);
		
		for (int i = 0; i < a.size(); i++) {

			if(a.get(i) == max) {
				arraySpot.add(i);
			}

			if (a.get(i) > max) {
				max = a.get(i);
				arraySpot.clear();
				arraySpot.add(i);
			}
		} // end of for

		int index = (int) (Math.random() * arraySpot.size());

		return arraySpot.get(index);
	}// end of getHighest()
	
private int getLowest(ArrayList<Integer> a) {
		
		int min = a.get(0);
		ArrayList<Integer> arraySpot = new ArrayList<Integer>();
		arraySpot.add(0);
		
		for (int i = 0; i < a.size(); i++) {

			if(a.get(i) == min) {
				arraySpot.add(i);
			}

			if (a.get(i) < min) {
				min = a.get(i);
				arraySpot.clear();
				arraySpot.add(i);
			}
		} // end of for

		int index = (int) (Math.random() * arraySpot.size());

		return arraySpot.get(index);
	}// end of getHighest()
	
	private Type[][] transformBoard(Type[][] b, String move){
	
		for(int i = 0; i < move.length() - 1; i += 4) {
			
			b[Integer.parseInt(move.substring(i + 2, i + 3))][Integer.parseInt(move.substring(i + 3, i + 4))] = b[Integer.parseInt(move.substring(i, i + 1))][Integer.parseInt(move.substring(i + 1, i + 2))];
			b[Integer.parseInt(move.substring(i, i + 1))][Integer.parseInt(move.substring(i + 1, i + 2))] = null;
		
		}		
		
		
		for(int x = 0; x < 7; x++) {
			if(b[0][x] != null && b[0][x].equals(Type.PAWN2)){
				b[0][x] = Type.QUEEN2;
			}
			
			if(b[7][x] != null && b[7][x].equals(Type.PAWN1)) {
				b[0][x] = Type.QUEEN1;
			}
		}
		
		
		return b;
	}
	
	private Type[][] copy(Type[][] b) {
		
		Type[][] newB = new Type[8][];
		for (int x = 0; x < 8; x++) {
			newB[x] = Arrays.copyOf(b[x], b[x].length);
		}

		return newB;
	}

}
