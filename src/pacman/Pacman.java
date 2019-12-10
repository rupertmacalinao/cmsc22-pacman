package pacman;

import javafx.scene.image.Image;

public class Pacman extends Sprite{
	private int score;
	private boolean alive;
	private int lives;
	
	public final static Image PACMAN_UP = new Image("images/PACMAN_UP.gif", GameTimer.CELL_WIDTH, GameTimer.CELL_HEIGHT, false, false);
	public final static Image PACMAN_DOWN = new Image("images/PACMAN_DOWN.gif", GameTimer.CELL_WIDTH, GameTimer.CELL_HEIGHT, false, false);
	public final static Image PACMAN_LEFT = new Image("images/PACMAN_LEFT.gif", GameTimer.CELL_WIDTH, GameTimer.CELL_HEIGHT, false, false);
	public final static Image PACMAN_RIGHT = new Image("images/PACMAN_RIGHT.gif", GameTimer.CELL_WIDTH, GameTimer.CELL_HEIGHT, false, false);
	
	public Pacman(int x, int y){
		super(x,y);
		this.score = 0;
		this.alive = true;
		this.lives = 3;
		this.speed = 0.15;
		this.loadImage(Pacman.PACMAN_RIGHT);
	}

	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	} 

	public void die(){
		System.out.println(this.x +" "+ this.y);
		this.setPxPy(this.x, this.y);
		this.setDxDy(0, 0);
		System.out.println(this.getPX()+" "+this.getPY());
    	this.lives--;
    	this.x = 15;
    	this.y = 6;
    }
	
	public void eat(int score) {
		this.score += score;
	}
	
	public void move() {
		super.move();
		if(GameTimer.GAME_MAP[this.getY()][this.getX()] == 0) this.eat(10);
		else if(GameTimer.GAME_MAP[this.getY()][this.getX()] == 2) this.eat(50);
		GameTimer.GAME_MAP[this.getY()][this.getX()] = -1;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int getLives() {
		return this.lives;
	}
	
	public void setPxPy(double px, double py) {
		this.px = px;
		this.py = py;
	}
}
