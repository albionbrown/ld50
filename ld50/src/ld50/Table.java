package ld50;

import com.albionbrown.rawge.Renderer;
import com.albionbrown.rawge.gfx.Image;
import com.albionbrown.rawge.gfx.InteractableSprite;

public class Table extends InteractableSprite {

	private int height, width;
	
	private int pointX, pointY;
	
	public Table(String id) {
		this.id = id;
	}
	
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
		if (this.image != null) {
			r.drawImage(image, x, y);
		}
		else {
			r.drawSquare(8421504, x, y, width, height);
		}
	}

	@Override
	public void contactCallback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noContactCallback() {
		// TODO Auto-generated method stub
		
	}

	public void setImage(Image image) {
		this.image = image;
	}	
}
