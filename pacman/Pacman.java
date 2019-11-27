package pacman;

import javafx.scene.image.Image;

public class Pacman extends Sprite{
	private int score;
	private boolean alive;
	
	private final static int PACMAN_SIZE = 30;
	public final static Image PACMAN_UP = new Image("images/PACMAN_UP.gif", PACMAN_SIZE, PACMAN_SIZE, false, false);
	public final static Image PACMAN_DOWN = new Image("images/PACMAN_DOWN.gif",PACMAN_SIZE, PACMAN_SIZE, false, false);
	public final static Image PACMAN_LEFT = new Image("images/PACMAN_LEFT.gif",PACMAN_SIZE, PACMAN_SIZE, false, false);
	public final static Image PACMAN_RIGHT = new Image("images/PACMAN_RIGHT.gif",PACMAN_SIZE, PACMAN_SIZE, false, false);
	
	public Pacman(int x, int y){
		super(x,y);
		this.score = 0;
		this.alive = true;
		this.loadImage(Pacman.PACMAN_RIGHT);
	}

	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	} 

	public void die(){
    	this.alive = false;
    }
	
	public void eat() {
		this.score += 25; //temporary value lang yung 25
	}

	public void move(){
		int newX = this.x + this.dx, newY = this.y + this.dy;
    	if(newX > 0 && newX < GameStage.WINDOW_WIDTH-50) this.x = newX;
    	if(newY > 0 && newY < GameStage.WINDOW_HEIGHT-50) this.y = newY;
	}
	
	public void updateSprite(Image img) {
		this.loadImage(img);
	}
	
	public int getScore() {
		return this.score;
	}
}
