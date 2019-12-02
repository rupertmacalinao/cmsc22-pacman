package pacman;

import javafx.scene.image.Image;
import javafx.geometry.Rectangle2D;
import java.util.ArrayList;
import javafx.geometry.Point2D;

public class Pacman extends Sprite{
	private int score;
	private boolean alive;
	protected Rectangle2D front;
	protected int lives;
	protected boolean vulnerable;
	//private Point2D ur, ul, lr, ll;
	
	private final static int PACMAN_SIZE = 25;
	public final static Image PACMAN_UP = new Image("images/PACMAN_UP.gif", PACMAN_SIZE, PACMAN_SIZE, false, false);
	public final static Image PACMAN_DOWN = new Image("images/PACMAN_DOWN.gif",PACMAN_SIZE, PACMAN_SIZE, false, false);
	public final static Image PACMAN_LEFT = new Image("images/PACMAN_LEFT.gif",PACMAN_SIZE, PACMAN_SIZE, false, false);
	public final static Image PACMAN_RIGHT = new Image("images/PACMAN_RIGHT.gif",PACMAN_SIZE, PACMAN_SIZE, false, false);
	
	public Pacman(int x, int y){
		super(x,y);
		this.score = 0;
		this.alive = true;
		this.front=new Rectangle2D(0,0,0,0);
		this.loadImage(Pacman.PACMAN_RIGHT);
		this.facing="UP";
		this.lives = 1;
		this.vulnerable=true;
	}

	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	} 

	public void die(){
    	this.alive = false;
    }
	
	public void eat(PacDot p) {
		this.score += 10; 
	}
	public void eat(Fruit f) {
		this.score += 50; 
	}
	public void eat(Ghost g) {
		this.score += 200; 
	}

	public void move(){
		int newX = this.x + this.dx, newY = this.y + this.dy;
		if(newX > 0 && newX < GameStage.WINDOW_WIDTH-50) this.x=newX;
		if(newY > 0 && newY < GameStage.WINDOW_HEIGHT-50) this.y=newY;	
	}
	
	public void updateSprite(Image img) {
		this.loadImage(img);
	}
	
	public int getScore() {
		return this.score;
	}
	public void setScore(int score) {
		this.score=score;
	}
	public int getLives() {
		return this.lives;
	}
	public void decreaseLife() {
		this.lives-=1;
	}	
	public void setVulnerable() {
		this.vulnerable=true;
	}
}
