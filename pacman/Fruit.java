package pacman;

import javafx.scene.image.Image;

public class Fruit extends Sprite{
	private boolean alive;
	private String type;
	
	private final static int FRUIT_SIZE = 30;
	public final static Image CHERRY_IMG = new Image("images/cherry.png", FRUIT_SIZE, FRUIT_SIZE, false, false);
	public final static Image APPLE_IMG = new Image("images/apple.png", FRUIT_SIZE, FRUIT_SIZE, false, false);
	public final static Image STRAWBERRY_IMG = new Image("images/strawberry.png", FRUIT_SIZE, FRUIT_SIZE, false, false);
	public final static Image CHICO_IMG = new Image("images/chico.png", FRUIT_SIZE, FRUIT_SIZE, false, false);
	public final static Image FREEZE_IMG = new Image("images/freeze.png", FRUIT_SIZE, FRUIT_SIZE, false, false);
	
	public Fruit(int x, int y, String type){
		super(x,y);
		this.alive = true;
		this.type=type;
		switch(type){
			case "Cherry": this.loadImage(Fruit.CHERRY_IMG); break;
			case "Apple": this.loadImage(Fruit.APPLE_IMG); break;
			case "Strawberry": this.loadImage(Fruit.STRAWBERRY_IMG); break;
			case "Chico": this.loadImage(Fruit.CHICO_IMG); break;
			case "Freeze": this.loadImage(Fruit.FREEZE_IMG); break;
		}
		
	}

	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	} 

	public void die(){
    	this.alive = false;
    	this.x=0;
    	this.y=0;
    }
	public String getType() {
		return this.type;
	}
}