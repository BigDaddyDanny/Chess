import java.util.ArrayList;
import java.util.Arrays;

public class AI {
	
	/* PROBLEMS:
	 * - if checkmate is achieved before final move prediction then things could get messy with predictions after
	 * - AI doesn't remove king in check moves from list of possible moves but shouldn't be 
	 * 	a problem b/c it will always try to avoid having king in check
	 * - moves that end in checkmate?? no valid moves from ValidMoves will be available
	*/
	
	private final int P_VALUE = 2;
	private final int R_VALUE = 20;
	private final int B_VALUE = 15;
	private final int K_VALUE = 10;
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
				
		Board.getInstance().AIBoardChange(getBestMove(board, 3, true));		
		
	}
	
	public String getBestMove(Type[][] b, int counter, boolean team) {
		int c = counter;//debugging
		
		ArrayList<String> moves = ValidMoves.getValidMoves(b, team);

		for(; counter > 0; counter--) {

			for(int i = 0; i < moves.size(); i++) {	
				
				String moveAdded = getBestMove(transformBoard(b, moves.get(i)), counter - 1, !team);
				moves.set(i, moves.get(i) + moveAdded);
				
			}

			
			ArrayList<String> newMoves = new ArrayList<String>();
			
			for(String m1 : moves) {
				
				ArrayList<String> possMoves = ValidMoves.getValidMoves(transformBoard(b, m1), team);
				
				for(String m2: possMoves) {
					newMoves.add(m1 + m2);
				}
			}
			
			moves = newMoves;
			
			if(c == 3) {
				System.out.println(newMoves);
			}
			
		}		

		//debugging
		if(c == 3) {
			return getBest(moves, b, team, true);
		}else {
			return getBest(moves, b, team, false);
		}
	}
	
	private String getBest(ArrayList<String> finalMoves, Type[][] board, boolean team, boolean print) {//returns the best move given a list of moves and board
																						//print variable is for debugging
		if(finalMoves.size() == 0) {
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
		
		if(print) {
			System.out.println(finalMoves.get(getHighest(boardValues)));
			Type.print(finalBoards.get(getHighest(boardValues)));
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
	}
	
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
		}

		int index = (int) (Math.random() * arraySpot.size());

		return arraySpot.get(index);
	}
	
	private Type[][] transformBoard(Type[][] board, String move){
		
		Type[][] b = copy(board);
	
		for(int i = 0; i < move.length() - 1; i += 4) {
			
			b[Integer.parseInt(move.substring(i + 2, i + 3))][Integer.parseInt(move.substring(i + 3, i + 4))] = b[Integer.parseInt(move.substring(i, i + 1))][Integer.parseInt(move.substring(i + 1, i + 2))];
			b[Integer.parseInt(move.substring(i, i + 1))][Integer.parseInt(move.substring(i + 1, i + 2))] = null;
		
		}		
		
		for(int x = 0; x < 8; x++) {
			if(b[0][x] != null && b[0][x].equals(Type.PAWN2)){
				b[0][x] = Type.QUEEN2;
			}
			
			if(b[7][x] != null && b[7][x].equals(Type.PAWN1)) {
				b[7][x] = Type.QUEEN1;
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
