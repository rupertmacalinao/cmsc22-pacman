package pacman;

import javafx.scene.image.Image;
import javafx.stage.Screen;

public class GameStage {
	public final static double WINDOW_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
	public final static double WINDOW_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
	
	public final static Image MAP_IMAGE = new Image("images/map.png", GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT, false, false);
}