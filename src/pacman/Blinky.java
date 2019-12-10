package pacman;

import javafx.scene.image.Image;

public class Blinky extends Ghost{
	
	public Blinky(int x, int y) {
		super(x,y);
		this.GHOST_IMAGE = new Image("images/ghost1.gif",GameTimer.CELL_WIDTH, GameTimer.CELL_HEIGHT, false, false);
		this.loadImage(this.GHOST_IMAGE);
		this.speed = 0.12;
	}
}
