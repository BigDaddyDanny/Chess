import java.util.ArrayList;

public class ValidMoves {

	public static ArrayList<String> getValidMoves(Type[][] b, boolean team) {

		ArrayList<String> moves = new ArrayList<String>();

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				
				Type piece = b[x][y];

				if (piece == null || Type.getTeam(piece) != team) {
					continue;
				}

				switch (piece) {

				case PAWN1:
				case PAWN2:

					for (String move : getPawnMoves(b, x, y)) {
						moves.add(move);
					}

					break;

				case ROOK1:
				case ROOK2:

					for (String move : getRookMoves(b, x, y)) {
						moves.add(move);
					}
					break;

				case BISHOP1:
				case BISHOP2:
					
					for(String move : getBishopMoves(b, x, y)) {
						moves.add(move);
					}

					break;

				case KNIGHT1:
				case KNIGHT2:

					for(String move : getKnightMoves(b, x, y)) {
						moves.add(move);
					}
					break;

				case QUEEN1:
				case QUEEN2:

					for(String move : getQueenMoves(b, x, y)) {
						moves.add(move);
					}
					break;

				case KING1:
				case KING2:

					for(String move : getKingMoves(b, x, y)) {
						moves.add(move);
					}

					break;

				}

			} // end of inner for
		} // end of outer for

		return moves;

	}

	private static ArrayList<String> getPawnMoves(Type[][] b, int x, int y) {// [x][y]

		ArrayList<String> moves = new ArrayList<String>();

		boolean team = Type.getTeam(b[x][y]);
		int mov;
		if (team) {
			mov = 1;
		} else {
			mov = -1;
		}

		boolean firstMove = false;
		if (team && x == 1 || (!team && x == 6)) {
			firstMove = true;
		}

		// adds takings of enemy pieces (up one and to the side)
		for (int i = -1; i <= 1; i += 2) {

			if (x + mov < 0 || x + mov > 7 || y + i < 0 || y + i > 7) {
				continue;
			}

			if (b[x + mov][y + i] != null) {
				if (Type.getTeam(b[x + mov][y + i]) != team) {
					moves.add(String.valueOf(x) + String.valueOf(y) + String.valueOf(x + mov) + String.valueOf(y + i));
				}
			}

		} // end of for

		// adds forward movements
		while (true) {

			if (x + mov < 0 || x + mov > 7) {
				break;
			}

			if (b[x + mov][y] == null) {
				moves.add(String.valueOf(x) + String.valueOf(y) + String.valueOf(x + mov) + String.valueOf(y));
			}

			if (firstMove) {
				firstMove = false;
				mov = mov * 2;
			} else {
				break;
			}

		} // end of while

		return moves;

	}

	private static ArrayList<String> getRookMoves(Type[][] b, int x, int y) {

		ArrayList<String> moves = new ArrayList<String>();

		boolean team = Type.getTeam(b[x][y]);

		int mov = 1;
		// adds moves in the piece's row(left and right)
		while (true) {

			if (y + mov < 0 || y + mov > 7) {

				if (mov > 0) {
					mov = -1;
					continue;
				} else {
					break;
				}

			}

			if (b[x][y + mov] != null) {

				if (Type.getTeam(b[x][y + mov]) != team) {
					moves.add(String.valueOf(x) + String.valueOf(y) + String.valueOf(x) + String.valueOf(y + mov));
				}

				if (mov > 0) {
					mov = -1;
					continue;
				} else {
					break;
				}
			}

			moves.add(String.valueOf(x) + String.valueOf(y) + String.valueOf(x) + String.valueOf(y + mov));

			if (mov > 0) {
				mov++;
			} else {
				mov--;
			}
		}

		mov = 1;
		// adds moves in the piece's row(side to side)
		while (true) {

			if (x + mov < 0 || x + mov > 7) {

				if (mov > 0) {
					mov = -1;
					continue;
				} else {
					break;
				}

			}

			if (b[x + mov][y] != null) {

				if (Type.getTeam(b[x + mov][y]) != team) {
					moves.add(String.valueOf(x) + String.valueOf(y) + String.valueOf(x + mov) + String.valueOf(y));
				}

				if (mov > 0) {
					mov = -1;
					continue;
				} else {
					break;
				}
			}

			moves.add(String.valueOf(x) + String.valueOf(y) + String.valueOf(x + mov) + String.valueOf(y));

			if (mov > 0) {
				mov++;
			} else {
				mov--;
			}
		}

		return moves;
	}

	private static ArrayList<String> getBishopMoves(Type[][] b, int x, int y) {

		ArrayList<String> moves = new ArrayList<String>();
		boolean team = Type.getTeam(b[x][y]);

		for (int i = -1; i <= 1; i += 2) {
			for (int z = -1; z <= 1; z += 2) {

				int temp1 = i;
				int temp2 = z;

				while (true) {

					if (x + i < 0 || x + i > 7 || y + z < 0 || y + z > 7) {

						i = temp1;
						z = temp2;

						break;
					}

					if (b[x + i][y + z] == null) {

						moves.add(
								String.valueOf(x) + String.valueOf(y) + String.valueOf(x + i) + String.valueOf(y + z));

					} else {

						if (Type.getTeam(b[x + i][y + z]) != team) {
							moves.add(String.valueOf(x) + String.valueOf(y) + String.valueOf(x + i)
									+ String.valueOf(y + z));
						}

						i = temp1;
						z = temp2;
						break;

					}

					i += temp1;
					z += temp2;

				}

			}
		}

		return moves;
	}

	private static ArrayList<String> getKnightMoves(Type[][] b, int x, int y) {

		ArrayList<String> moves = new ArrayList<String>();
		boolean team = Type.getTeam(b[x][y]);

		for (int two = -2; two <= 2; two += 4) {
			for (int one = -1; one <= 1; one += 2) {
				
				if (x + one < 0 || x + one > 7 || y + two < 0 || y + two > 7) {
				}else {	

					if (b[x + one][y + two] == null || Type.getTeam(b[x + one][y + two]) != team) {
						System.out.println(String.valueOf(x) + String.valueOf(y) + String.valueOf(x + one)
								+ String.valueOf(y + two));
						moves.add(String.valueOf(x) + String.valueOf(y) + String.valueOf(x + one)
								+ String.valueOf(y + two));
					}
				}
				
				if (x + two < 0 || x + two > 7 || y + one < 0 || y + one > 7) {
				}else {
					if (b[x + two][y + one] == null || Type.getTeam(b[x + two][y + one]) != team) {
						System.out.println(String.valueOf(x) + String.valueOf(y) + String.valueOf(x + two)
								+ String.valueOf(y + one));
						moves.add(String.valueOf(x) + String.valueOf(y) + String.valueOf(x + two)
								+ String.valueOf(y + one));
					}
				}
				

			}
		}

		return moves;
	}

	private static ArrayList<String> getQueenMoves(Type[][] b, int x, int y) {

		ArrayList<String> moves = new ArrayList<String>();
		boolean team = Type.getTeam(b[x][y]);

		for (int i = -1; i <= 1; i += 2) {
			for (int z = -1; z <= 1; z += 2) {

				int temp1 = i;
				int temp2 = z;

				while (true) {

					if (x + i < 0 || x + i > 7 || y + z < 0 || y + z > 7) {

						i = temp1;
						z = temp2;

						break;
					}

					if (b[x + i][y + z] == null) {

						moves.add(
								String.valueOf(x) + String.valueOf(y) + String.valueOf(x + i) + String.valueOf(y + z));

					} else {

						if (Type.getTeam(b[x + i][y + z]) != team) {
							moves.add(String.valueOf(x) + String.valueOf(y) + String.valueOf(x + i)
									+ String.valueOf(y + z));
						}

						i = temp1;
						z = temp2;
						break;

					}

					i += temp1;
					z += temp2;

				}

			}
		}

		int mov = 1;
		// adds moves in the piece's row(left and right)
		while (true) {

			if (y + mov < 0 || y + mov > 7) {

				if (mov > 0) {
					mov = -1;
					continue;
				} else {
					break;
				}

			}

			if (b[x][y + mov] != null) {

				if (Type.getTeam(b[x][y + mov]) != team) {
					moves.add(String.valueOf(x) + String.valueOf(y) + String.valueOf(x) + String.valueOf(y + mov));
				}

				if (mov > 0) {
					mov = -1;
					continue;
				} else {
					break;
				}
			}

			moves.add(String.valueOf(x) + String.valueOf(y) + String.valueOf(x) + String.valueOf(y + mov));

			if (mov > 0) {
				mov++;
			} else {
				mov--;
			}
		}

		mov = 1;
		// adds moves in the piece's row(side to side)
		while (true) {

			if (x + mov < 0 || x + mov > 7) {

				if (mov > 0) {
					mov = -1;
					continue;
				} else {
					break;
				}

			}

			if (b[x + mov][y] != null) {

				if (Type.getTeam(b[x + mov][y]) != team) {
					moves.add(String.valueOf(x) + String.valueOf(y) + String.valueOf(x + mov) + String.valueOf(y));
				}

				if (mov > 0) {
					mov = -1;
					continue;
				} else {
					break;
				}
			}

			moves.add(String.valueOf(x) + String.valueOf(y) + String.valueOf(x + mov) + String.valueOf(y));

			if (mov > 0) {
				mov++;
			} else {
				mov--;
			}
		}

		return moves;
	}

	private static ArrayList<String> getKingMoves(Type[][] b, int x, int y) {
		// a move shouldn't be possible if king is threat
		ArrayList<String> moves = new ArrayList<String>();
		boolean team = Type.getTeam(b[x][y]);
		
		//adds possible moves
		for(int i = -1; i <= 1; i++) {
			for(int z = -1; z <= 1; z++) {
				
				if (x + i < 0 || x + i > 7 || y + z < 0 || y + z > 7) {
					continue;
				}

				if(b[x + i][y + z] == null || Type.getTeam(b[x + i][y + z]) != team) {
					moves.add(String.valueOf(x) + String.valueOf(y) + String.valueOf(x + i) + String.valueOf(y + z));
				}
				
				
			}
		}
		
		//castling moves
		if(team) {
			if(x == 0 && y == 4 && b[0][0] != null && b[0][7] != null) {
				
				if(b[0][0].equals(Type.ROOK1) && b[0][1] == null && b[0][2] == null && b[0][3] == null) {
					moves.add(String.valueOf(x) + String.valueOf(y) + String.valueOf(0) + String.valueOf(0));
				}else if(b[0][7].equals(Type.ROOK1) && b[0][6] == null && b[0][5] == null) {
					moves.add(String.valueOf(x) + String.valueOf(y) + String.valueOf(0) + String.valueOf(7));
				}
			}
		}else {
			if(x == 7 && y == 4 && b[7][0] != null && b[7][7] != null) {
				
				if(b[7][0].equals(Type.ROOK2) && b[7][1] == null && b[7][2] == null && b[7][3] == null) {
					moves.add(String.valueOf(7) + String.valueOf(y) + String.valueOf(7) + String.valueOf(0));
				}else if(b[7][7].equals(Type.ROOK2) && b[7][6] == null && b[7][5] == null) {
					moves.add(String.valueOf(7) + String.valueOf(y) + String.valueOf(7) + String.valueOf(7));
				}
				
			}
		}
		
		
		return moves;
	}// end of getValidMoves()
	
	public ArrayList<String> removeInvalidKingMoves(ArrayList<String> moves, ArrayList<String> enemyMoves, boolean team) {
		
		String kingPos = null;
		Type ownKing;
		if(team) {
			ownKing = Type.KING1;
		}else {
			ownKing = Type.KING2;
		}
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
		
				if(Board.getInstance().getB()[x][y].equals(ownKing)) {
					kingPos = String.valueOf(x) + String.valueOf(y);
					x = 8;//to break out of outer loop
					break;
				}

			}
		}
		
	

		for(int i = 0; i < moves.size(); i++) {
			if(moves.get(i).substring(0, 2).equals(kingPos) ) {
				
			}
		}
		
		
		return moves;
	}
	
	public static boolean checkForKingInCheck(Type[][] b, boolean team) {//returns true if king is in check
			
			ArrayList<String> enemyMoves = getValidMoves(b, !team);
			String kingPos = null;
			
			Type ownKing;
			if(team) {
				ownKing = Type.KING1;
			}else {
				ownKing = Type.KING2;
			}
			
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					if(b[x][y] == null) continue;
			
					if(b[x][y].equals(ownKing)) {
						kingPos = String.valueOf(x) + String.valueOf(y);
						x = 8;//to break out of outer loop
						break;
					}
	
				}
			}
			
		
			//could be optimized
			for(int i = 0; i < enemyMoves.size(); i++) {
				if(enemyMoves.get(i).substring(2, 4).equals(kingPos)) {
					return true;
				}
			}
			
			
			return false;
		}

}// end of class
