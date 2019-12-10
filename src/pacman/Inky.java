package pacman;

import javafx.scene.image.Image;

public class Inky extends Ghost{
	
	public Inky(int x, int y) {
		super(x,y);
		this.speed = 0.115;
		this.GHOST_IMAGE = new Image("images/ghost2.gif",GameTimer.CELL_WIDTH, GameTimer.CELL_HEIGHT, false, false);
		this.loadImage(this.GHOST_IMAGE);
	}
}
