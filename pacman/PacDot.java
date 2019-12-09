package pacman;

import javafx.scene.image.Image;

public class PacDot extends Sprite{
	private boolean alive;
	
	private final static int PACDOT_SIZE = 10;
	public static Image PACDOT_IMG = new Image("images/pacdot2.png", GameTimer.CELL_WIDTH/3, GameTimer.CELL_HEIGHT/3, false, false);
	public static Image BPACDOT_IMG = new Image("images/cherry.png", GameTimer.CELL_WIDTH, GameTimer.CELL_HEIGHT,false,false);
	public PacDot(int x, int y){
		super(x,y);
		this.alive = true;
		this.loadImage(PacDot.PACDOT_IMG);
	}

	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	} 

	public void die(){
    	this.alive = false;
    }
}
