package pacman;

import javafx.scene.image.Image;

public class Clyde extends Ghost{
	
	public Clyde(int x, int y) {
		super(x,y);
		this.speed = 0.1;
		this.GHOST_IMAGE = new Image("images/ghost4.gif",GameTimer.CELL_WIDTH, GameTimer.CELL_HEIGHT, false, false);
		this.loadImage(this.GHOST_IMAGE);
	}
}
