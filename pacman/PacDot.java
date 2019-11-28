package pacman;

import javafx.scene.image.Image;

public class PacDot extends Sprite{
	private boolean alive;
	
	private final static int PACDOT_SIZE = 10;
	public final static Image PACDOT_IMG = new Image("images/PACMAN_UP.gif", PACDOT_SIZE, PACDOT_SIZE, false, false);
	
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