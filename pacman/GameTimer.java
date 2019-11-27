package pacman;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class GameTimer extends AnimationTimer{
	private boolean gameOver;
	private boolean victory;
	private GraphicsContext gc;
	private Scene theScene;
	private int[][] map;
	private Pacman pac;
	private ArrayList<Ghost> ghosts;
	private ArrayList<PacDot> dots;
	
	private final static int MAP_LENGTH = 28;
	private final static int MAP_HEIGHT = 31;
	private final static int[][] GAME_MAP = initMap();
	private final static int B = 0;
	private final static int W = 1;
	private final static int d = 2;
	private final static int D = 3;
	
	GameTimer(GraphicsContext gc, Scene theScene){
		this.gameOver = false;
		this.victory = false;
		this.map = initMap();
		this.gc = gc;
		this.theScene = theScene;
		this.pac = new Pacman((int)GameStage.WINDOW_WIDTH/2, (int)GameStage.WINDOW_HEIGHT/2);			
		//call method to handle mouse click event
		this.handleKeyPressEvent();
	}
	
	public static int[][] initMap() {
		int[][] map = {
				{W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W},
				{W,d,d,d,d,d,d,d,d,d,d,d,d,W,W,d,d,d,d,d,d,d,d,d,d,d,d,W},
				{W,d,W,W,W,W,d,W,W,W,W,W,d,W,W,d,W,W,W,W,W,d,W,W,W,W,d,W},
				{W,d,W,W,W,W,d,W,W,W,W,W,d,W,W,d,W,W,W,W,W,d,W,W,W,W,d,W},
				{W,d,W,W,W,W,d,W,W,W,W,W,d,W,W,d,W,W,W,W,W,d,W,W,W,W,d,W},
				{W,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,W},
				{W,d,W,W,W,W,d,W,W,d,W,W,W,W,W,W,W,W,d,W,W,d,W,W,W,W,d,W},
				{W,d,W,W,W,W,d,W,W,d,W,W,W,W,W,W,W,W,d,W,W,d,W,W,W,W,d,W},
				{W,d,d,d,d,d,d,W,W,d,d,d,d,W,W,d,d,d,d,W,W,d,d,d,d,d,d,W},
				{W,W,W,W,W,W,d,W,W,W,W,W,d,W,W,d,W,W,W,W,W,d,W,W,W,W,W,W},
				{B,B,B,B,B,W,d,W,W,W,W,W,d,W,W,d,W,W,W,W,W,d,W,B,B,B,B,B},
				{B,B,B,B,B,W,d,W,W,d,d,d,d,d,d,d,d,d,d,W,W,d,W,B,B,B,B,B},
				{B,B,B,B,B,W,d,W,W,d,W,W,W,B,B,W,W,W,d,W,W,d,W,B,B,B,B,B},
				{W,W,W,W,W,W,d,W,W,d,W,B,B,B,B,B,B,W,d,W,W,d,W,W,W,W,W,W},
				{d,d,d,d,d,d,d,d,d,d,W,B,B,B,B,B,B,W,d,d,d,d,d,d,d,d,d,d},
				{W,W,W,W,W,W,d,W,W,d,W,B,B,B,B,B,B,W,d,W,W,d,W,W,W,W,W,W},
				{B,B,B,B,B,W,d,W,W,d,W,W,W,W,W,W,W,W,d,W,W,d,W,B,B,B,B,B},
				{B,B,B,B,B,W,d,W,W,d,d,d,d,d,d,d,d,d,d,W,W,d,W,B,B,B,B,B},
				{B,B,B,B,B,W,d,W,W,d,W,W,W,W,W,W,W,W,d,W,W,d,W,B,B,B,B,B},
				{W,W,W,W,W,W,d,W,W,d,W,W,W,W,W,W,W,W,d,W,W,d,W,W,W,W,W,W},
				{W,d,d,d,d,d,d,d,d,d,d,d,d,W,W,d,d,d,d,d,d,d,d,d,d,d,d,W},
				{W,d,W,W,W,W,d,W,W,W,W,W,d,W,W,d,W,W,W,W,W,d,W,W,W,W,d,W},
				{W,d,W,W,W,W,d,W,W,W,W,W,d,W,W,d,W,W,W,W,W,d,W,W,W,W,d,W},
				{W,d,d,d,W,W,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,W,W,d,d,d,W},
				{W,W,W,d,W,W,d,W,W,d,W,W,W,W,W,W,W,W,d,W,W,d,W,W,d,W,W,W},
				{W,W,W,d,W,W,d,W,W,d,W,W,W,W,W,W,W,W,d,W,W,d,W,W,d,W,W,W},
				{W,d,d,d,d,d,d,W,W,d,d,d,d,W,W,d,d,d,d,W,W,d,d,d,d,d,d,W},
				{W,d,W,W,W,W,W,W,W,W,W,W,d,W,W,d,W,W,W,W,W,W,W,W,W,W,d,W},
				{W,d,W,W,W,W,W,W,W,W,W,W,d,W,W,d,W,W,W,W,W,W,W,W,W,W,d,W},
				{W,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,W},
				{W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W}
				};
		
		return map;
	}

	@Override
	public void handle(long currentNanoTime) {
		
		//Clears the screen
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		
		//Renders and updates game elements when game is not over
		if(!this.gameOver) {
			this.renderGameSprites();
		}else {
			System.out.println("Game over");
			this.showGameOver();
		}
	}
	
	//method that will listen and handle the key press events
	private void handleKeyPressEvent() {
		theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                movePac(code);
			}
		});
		
		theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
		            public void handle(KeyEvent e){
		            	KeyCode code = e.getCode();
		                stopPac(code);
		            }
		        });
    }
	
	private void renderGameSprites() {
		
		//Moves and renders pacman
		this.pac.move();
		this.pac.render(this.gc);
		
		//Moves and renders all alive fishes
//		for(Ghost g : this.ghosts) {
//			g.move();
//			g.render(this.gc);
//		}
		
		//Moves and renders all functioning bullets
//		for(PacDot d : this.dots) {
//			if(d.isVisible()) d.render(this.gc);
//		}
		
		this.showScore(this.gc);
	}
	
	private void showGameOver() {
		//Make background black
		this.gc.setFill(Color.BLACK);
		this.gc.fillRect(0,0,GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		
		//Add text "Game Over"
		this.gc.setFont(Font.font("Arial", FontWeight.BLACK, FontPosture.REGULAR, 50));
		this.gc.setFill(Color.RED);
		this.gc.fillText("GAME OVER", GameStage.WINDOW_WIDTH*0.3, GameStage.WINDOW_HEIGHT*0.5, GameStage.WINDOW_WIDTH*0.4);	
		this.stop();
		
	}
	
	private void showScore(GraphicsContext gc) {
		this.gc.setFont(Font.loadFont("file:src/fonts/Pixellari.ttf", 50));
		this.gc.setFill(Color.WHITE);
		this.gc.fillText("Score: "+this.pac.getScore(), GameStage.WINDOW_WIDTH*0.02, GameStage.WINDOW_HEIGHT*0.9);
	}
	
	//method that will move the ship depending on the key pressed
	private void movePac(KeyCode ke) {
		
		if(ke==KeyCode.UP) this.movePacUpdate(0, -3, Pacman.PACMAN_UP);

		if(ke==KeyCode.LEFT) this.movePacUpdate(-3, 0, Pacman.PACMAN_LEFT);
		
		if(ke==KeyCode.DOWN) this.movePacUpdate(0, 3, Pacman.PACMAN_DOWN);
		
		if(ke==KeyCode.RIGHT) this.movePacUpdate(3, 0, Pacman.PACMAN_RIGHT);
		
		System.out.println(ke+" key pressed.");
   	}
	
	private void movePacUpdate(int dx, int dy, Image img) {
		this.pac.setDY(dy);
		this.pac.setDX(dx);
		this.pac.loadImage(img);
	}
	
	//method that will stop the ship's movement; set the ship's DX and DY to 0
	private void stopPac(KeyCode ke){
		this.pac.setDX(0);
		this.pac.setDY(0);
	}
}
