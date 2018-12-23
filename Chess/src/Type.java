
public enum Type {
	
	PAWN1, ROOK1, BISHOP1, KNIGHT1, QUEEN1, KING1, PAWN2, ROOK2, BISHOP2, KNIGHT2, QUEEN2, KING2;
	
	public static Type[][] newBoard(){
		
		
		Type[][] b = 
			{{ROOK1, KNIGHT1, BISHOP1, QUEEN1, KING1, BISHOP1, KNIGHT1, ROOK1},
			{PAWN1, PAWN1, PAWN1, PAWN1, PAWN1, PAWN1, PAWN1, PAWN1},
			{ null, null, null, null, null, null, null, null},
			{ null, null, null, null, null, null, null, null},
			{ null, null, null, null, null, null, null, null},
			{ null, null, null, null, null, null, null, null},
			{PAWN2, PAWN2, PAWN2, PAWN2, PAWN2, PAWN2, PAWN2, PAWN2},
			{ROOK2, KNIGHT2, BISHOP2, QUEEN2, KING2, BISHOP2, KNIGHT2, ROOK2},
				
			};
		
		return b;
	}
	
	public static boolean getTeam(Type piece) {
		
		if(piece == null) {
			System.out.println("ERROR: GET_TEAM CALLED WITH NULL PIECE");
			return false;
		}
		
		switch (piece) {
			
			case ROOK1:
				return true;
				
			case BISHOP1:
				return true;
				
			case KNIGHT1:
				return true;
				
			case QUEEN1:
				return true;
				
			case KING1:
				return true;
				
			case PAWN1:
				return true;
		default:
			break;
				
				
				
			
		}
				
						
		return false;
		
	}//end of getTeam
	
	public static void print(Type[][] b) {
		
		for (int u = 0; u < 8; u++) {
			System.out.println("\n");
			for (int y = 0; y < 8; y++) {
				String temp = String.valueOf(b[u][y]);
				int length = temp.length();
				for(int characters = length; characters <= 7; characters++ ) {
					temp = temp + " ";
				}
				
				System.out.print(temp + " ");
			}
		}
		System.out.println();
	}
	
}
