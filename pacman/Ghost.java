package pacman;

import javafx.scene.image.Image;

public class Ghost extends Sprite{
	private boolean alive;
	
	private final static int GHOST_SIZE = 50;
	private Image ghostImg;
	
	public Ghost(int x, int y, Image ghostImg){
		super(x,y);
		this.alive = true;
		this.ghostImg = ghostImg;
		this.loadImage(ghostImg);
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
		int newX = this.x + this.dx, newY = this.y + this.dy;
    	if(newX > 0 && newX < GameStage.WINDOW_WIDTH-50) this.x = newX;
    	if(newY > 0 && newY < GameStage.WINDOW_HEIGHT-50) this.y = newY;
	}
}
