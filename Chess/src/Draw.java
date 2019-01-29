import java.io.FileInputStream;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Draw extends Application {

	private final int WIDTH = 1000;
	private final int HEIGHT = 1000;
	private final int SQUARE_WIDTH = WIDTH / 8;
	private final Paint SQUARE_COLOR1 = Color.SADDLEBROWN;
	private final Paint SQUARE_COLOR2 = Color.ANTIQUEWHITE;
	private String previousMove;
	private Image whiteKing;
	private Image blackKing;
	private Image whiteQueen;
	private Image blackQueen;
	private Image whiteBishop;
	private Image blackBishop;
	private Image whiteRook;
	private Image blackRook;
	private Image whiteKnight;
	private Image blackKnight;
	private Image blackPawn;
	private Image whitePawn;
	private String move;
	private Scene s;
	private Group root;

	public static void startGUI(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		whiteKing = new Image(
				new FileInputStream("C:\\Users\\Danny\\git\\Chess\\Chess\\Chess Sprites\\WhiteKing.png"));
		blackKing = new Image(
				new FileInputStream("C:\\Users\\Danny\\git\\Chess\\Chess\\Chess Sprites\\BlackKing.png"));
		whiteQueen = new Image(      
				new FileInputStream("C:\\Users\\Danny\\git\\Chess\\Chess\\Chess Sprites\\WhiteQueen.png"));
		blackQueen = new Image(      
				new FileInputStream("C:\\Users\\Danny\\git\\Chess\\Chess\\Chess Sprites\\BlackQueen.png"));
		whiteRook = new Image(       
				new FileInputStream("C:\\Users\\Danny\\git\\Chess\\Chess\\Chess Sprites\\WhiteRook.png"));
		blackRook = new Image(      
				new FileInputStream("C:\\Users\\Danny\\git\\Chess\\Chess\\Chess Sprites\\BlackRook.png"));
		whiteBishop = new Image(     
				new FileInputStream("C:\\Users\\Danny\\git\\Chess\\Chess\\Chess Sprites\\WhiteBishop.png"));
		blackBishop = new Image(     
				new FileInputStream("C:\\Users\\Danny\\git\\Chess\\Chess\\Chess Sprites\\BlackBishop.png"));
		whiteKnight = new Image(     
				new FileInputStream("C:\\Users\\Danny\\git\\Chess\\Chess\\Chess Sprites\\WhiteKnight.png"));
		blackKnight = new Image(     
				new FileInputStream("C:\\Users\\Danny\\git\\Chess\\Chess\\Chess Sprites\\BlackKnight.png"));
		blackPawn = new Image(       
				new FileInputStream("C:\\Users\\Danny\\git\\Chess\\Chess\\Chess Sprites\\BlackPawn.png"));
		whitePawn = new Image(       
				new FileInputStream("C:\\Users\\Danny\\git\\Chess\\Chess\\Chess Sprites\\WhitePawn.png"));

		primaryStage.setTitle("Chess");
	
		root = new Group();
		s = new Scene(root, WIDTH, HEIGHT);
		
		move = "";
		
		root.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			
			public void handle(MouseEvent click) {
				
				int y = (int) (click.getSceneX() / SQUARE_WIDTH);
				int x = (int) (click.getSceneY() / SQUARE_WIDTH);
				
				move = move + String.valueOf(x) + String.valueOf(y);
				if(move.equals(previousMove)) {
					move = move.substring(2, 4);
				}
				
				if(move.length() == 4) {
					
					Board.getInstance().changeBoard(move);
					previousMove = move;
					move = "";
					root = reDrawBoard(root);
					
				}	
			}
			
			
		});
		
		

		reDrawBoard(root);

		primaryStage.setScene(s);
		primaryStage.show();
	}

	private Group reDrawBoard(Group root) {

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Rectangle r = new Rectangle(row * SQUARE_WIDTH, col * SQUARE_WIDTH, SQUARE_WIDTH, SQUARE_WIDTH);
				if ((row + col) % 2 == 0)
					r.setFill(SQUARE_COLOR1);
				else
					r.setFill(SQUARE_COLOR2);

				root.getChildren().add(r);
			}
		}

		Type[][] b = Board.getInstance().getB();

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (b[row][col] == null) {
					continue;
				}

				Image temp = null;
				switch (b[row][col]) {

				case ROOK1:
					temp = whiteRook;
					break;
				case BISHOP1:
					temp = whiteBishop;
					break;
				case KNIGHT1:
					temp = whiteKnight;
					break;
				case QUEEN1:
					temp = whiteQueen;
					break;
				case KING1:
					temp = whiteKing;
					break;
				case PAWN1:
					temp = whitePawn;
					break;
				case ROOK2:
					temp = blackRook;
					break;
				case BISHOP2:
					temp = blackBishop;
					break;
				case KNIGHT2:
					temp = blackKnight;
					break;
				case QUEEN2:
					temp = blackQueen;
					break;
				case KING2:
					temp = blackKing;
					break;
				case PAWN2:
					temp = blackPawn;
					break;

				}

				ImageView piece = new ImageView(temp);

				piece.setX(col * SQUARE_WIDTH);
				piece.setY(row * SQUARE_WIDTH);
				piece.setFitHeight(SQUARE_WIDTH);
				piece.setFitWidth(SQUARE_WIDTH);
				piece.preserveRatioProperty();

				root.getChildren().add(piece);

			}
		}

		
		return root;
		
	}

}
