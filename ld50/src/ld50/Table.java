package ld50;

import com.albionbrown.rawge.Renderer;
import com.albionbrown.rawge.gfx.Sprite;

public class Table extends Sprite {

	private int height, width;
	
	private int pointX, pointY;
	
	public int getPointX() {
		return pointX;
	}

	public void setPointX(int pointX) {
		this.pointX = pointX;
	}

	public int getPointY() {
		return pointY;
	}

	public void setPointY(int pointY) {
		this.pointY = pointY;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Renderer r) {
		// TODO Auto-generated method stub
		r.drawSquare(8421504, x, y, width, height);
	}

}
