package pacman;

import javafx.scene.image.Image;

public class Wall extends Sprite{	
	private final static int WALL_SIZE = 40;
	public final static Image WALL_IMG = new Image("images/wall.png", WALL_SIZE, WALL_SIZE, false, false);
	
	public Wall(int x, int y){
		super(x,y);
		this.loadImage(Wall.WALL_IMG);
	}
}
