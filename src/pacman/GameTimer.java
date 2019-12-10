package pacman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import java.io.*;
import java.io.File;
import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameTimer extends AnimationTimer{
	private boolean gameOver;
	private boolean victory;
	private Group root;
	private GridPane map;
	private Canvas canvas;
	private GraphicsContext gc;
	private Scene theScene;
	private Pacman pac;
	private ArrayList<Ghost> ghosts;
	private ArrayList<ArrayList<ImageView>> tiles;
	
	public static int MAP_LENGTH;
	public static int MAP_HEIGHT;
	public static double CELL_WIDTH;
	public static double CELL_HEIGHT;
	
	//private final static int[][] MAP_MATRIX = initMap();
	public final static int[][] GAME_MAP = initMap();
			
	
	//	[0] GAME SETUP
	GameTimer(Group root, Scene theScene, GridPane gp){
		this.root = root;
		this.gameOver = false;
		this.victory = false;
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.theScene = theScene;
		this.map = gp;
		
		//	Set-up game map size and image array constraints
		MAP_HEIGHT = GAME_MAP.length;
		MAP_LENGTH = GAME_MAP[0].length;
		CELL_WIDTH = GameStage.WINDOW_WIDTH/MAP_LENGTH;
		CELL_HEIGHT = GameStage.WINDOW_HEIGHT/MAP_HEIGHT;
		
		//	Set-up game elements
		this.tiles = new ArrayList<ArrayList<ImageView>>(MAP_LENGTH);
		this.pac = new Pacman(15, 6);
		this.ghosts = new ArrayList<Ghost>(4); 
		this.ghosts.add(new Blinky(1,1));
		this.ghosts.add(new Inky(1,MAP_HEIGHT-2));
		this.ghosts.add(new Pinky(MAP_LENGTH-2,1));
		this.ghosts.add(new Clyde(MAP_LENGTH-2,MAP_HEIGHT-2));
		
		//	Set-up ImageView Array (Game GUI)
		this.initiateImageViewArray();
		this.setGridPaneProperties();
		this.addGridPaneConstraints();
		
		for(ArrayList<ImageView> row : tiles)	//	Adds images to map
			for(ImageView i : row)
				this.map.getChildren().add(i);
		
		this.root.getChildren().addAll(map,canvas);
		
		this.handleKeyPressEvent();
	}
	
	public static int[][] initMap() {
//		int[][] mapMatrix = {
//		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
//		{1,2,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,2,1},
//		{1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,1,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,1,1,0,1,0,1},
//		{1,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,1,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,1},
//		{1,0,1,0,1,1,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,0,1,0,1,1,1,0,1,0,1,1,1,0,1,0,1},
//		{1,0,0,0,0,0,0,0,1,0,1,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,1,0,0,0,0,0,0,0,1},
//		{1,0,1,0,1,1,1,0,1,0,1,0,1,1,1,0,1,1,1,1,1,0,1,1,1,0,1,0,1,0,1,1,1,0,1,0,1},
//		{1,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1},
//		{1,0,1,0,1,0,1,0,1,0,1,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,0,1},
//		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,1,1,1,1,1,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
//		{1,0,1,1,1,1,1,0,1,0,1,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,0,1,0,1,1,1,1,1,0,1},
//		{1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
//		{1,0,1,0,1,1,1,0,1,0,1,0,1,1,1,0,1,1,1,1,1,0,1,1,1,0,1,0,1,0,1,1,1,0,1,0,1},
//		{1,0,1,0,0,0,1,0,1,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,1,0,1,0,0,0,1,0,1},
//		{1,0,1,1,1,0,1,0,1,0,1,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,1,0,1,0,1,0,1,1,1,0,1},
//		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//		{1,0,1,0,1,1,1,0,1,1,1,0,1,0,1,0,1,1,1,1,1,0,1,0,1,0,1,1,1,0,1,1,1,0,1,0,1},
//		{1,0,1,0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,1,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,1,0,1},
//		{1,0,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,1,1,0,1,0,1,0,1},
//		{1,2,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,2,1},
//		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
//		};
////		
//		return mapMatrix;
//		}		
		//LOAD MAP FROM TEXT FILE
		//int[][] mapMatrix = new int[15][30];
		
		try {
		Scanner sc = new Scanner(new BufferedReader(new FileReader("src/pacman/lvl1.txt")));
		int rows = 21;
	    int columns = 37;
	    int [][] mapMatrix = new int[rows][columns];
	   
	    while(sc.hasNextLine()) {
	    	for (int i=0; i<mapMatrix.length; i++) {
	    		String[] line = sc.nextLine().trim().split(" ");
	    				for (int j=0; j<line.length; j++) {
	               mapMatrix[i][j] = Integer.parseInt(line[j]);
	            }
	         }
	      }
	      System.out.println(Arrays.deepToString(mapMatrix));
		return mapMatrix;
		}catch(IOException i) {
			System.out.println("cant read file");
			return null;
		}
		
	}	
	
	
	@Override
	public void handle(long currentNanoTime) {
		
		//Clears the screen
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		
		//Renders and updates game elements when game is not over
		if(this.pac.getLives()>=1) {
			this.showScore(this.gc);
			this.renderGameSprites();
		}else this.showGameOver();
	}
	
	private void initiateImageViewArray(){
		//	Sets up images to be placed on GUI
		//	Replace images here
		
		for(int i = 0; i < GAME_MAP.length; i++) {
			ArrayList<ImageView> temp = new ArrayList<ImageView>(MAP_HEIGHT);
			for(int j = 0; j < GAME_MAP[i].length; j++) {
				switch(GAME_MAP[i][j]) {
				
					//Element is a wall
					case 1: temp.add(new ImageView(new Image("images/wall.png", CELL_WIDTH, CELL_HEIGHT, false, false))); break;
					
					//Element is a small dot
					case 0: temp.add(new ImageView(PacDot.PACDOT_IMG)); break;
					
					//Element is a fruit
					case 2: temp.add(new ImageView(PacDot.BPACDOT_IMG)); break;
					
				}
			}
			this.tiles.add(temp);
		}
		
	}
	
	private void setGridPaneProperties() {
		this.map.setPrefSize(GameStage.WINDOW_WIDTH-52, GameStage.WINDOW_HEIGHT-104);
		this.map.setLayoutX(9);
		this.map.setLayoutY(1);
	}
	
	private void addGridPaneConstraints(){
		
		//Set number of rows and height of each row
		for(int i=0;i<MAP_HEIGHT;i++){
			RowConstraints row = new RowConstraints();
			row.setPercentHeight(GameTimer.CELL_HEIGHT);
			this.map.getRowConstraints().add(row);
		}
	    
	    //Set the number of columns and width of each column
		for(int i=0;i<MAP_LENGTH;i++){
			ColumnConstraints col = new ColumnConstraints();
			col.setPercentWidth(GameTimer.CELL_WIDTH);
			this.map.getColumnConstraints().add(col);
		}
	          
		 //loop that will add the image views / tile images to the gridpane
	     for(int row=0;row<MAP_HEIGHT;row++){
	    	 for(int col=0;col<MAP_LENGTH;col++){
	    		 //set each tileCells arraylist element (imageview) to the gridpane/map's constraints
	    		 GridPane.setConstraints(this.tiles.get(row).get(col),col,row);
	    	 }
	     }   
	}
	//	[0] END - GAME SETUP

	

	
	//	[1] GAME LISTENER
	private void handleKeyPressEvent() {
		theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                movePac(code);
			}
		});
    }
	
	
	
	//	[2] GAME UPDATES AND GUI
	private void changeCellImage(int row, int col, Image img) {
		ImageView iv = this.tiles.get(row).get(col);
		iv.setImage(img);
	}
	
	private void renderGameSprites() {
		
		for(Ghost g : this.ghosts) {
			if(this.pac.getX() == g.getX() && this.pac.getY() == g.getY())
				this.pacDied();
		}
		
		this.movePacUpdate();
		this.moveGhostUpdate();
		this.showScore(this.gc);
		
	}
	
	private void showScore(GraphicsContext gc) {
		this.gc.setFont(Font.loadFont("file:src/fonts/Pixellari.ttf", 30));
		this.gc.setFill(Color.WHITE);
		this.gc.fillText("Score: "+ this.pac.getScore()+" Lives: "+this.pac.getLives(), GameStage.WINDOW_WIDTH*0.02, GameStage.WINDOW_HEIGHT*0.9);
	}
	
	private void movePac(KeyCode ke) {
		if(ke==KeyCode.UP && !this.pac.isNextPosWall(this.pac.getY()-1, this.pac.getX())) this.pac.moveUp(Pacman.PACMAN_UP);
		if(ke==KeyCode.LEFT && !this.pac.isNextPosWall(this.pac.getY(), this.pac.getX()-1)) this.pac.moveLeft(Pacman.PACMAN_LEFT);		
		if(ke==KeyCode.DOWN && !this.pac.isNextPosWall(this.pac.getY()+1, this.pac.getX())) this.pac.moveDown(Pacman.PACMAN_DOWN);
		if(ke==KeyCode.RIGHT && !this.pac.isNextPosWall(this.pac.getY(), this.pac.getX()+1)) this.pac.moveRight(Pacman.PACMAN_RIGHT);
		if(this.pac.isNextPosWall((int) Math.ceil((this.pac.getY()+this.pac.getDY())), (int) Math.ceil(this.pac.getX()+this.pac.getDX()))) this.stopPac();
   	}
	
	private void stopPac() {
		this.pac.setDxDy(0,0);
	}
	
	private void movePacUpdate() {
		this.pac.move();
		this.changeCellImage(this.pac.getPY(), this.pac.getPX(), null);
		this.changeCellImage(this.pac.getY(), this.pac.getX(), this.pac.getImage());
		
		//	All moveable cells have dots, set to -1 (blank cell representation)
		GAME_MAP[this.pac.getY()][this.pac.getX()] = -1;
	}
	
	private void moveGhostUpdate() {
		for(Ghost g : this.ghosts) {
			g.chase(this.pac.getX(), this.pac.getY());
			this.updateGhostTrail(g);
			this.changeCellImage(g.getY(), g.getX(), g.GHOST_IMAGE);
		}
	}
	
	private void pacDied() {
		this.pac.die();
		for(Ghost g : this.ghosts) {
			g.resetPosition();
			this.updateGhostTrail(g);
		}
		try {
			Thread.sleep(1000);
		}catch(Exception e) {}
	}
	
	private void updateGhostTrail(Ghost ghost) {
		int py = ghost.getPY(), px = ghost.getPX();
			
		if( GAME_MAP[py][px] == 0) this.changeCellImage(py, px, PacDot.PACDOT_IMG);
		if( GAME_MAP[py][px] == 2) this.changeCellImage(py, px, PacDot.BPACDOT_IMG);
		if( GAME_MAP[py][px] == -1) this.changeCellImage(py, px, null);
		
	}
	
	private void showGameOver() {
		//Make background black
		this.gc.setFill(Color.BLACK);
		this.gc.fillRect(0,0,GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		
		//Add text "Game Over"
		this.gc.setFont(Font.loadFont("file:src/fonts/Pixellari.ttf", 50));
		this.gc.setFill(Color.RED);
		this.gc.fillText("GAME OVER", GameStage.WINDOW_WIDTH*0.40, GameStage.WINDOW_HEIGHT*0.5, GameStage.WINDOW_WIDTH*0.4);
		this.gc.setFont(Font.loadFont("file:src/fonts/Pixellari.ttf", 25));
		this.gc.setFill(Color.WHITE);
		this.gc.fillText("Your Score: "+this.pac.getScore(), GameStage.WINDOW_WIDTH*0.43, GameStage.WINDOW_HEIGHT*0.55, GameStage.WINDOW_WIDTH*0.4);	
	
		
		//HIGH SCORE PART
		int highScore = 0;
		File file = new File("src/pacman/highScore.txt");
		try {
			Scanner scan = new Scanner(file);
			highScore = scan.nextInt();
			scan.close();
		}catch (IOException i) {
	        System.out.println("cant read file");
	    }
		
		this.gc.setFont(Font.loadFont("file:src/fonts/Pixellari.ttf", 25));
		this.gc.setFill(Color.WHITE);
		this.gc.fillText("High Score: "+ highScore, GameStage.WINDOW_WIDTH*0.43, GameStage.WINDOW_HEIGHT*0.60, GameStage.WINDOW_WIDTH*0.4);	
		
		if (highScore < this.pac.getScore()) {
			highScore = this.pac.getScore();
			try {
			PrintStream out = new PrintStream(file);
			PrintStream console = System.out; 
			System.setOut(out);
			System.out.print(highScore); 
			out.close();
			}catch (IOException i) {
	            System.out.println("cant write to file");
	        }
		}
		
		
		
		
		this.stop();
	}
}
