package pacman;

import java.util.Random;
import java.lang.Math.*;
import javafx.scene.image.Image;


public class Ghost extends Sprite{
	private boolean alive;
	private String name;
	private final static int GHOST_SIZE = 30;
	private static Image GHOST_IMG1 = new Image("images/ghost1.gif",Ghost.GHOST_SIZE,Ghost.GHOST_SIZE,false,false);
	private static Image GHOST_IMG2 = new Image("images/ghost2.gif",Ghost.GHOST_SIZE,Ghost.GHOST_SIZE,false,false);
	private static Image GHOST_IMG3 = new Image("images/ghost3.gif",Ghost.GHOST_SIZE,Ghost.GHOST_SIZE,false,false);
	private static Image GHOST_IMG4 = new Image("images/ghost4.gif",Ghost.GHOST_SIZE,Ghost.GHOST_SIZE,false,false);
	protected static Image WEAK_GHOST = new Image("images/weak.gif",Ghost.GHOST_SIZE,Ghost.GHOST_SIZE,false,false);
	protected static double G1_SPEED=1.75;
	protected static double G2_SPEED=1.5;
	protected static double G3_SPEED=1.25;
	protected static double G4_SPEED=1;
	private double speed;
	private boolean weak;
	
	public Ghost(String name,int x, int y){
		super(x,y);
		this.name=name;
		this.alive = true;
		this.facing=randomizeD();
		this.weak=false;
		switch (name) {
			case "Ghost1": this.loadImage(GHOST_IMG1); this.speed = Ghost.G1_SPEED; break;
			case "Ghost2": this.loadImage(GHOST_IMG2); this.speed = Ghost.G2_SPEED; break;
			case "Ghost3": this.loadImage(GHOST_IMG3); this.speed = Ghost.G3_SPEED;break;
			case "Ghost4": this.loadImage(GHOST_IMG4); this.speed = Ghost.G4_SPEED;break;
		}
	}

	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	} 

	public void die(){
    	this.alive = false;
    }
	
	public void attacman() {
		//Pacman buhay--;
	}

	public void move(){
		int newX = this.x + (int)(this.dx*this.speed), newY = (int)(this.y + this.dy*this.speed);
    	if(newX > 0 && newX < GameStage.WINDOW_WIDTH-50) this.x = newX;
    	if(newY > 0 && newY < GameStage.WINDOW_HEIGHT-50) this.y = newY;
	}
	public void randomizeFacing() {
		Random r = new Random();
		int randDirection = r.nextInt(4);
		switch(randDirection) {
		case 0: this.facing ="UP"; break;
		case 1: this.facing ="DOWN"; break;
		case 2: this.facing ="LEFT"; break;
		case 3: this.facing ="RIGHT"; break;
		}
	}
	public void chasePac(Pacman pac) {
		int xDifference = pac.getX()-this.getX();
		int yDifference = pac.getY()-this.getY();
		if(xDifference>=0 && Math.abs(xDifference)>Math.abs(yDifference)) this.facing="RIGHT";
		if(xDifference<=0 && Math.abs(xDifference)>Math.abs(yDifference)) this.facing="LEFT";
		if(yDifference>=0 && Math.abs(xDifference)<Math.abs(yDifference)) this.facing="DOWN";
		if(xDifference>=0 && Math.abs(xDifference)<Math.abs(yDifference)) this.facing="UP";
	}
	
	public static String randomizeD() {
		Random r = new Random();
		int randDirection = r.nextInt(4);
		switch(randDirection) {
		case 0: return "UP"; 
		case 1: return "DOWN";
		case 2: return "LEFT"; 
		case 3: return "RIGHT"; 
		}
		return null;
	}
	public String getName() {
		return this.name;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public void setWeak(Boolean b) {
		this.weak=b;
		if (b==false) {
			switch (name) {
			case "Ghost1": this.speed = Ghost.G1_SPEED; break;
			case "Ghost2": this.speed = Ghost.G2_SPEED; break;
			case "Ghost3": this.speed = Ghost.G3_SPEED; break;
			case "Ghost4": this.speed = Ghost.G4_SPEED; break;
		}
		}
	}
	public boolean isWeak() {
		return this.weak;
	}

}
