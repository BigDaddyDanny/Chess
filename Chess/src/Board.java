import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

//TODO: team(1 == top, 2 == bottom) 
/*
 * have two separate validMoves arrays for each player and compare them to each other
 * to figure out if king is moving onto vulnerable position
*/
public class Board {

	private static Type[][] b;
	private static Board instance;
	private static ArrayList<String> validMoves1;
	private static ArrayList<String> validMoves2;
	private static boolean playerTurn;
	
	public static Board getInstance() {
		
		if(instance == null) {
			instance = new Board();
		}
		
		return instance;
	}

	private Board() {

		b = Type.newBoard();
		playerTurn = true;
		validMoves1 = ValidMoves.getValidMoves(b, true);
		validMoves2 = ValidMoves.getValidMoves(b, false);
				
	}
	
	//can't allow king to move onto a vulnerable square
	//must force player to get out of check
	public void changeBoard(String move) {
		
		ArrayList<String> playerMoving;
		ArrayList<String> playerWaiting;
		if(playerTurn) {
			playerMoving = validMoves1;
			playerWaiting = validMoves2;
		}else {
			playerMoving = validMoves2;
			playerWaiting = validMoves1;
		}
		
		if(!playerMoving.contains(move)) {
			System.out.println("invalid move");
			return;
		}
		
		Type[][] hypo = copy(b);
		hypo[Integer.parseInt(move.substring(2, 3))][Integer.parseInt(move.substring(3, 4))] = hypo[Integer.parseInt(move.substring(0, 1))][Integer.parseInt(move.substring(1, 2))];
		hypo[Integer.parseInt(move.substring(0, 1))][Integer.parseInt(move.substring(1, 2))] = null;
		
		if(ValidMoves.checkForKingInCheck(hypo, playerTurn)) {
			System.out.println("Invalid move: king in check");
			return;
		}
		
		//modify board
		b[Integer.parseInt(move.substring(2, 3))][Integer.parseInt(move.substring(3, 4))] = b[Integer.parseInt(move.substring(0, 1))][Integer.parseInt(move.substring(1, 2))];
		b[Integer.parseInt(move.substring(0, 1))][Integer.parseInt(move.substring(1, 2))] = null;
		
		//Type.print(b);
		
		changePlayerTurn();
	}

	
	private void changePlayerTurn() {
		playerTurn = !playerTurn;
		validMoves1 = ValidMoves.getValidMoves(b, true);
		validMoves2 = ValidMoves.getValidMoves(b, false);
		checkForWin();
	}
	
	private void checkForWin() {
		
		ArrayList<String> playerMoving;
		ArrayList<String> playerWaiting;
		if(playerTurn) {
			playerMoving = validMoves1;
			playerWaiting = validMoves2;
		}else {
			playerMoving = validMoves2;
			playerWaiting = validMoves1;
		}
	
		boolean noMoves = true;
		for(String move : playerMoving) {
			if(!ValidMoves.checkForKingInCheck(b, playerTurn)) {
				noMoves = false;
				break;
			}
		}
		
		if(noMoves) {
			winSequence(!playerTurn);
		}
		
		//checking for absence of kings
		boolean kingOnePresent = false;
		boolean kingTwoPresent = false;
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				
				if(b[x][y] == null) continue;
				if(b[x][y].equals(Type.KING1)) {
					kingOnePresent = true;
				}
				if(b[x][y].equals(Type.KING2)) {
					kingTwoPresent = true;
				}
				
			}
		}
		
		if(!kingOnePresent) {
			winSequence(true);
		}else if(!kingTwoPresent) {
			winSequence(false);
		}
		
		//checking for draw
		if(validMoves1.size() == 0){
			stalemateSequence();
		}else if(validMoves2.size() == 0) {
			stalemateSequence();
		}
		
		
	}
	
	private void winSequence(boolean player) {
		
		String name;
		if(player) {
			name = "White";
		}else {
			name = "Black";
		}
		
		JOptionPane.showMessageDialog(null, name + " is the winner!", "WIN", 1);
		
	}
	
	private void stalemateSequence() {
		JOptionPane.showMessageDialog(null, "Stalemate", "STALEMATE", 1);
	}
	
	private Type[][] copy(Type[][] b) {
		
		Type[][] newB = new Type[8][];
		for (int x = 0; x < 8; x++) {
			newB[x] = Arrays.copyOf(b[x], b[x].length);
		}

		return newB;
	}
	
	public Type[][] getB(){
		return b;
	}
	
}//end of class
