package pacman;

import javafx.scene.image.Image;

public class Sprite {
	protected Image img;
	protected double x, y, dx, dy, px, py;
	protected boolean visible;
	protected double width;
	protected double height;
	protected double speed;
	
	public Sprite(int x, int y){
		this.x = this.px = x;
		this.y = this.py = y;
		this.speed = 0.125;
		this.dx = 0;
		this.dy = 0;
		this.visible = true;
	}
	
	//method to set the object's image
	protected void loadImage(Image img){
		try{
			this.img = img;
	        this.setSize();
		} catch(Exception e){}
	}
	
	//method to set the object's width and height properties
	private void setSize(){
		this.width = this.img.getWidth();
	    this.height = this.img.getHeight();
	}
	
	//method to return the image
	Image getImage(){
		return this.img;
	}
	//getters
	public int getX() {
    	return (int) this.x;
	}

	public int getY() {
    	return (int) this.y;
	}
	
	public int getPX() {
		return (int) this.px;
	}
	
	public int getPY() {
		return (int) this.py;
	}
	
	public double getDX() {
		return this.dx;
	}
	
	public double getDY() {
		return this.dy;
	}
	
	public void setWidth(double val){
		this.width = val;
	}

	public void setHeight(double val){
		this.height = val;
	}
	
	public void move(){
		double newX = this.x + this.dx, newY = this.y + this.dy;
		
		if(newX > 0 && newX < GameTimer.MAP_LENGTH && !isNextPosWall((int) this.y, (int) newX)) {
			this.px = this.x;
			this.x += this.dx;
		}
		if(newY > 0 && newY < GameTimer.MAP_HEIGHT && !isNextPosWall((int)newY, (int)this.x)) {
			this.py = this.y;
			this.y += this.dy;
		}
	}
	
	public void setDxDy(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void moveLeft(Image img) {
		this.loadImage(img);
		this.setDxDy(-this.speed, 0);
	}
	
	public void moveRight(Image img) {
		this.loadImage(img);
		this.setDxDy(this.speed, 0);
	}
	
	public void moveUp(Image img) {
		this.loadImage(img);
		this.setDxDy(0, -this.speed);
	}
	
	public void moveDown(Image img) {
		this.loadImage(img);
		this.setDxDy(0, this.speed);
	}
	
	public boolean isNextPosWall(int row, int col) {
		try{if(GameTimer.GAME_MAP[row][col] == 1) return true;} catch(Exception e) {}
		return false;
	}
}