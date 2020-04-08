
/*	TODO: 
 * 
 * 	High Priority:
 *  - need a way to determine if king is in checkmate to determine if win
 *  - clean up AI - somethings wrong not adding best move of player
 *  ----------------------------------------------------
 *  Low Priority:
 *  - clean up GUI
 * 
	*/
public class Drive{

	public static void main(String[] args) {
		
		Board.getInstance().init();
		Draw.startGUI(args);
		
	}

}
