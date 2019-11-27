package pacman;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameMenu extends Parent{
	Stage stage;
	Group root;
	GameTimer gametimer;
	Canvas canvas;
	GraphicsContext gc;
	
	public static final int MENU_FONTSIZE = 55;
	public static final int MENU_RWIDTH = 400;
	public static final int MENU_RHEIGHT = 65;
	public static final int MENU_YOFFSET = -10;
	public static final Image backgroundIMG = newImageBackground("images/Main Menu.png");
	public static final Image aboutIMG = newImageBackground("images/About.png");
	public static final Image instructionsIMG = newImageBackground("images/Instructions.png");
	
	public Parent createContent(Stage stage) {
		this.stage = stage;
		this.root = new Group();
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);	
		this.gc = canvas.getGraphicsContext2D();
		this.changeBackground(root, backgroundIMG);
		this.addMenu();
		
		return this.root;
	}
	
	public void addMenu() {
		MenuItem start = new MenuItem("START", MENU_RHEIGHT, MENU_RWIDTH, MENU_FONTSIZE, 0, MENU_YOFFSET);
		MenuItem exit = new MenuItem("EXIT", MENU_RHEIGHT, MENU_RWIDTH, MENU_FONTSIZE, 0, MENU_YOFFSET);
		MenuItem about = this.newToggleMenu("ABOUT", aboutIMG);
		MenuItem instructions = this.newToggleMenu("HOW TO PLAY", instructionsIMG);
		
		start.setOnMouseClicked(event -> this.loadGame());
		exit.setOnMouseClicked(event -> System.exit(0));
			
		//Compiles menu items
		MenuBox menu = new MenuBox(start, about, instructions, exit);
		menu.setSpacing(7);
		menu.setTranslateX(GameStage.WINDOW_WIDTH*0.33);
		menu.setTranslateY(GameStage.WINDOW_HEIGHT*0.4);
		this.root.getChildren().add(menu);
	}
	
	public void loadGame() {
		Group gameroot = new Group();
		this.changeBackground(gameroot, GameStage.MAP_IMAGE);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc = this.canvas.getGraphicsContext2D();
		gameroot.getChildren().add(canvas);
		Scene gameScene = new Scene(gameroot, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT,Color.BLACK);
		this.gametimer = new GameTimer(this.gc, gameScene);
		this.stage.setScene(gameScene);
		this.gametimer.start();
		this.stage.show();
	}
	
	//The remaining lines of code are intended for menu effects
	//They do not affect the game in any way
	
	public static void menuFade(ImageView img, int start, int end) {
		FadeTransition ft = new FadeTransition(Duration.millis(300), img);
		ft.setFromValue(start);
	    ft.setToValue(end);
	    ft.play();
	}
	
	public static Image newImageBackground(String directory) {
		return new Image(directory, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT, false, false);
	}
	
	public ImageView changeBackground(Group root, Image img) {
		ImageView background = new ImageView(img);
		background.setFitWidth(GameStage.WINDOW_WIDTH);
		background.setFitHeight(GameStage.WINDOW_HEIGHT-10);
		root.getChildren().add(background);
	    menuFade(background, 0, 1);
		
		return background;
	}
	
	public MenuItem newToggleMenu(String name, Image img) {
		MenuItem menu = new MenuItem(name, MENU_RHEIGHT, MENU_RWIDTH, MENU_FONTSIZE, 0, MENU_YOFFSET);
		
		menu.setOnMouseClicked(event -> {
			ImageView menuBackground = this.changeBackground(this.root, img);
			MenuItem back = new MenuItem("BACK", MENU_RHEIGHT, MENU_RWIDTH, 30, 0, -5);
			back.setTranslateX(GameStage.WINDOW_WIDTH*0.75);
			back.setTranslateY(GameStage.WINDOW_HEIGHT*0.06);
			
			this.root.getChildren().add(back);
			back.setOnMouseClicked(event2 -> {
				menuFade(menuBackground, 1, 0);
				this.root.getChildren().remove(menuBackground);
				this.root.getChildren().remove(back);
			});
		});
		
		return menu;
	}
	
	private static class MenuBox extends VBox{
		public MenuBox(MenuItem...items) {
			for(MenuItem item : items)
				getChildren().addAll(item, createSeparator());
		}
		
		private Line createSeparator(){
			Line sep = new Line();
			sep.setEndX(400);
			return new Line();
		}
	}
	
	private static class MenuItem extends StackPane{
		private final static LinearGradient menuGradient = new LinearGradient(0,0,1,0,true,CycleMethod.NO_CYCLE, new Stop[]{
				new Stop(0, Color.DARKSLATEGRAY),
				new Stop(0.2, Color.WHITE),
				new Stop(0.8, Color.WHITE),
				new Stop(1, Color.DARKSLATEGRAY)
		});
		
		private final static DropShadow menuDSY = new DropShadow(15, Color.YELLOW);
		private final static DropShadow menuDSW = new DropShadow(15, Color.WHITE);
		
		public MenuItem(String name, int rHeight, int rWidth, int size, int xOffset, int yOffset) {
			
			Rectangle menu = new Rectangle(rWidth, rHeight);
			menu.setOpacity(0.01);
			menuDSY.setInput(new Glow());
			menuDSW.setInput(new Glow());
			
			Text text = new Text(name);
			text.setFill(Color.WHITE);
			text.setFont(Font.loadFont("file:src/fonts/Pixellari.ttf", size));
			
			setAlignment(Pos.CENTER);
			getChildren().addAll(menu,text);
			
			setOnMouseEntered(event -> {
				menuMouseEventDesign(menu, text, menuGradient, Color.WHITE, menuDSW, xOffset, yOffset);
			});
			
			setOnMouseExited(event -> {
				menuMouseEventDesign(menu, text, Color.TRANSPARENT, Color.WHITE, null, 0, 0);
			});
			
			setOnMousePressed(event -> {
				menuMouseEventDesign(menu, text, menuGradient, Color.YELLOW, menuDSY, xOffset, yOffset);
			});
		}
		
		public static void menuMouseEventDesign(Rectangle menu, Text txt, Paint mFill, Color txtFill, Effect e, int x, int y) {
			menu.setFill(mFill);
			txt.setFill(txtFill);
			menu.setTranslateX(x);
			txt.setTranslateX(x);
			menu.setTranslateY(y);
			txt.setTranslateY(y);
			menu.setEffect(e);
			txt.setEffect(e);
		}
	}
}
