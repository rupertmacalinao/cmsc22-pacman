package pacman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.scene.image.Image;

public class Ghost extends Sprite{
	private boolean alive;
	private int origX, origY;
	protected final static int GHOST_SIZE = 25;
	public Image GHOST_IMAGE;
	
	public Ghost(int x, int y){
		super(x,y);
		this.origX = x;
		this.origY = y;
		this.alive = true;
		
		Random r = new Random();
		switch(r.nextInt(2)) {
			case 0: this.dx = this.speed; break;
			case 1: this.dy = this.speed; break;
		}
	}

	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	} 

	public void die(){
    	this.alive = false;
    }

	public void move(){
		double newX = this.x + this.dx, newY = this.y + this.dy;
		
		if(newX > 0 && newX < GameTimer.MAP_LENGTH-1 && !isNextPosWall((int) this.y, (int) newX)) {
			this.px = this.x;
			this.x += this.dx;
		}
		if(newY > 0 && newY < GameTimer.MAP_HEIGHT-1 && !isNextPosWall((int) newY, (int) this.x)) {
			this.py = this.y;
			this.y += this.dy;
		}
		
		//if(isNextPosWall((int) (this.y + this.dy), (int) (this.x + this.dx))) this.chase();
	}
	
	public void switchDirection() {
		Random r = new Random();
		int next = r.nextInt(4);
		
		switch(next) {
			case 0: this.moveLeft(this.GHOST_IMAGE); break;
			case 1: this.moveRight(this.GHOST_IMAGE); break;
			case 2: this.moveUp(this.GHOST_IMAGE); break;
			case 3: this.moveDown(this.GHOST_IMAGE); break;
		}
	}
	
	public void resetPosition() {
		this.px = this.x;
		this.py = this.y;
		this.x = this.origX;
		this.y = this.origY;
	}
	
	public boolean isPosIntersection() {
		try {
			if(this.dy != 0)
				if(GameTimer.GAME_MAP[this.getY()][this.getX()-1] != 1 || GameTimer.GAME_MAP[this.getY()][this.getX()+1] != 1) return true;
			
			if(this.dx != 0)
				if(GameTimer.GAME_MAP[this.getY()-1][this.getX()] != 1 || GameTimer.GAME_MAP[this.getY()+1][this.getX()] != 1) return true;
		}catch(Exception e) {}
		
		return false;
	}
	
	public void chase(int pacX, int pacY) {
		this.move();
		
		//Change Direction if next position is wall or current position is an intersection
		if(isNextPosWall((int) (this.y + this.dy), (int) (this.x + this.dx)) || isPosIntersection()) {
			double x = this.x - pacX, y = this.y - pacY;
			
			//Pythagorean Theorem: a^2 + b^2 = c^2 (Gets distances between pac and ghost)
			double up = ((y-1)*(y-1)) + (x*x);
			double down = ((y+1)*(y+1)) + (x*x);
			double left = ((x-1)*(x-1)) + (y*y);
			double right = ((x+1)*(x+1)) + (y*y);
			
			//If ghost is moving left/right, change direction to up/down
			if(this.dx != 0) {
				if(up < down || isNextPosWall((int) (this.y + this.speed), (int) this.x)) this.moveUp(this.GHOST_IMAGE);
				else this.moveDown(this.GHOST_IMAGE);
			}
			
			//If ghost is moving up/down, change direction to left/right
			else if(this.dy != 0) {
				if(left < right || isNextPosWall((int) this.y, (int) (this.x + this.speed))) this.moveLeft(this.GHOST_IMAGE);
				else this.moveRight(this.GHOST_IMAGE);
			}
		}
		
	}
	
}
