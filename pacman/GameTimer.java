package pacman;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;  
import java.util.Scanner;
import javafx.geometry.Rectangle2D;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import java.lang.Math.*;
import java.util.Random;

public class GameTimer extends AnimationTimer{
	private boolean gameOver;
	private boolean victory; 
	private GraphicsContext gc;
	private Scene theScene;
	private Pacman pac;
	private ArrayList<Ghost> ghosts;
	private ArrayList<PacDot> dots;
	private ArrayList<Wall> walls;
	private ArrayList<Fruit> fruits;
//	private Ghost ghost1;
//	private Ghost ghost2;
//	private Ghost ghost3;
//	private Ghost ghost4;
	private String direction;
//	private static long time1=0;
//	private static long time2=0;
//	private static boolean recordedTime1=false;
////	private static boolean recordedTime2=false;
//	private static long duration=0;
	
	public static int HALF_WIDTH=(int) Math.floor(GameStage.WINDOW_WIDTH/2);
	public static int HALF_HEIGHT=(int)Math.floor(GameStage.WINDOW_HEIGHT/2);
	private final static int MAP_COLS = 30;
	private final static int MAP_ROWS = 15;

	private final static int[][] MAP_MATRIX = initMap();
//	private final static int 0 = 0;
//	private final static int 1 = 1;
//	private final static int 0 = 2;
//	private final static int D = 3;
	
	GameTimer(GraphicsContext gc, Scene theScene){
		this.gameOver = false;
		this.victory = false;
		this.gc = gc;
		this.theScene = theScene; 
		this.handleKeyPressEvent();
		this.walls = new ArrayList<Wall>();
		this.dots = new ArrayList<PacDot>();
		this.ghosts = new ArrayList<Ghost>();
		this.fruits=new ArrayList<Fruit>();
		this.generateMap();
		this.direction="UP";
		this.pac = new Pacman(GameTimer.HALF_WIDTH,GameTimer.HALF_HEIGHT);
//		this.ghost1=new Ghost("Ghost1",(int)40+GameTimer.HALF_WIDTH-600,(int)40+GameTimer.HALF_HEIGHT-300);
//		this.ghost2=new Ghost("Ghost2",(int)-80+GameTimer.HALF_WIDTH+600,(int)40+GameTimer.HALF_HEIGHT-300);
//		this.ghost3=new Ghost("Ghost3",(int)40+GameTimer.HALF_WIDTH-600,(int)-80+GameTimer.HALF_HEIGHT+300);
//		this.ghost4 =new Ghost("Ghost4",(int)-80+GameTimer.HALF_WIDTH+600,(int)-80+GameTimer.HALF_HEIGHT+300);
		this.ghosts.add(new Ghost("Ghost1",(int)40+GameTimer.HALF_WIDTH-600,(int)40+GameTimer.HALF_HEIGHT-300));
		this.ghosts.add(new Ghost("Ghost2",(int)40+GameTimer.HALF_WIDTH-600,(int)-80+GameTimer.HALF_HEIGHT+300));
		this.ghosts.add(new Ghost("Ghost3",(int)-80+GameTimer.HALF_WIDTH+600,(int)40+GameTimer.HALF_HEIGHT-300));
		this.ghosts.add(new Ghost("Ghost4",(int)-80+GameTimer.HALF_WIDTH+600,(int)-80+GameTimer.HALF_HEIGHT+300));
		//call method to handle mouse click event

//		this.mapMatrix = new int[MAP_HEIGHT][MAP_LENGTH];

	}
	
	public static int[][] initMap() {
//		int[][] mapMatrix = {
//				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
//				{1,3,0,0,0,0,0,0,1,0,0,0,0,0,1,1,1,0,0,0,0,1,0,0,0,0,0,0,2,1},
//				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//				{1,0,1,1,1,1,0,0,1,1,1,1,1,0,0,1,0,0,1,1,1,1,0,0,1,1,1,1,0,1},
//				{1,0,1,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,1},
//				{1,0,1,0,0,0,0,1,1,0,0,0,0,1,1,1,1,1,0,0,0,1,1,0,0,1,6,1,0,1},
//				{1,0,1,0,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,1},
//				{1,0,1,0,6,1,0,0,1,1,1,1,1,0,0,0,0,0,1,1,1,1,0,0,1,1,1,1,0,1},
//				{1,0,1,0,1,1,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1},
//				{1,0,1,0,0,0,0,0,0,0,1,6,1,0,0,1,0,0,1,6,1,0,0,1,1,0,1,0,0,1},
//				{1,0,1,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1},
//				{1,0,1,1,1,1,0,0,1,1,1,1,1,0,1,1,1,0,1,1,1,1,0,0,1,1,1,1,0,1},
//				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//				{1,4,0,0,0,0,1,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,1,0,0,0,5,1},
//				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
		
//		return mapMatrix;
		
		//LOAD MAP FROM TEXT FILE
		//int[][] mapMatrix = new int[15][30];
		
//		File file = new File("src/pacman/level1.txt");
//		
//		try {
//			Scanner scan = new Scanner(file);
//			highScore = scan.nextInt();
//			scan.close();
//		}catch (IOException i) {
//            System.out.println("cant read file");
//        }
//		
		try {
		Scanner sc = new Scanner(new BufferedReader(new FileReader("src/pacman/lvl1.txt")));
		int rows = 15;
	    int columns = 30;
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
//		setTime1(currentNanoTime);
//		setTime2(currentNanoTime);
//		if (time2-time1>=1000000000) {
//			System.out.println("1s");
//			recordedTime1=false;;
//		}
		//System.out.println(currentNanoTime);
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);	
		//System.out.println("HAYS:"+this.pac.getFacing());
		if(!this.gameOver) {
			this.renderGameBoard();
			//this.stop();
			for (Wall w: this.walls) {
				this.pac.setFrontRectangle();
				if (w.collidesWith(this.pac.getFront()) || w.collidesWith(this.pac)) {
					this.stopPac();
				}
				
			}
			for (Wall w: walls) {
				for (Ghost g: ghosts) {
					g.setFrontRectangle();
					//g.chasePac(this.pac);
					if (w.collidesWith(g.getFront())) {
						this.stopGhost(g);
						g.setWeak(false);
						g.randomizeFacing();
					}					
				}
			}
			for (PacDot p: this.dots) {
				if (this.pac.collidesWith(p)) {
					p.die();
					this.pac.eat(p);
				}
			}
			for (Ghost g: ghosts) {
				//System.out.println("Name"+g.getName());
				if (g.collidesWith(this.pac)) {
					if (g.isWeak()) {
						g.die();
						this.pac.eat(g);
					}else {
						this.pac.decreaseLife();
					}
					if (this.pac.getLives()==0) {
						this.gameOver=true;
					}
					else if (!g.isWeak()){
						this.stopPac();
						this.pac.setX(GameTimer.HALF_WIDTH); this.pac.setY(GameTimer.HALF_HEIGHT);
						//this.start();
					}
					//this.pac.setInvulnerable();
//					while (!forDuration(1)) {
//						this.pac.setInvulnerable();
//					}
//					this.pac.setVulnerable();
				}
			}
			for (Fruit f: this.fruits) {
				if (f.collidesWith(this.pac)) {
					f.die();
					this.pac.eat(f);
					if (f.getType()=="Freeze") {
						for (Ghost g: ghosts) {
							g.setWeak(true);
							g.setSpeed(.5);
						}
					}
				}
			}
	
		}else {
			System.out.println("Game over");
			this.showGameOver();
			this.stop();
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
		
//		theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
//		            public void handle(KeyEvent e){
//		            	KeyCode code = e.getCode();
//		                //stopPac(code);
//		            }
//		        });
    }
	
	private void renderGameBoard() {
		//Moves and renders pacman
		this.pac.setFacing(this.direction);
		this.pac.move();
		this.pac.render(this.gc);
		//
		for (Wall w: this.walls) {
			w.render(this.gc);
		}		
		for (PacDot p: this.dots) {
			if (p.isAlive()) p.render(this.gc);
		}
		for (Fruit f: fruits) {
			if (f.isAlive()) f.render(this.gc);
		}

		
		for (Ghost g: ghosts) {
			if (g.isWeak() && g.isAlive()) {
				this.moveGhost(g);
				g.move();
				g.render(Ghost.WEAK_GHOST,this.gc);
			}
			else if (g.isAlive()) {
				this.moveGhost(g);
				g.move();
				g.render(this.gc);
			}else {
				g.setX(0);
				g.setY(0);
			}
		}

		
		this.showScore(this.gc);
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
	
	private void showScore(GraphicsContext gc) {
		this.gc.setFont(Font.loadFont("file:src/fonts/Pixellari.ttf", 30));
		this.gc.setFill(Color.WHITE);
		this.gc.fillText("Score: "+this.pac.getScore(), 40, 40);
		this.gc.fillText("Lives: "+this.pac.getLives(), 240, 40);
	}
	
	//method that will move the ship depending on the key pressed
	private void movePac(KeyCode ke) {
		
		if(ke==KeyCode.UP) {
			this.movePacUpdate(0, -3, Pacman.PACMAN_UP);
			this.direction="UP";
//			pac.setFacing("UP");
//			Rectangle2D front = new Rectangle2D(pac.getX()+10,pac.getY()-10,10,10);
//			pac.setFront(front);
		}

		if(ke==KeyCode.LEFT) {
			this.movePacUpdate(-3, 0, Pacman.PACMAN_LEFT);
			this.direction="LEFT";
//			pac.setFacing("LEFT");
//			Rectangle2D front = new Rectangle2D();
//			pac.setFront(front);
		}
		
		if(ke==KeyCode.DOWN) {
			this.movePacUpdate(0, 3, Pacman.PACMAN_DOWN);
			this.direction="DOWN";
	//		pac.setFacing("DOWN");
//			Rectangle2D front = new Rectangle2D();
//			pac.setFront(front);
		}
		
		if(ke==KeyCode.RIGHT) {
			this.movePacUpdate(3, 0, Pacman.PACMAN_RIGHT);
			this.direction="RIGHT";
//			pac.setFacing("RIGHT");
//			Rectangle2D front = new Rectangle2D();
//			pac.setFront(front);
		}
		
		System.out.println(ke+" key pressed.");
   	}
	
	private void movePacUpdate(int dx, int dy, Image img) {
		this.pac.setDY(dy);
		this.pac.setDX(dx);
		this.pac.loadImage(img);
	}
	
	//method that will stop the ship's movement; set the ship's DX and DY to 0
	public void stopPac(){
		this.pac.setDX(0);
		this.pac.setDY(0);
	}
	public void generateMap() {
		for (int i=0; i<MAP_ROWS;i++) {
			for (int j=0; j<MAP_COLS;j++) {
				if (MAP_MATRIX[i][j]==1) {
					Wall w = new Wall((j*40)+GameTimer.HALF_WIDTH-600,(i*40)+GameTimer.HALF_HEIGHT-300);
					this.walls.add(w);
				}else if(MAP_MATRIX[i][j]==2) {
					Fruit f = new Fruit((j*40)+10+GameTimer.HALF_WIDTH-600,(i*40)+10+GameTimer.HALF_HEIGHT-300,"Cherry");
					this.fruits.add(f);
				}else if(MAP_MATRIX[i][j]==3) {
					Fruit f = new Fruit((j*40)+10+GameTimer.HALF_WIDTH-600,(i*40)+10+GameTimer.HALF_HEIGHT-300,"Apple");
					this.fruits.add(f);
				}else if(MAP_MATRIX[i][j]==4) {
					Fruit f = new Fruit((j*40)+10+GameTimer.HALF_WIDTH-600,(i*40)+10+GameTimer.HALF_HEIGHT-300,"Strawberry");
					this.fruits.add(f);
				}else if(MAP_MATRIX[i][j]==5) {
					Fruit f = new Fruit((j*40)+10+GameTimer.HALF_WIDTH-600,(i*40)+10+GameTimer.HALF_HEIGHT-300,"Chico");
					this.fruits.add(f);
				}else if(MAP_MATRIX[i][j]==6) {
					Fruit f = new Fruit((j*40)+10+GameTimer.HALF_WIDTH-600,(i*40)+10+GameTimer.HALF_HEIGHT-300,"Freeze");
					this.fruits.add(f);
				}
				else {
					PacDot p = new PacDot((j*40)+10+GameTimer.HALF_WIDTH-600,(i*40)+10+GameTimer.HALF_HEIGHT-300);
					this.dots.add(p);
				}
			}
		}
	}
	public void updateGhostMove(int dx, int dy, Image img,Ghost ghost) {
		ghost.setDX(dx);
		ghost.setDY(dy);
		ghost.loadImage(img);
	}
	public void moveGhost(Ghost ghost) {
		switch (ghost.getFacing()) {
		case "UP": 
			this.updateGhostMove(0,-3,ghost.getImage(),ghost); 
//			ghost.setFrontRectangle();
			break;
		case "DOWN":
			this.updateGhostMove(0,3,ghost.getImage(),ghost);
//			ghost.setFrontRectangle();
			break;
		case "RIGHT":
			this.updateGhostMove(3,0,ghost.getImage(),ghost);
//			ghost.setFrontRectangle();
			break;
		case "LEFT":	
			this.updateGhostMove(-3,0,ghost.getImage(),ghost);
//			ghost.setFrontRectangle();
			break;
		}
	}

	public void stopGhost(Ghost g){
		g.setDX(0);
		g.setDY(0);
	}	
}	