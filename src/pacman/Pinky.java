package pacman;

import javafx.scene.image.Image;

public class Pinky extends Ghost{
	
	public Pinky(int x, int y) {
		super(x,y);
		this.speed = 0.175;
		this.GHOST_IMAGE = new Image("images/ghost3.gif",GameTimer.CELL_WIDTH, GameTimer.CELL_HEIGHT, false, false);
		this.loadImage(this.GHOST_IMAGE);
	}
}
