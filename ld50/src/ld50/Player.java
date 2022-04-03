package ld50;

import java.awt.event.KeyEvent;

import com.albionbrown.rawge.Input;
import com.albionbrown.rawge.Renderer;
import com.albionbrown.rawge.gfx.Image;
import com.albionbrown.rawge.gfx.InteractableSprite;

public class Player extends InteractableSprite {
	
	private int speed = 3;
	private Drunk drunk;

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Player(String id, Image image, int width, int height, int x, int y, Input input)
	{
		this.image = image;
		this.width = width;
		this.height = height;
		this.x = x; 
		this.y = y;
		this.input = input;
	}
	
	@Override
	public void update() {
		
		  // Register the next move
		  if (this.getInput().isKey(KeyEvent.VK_LEFT) && ((this.x - speed) >= 0)) {
								
			  this.x = this.x - speed;
		  }
		  // If leaning right
		  if (this.getInput().isKey(KeyEvent.VK_RIGHT) && (((this.x + this.width) + speed) <= 1280)) {
								
			  this.x = this.x + speed;
		  }
		  
		  if (this.getInput().isKey(KeyEvent.VK_UP) && ((this.y - speed) >= 0)) {
				
			  this.y = this.y - speed;
		  }
		  
		  if (this.getInput().isKey(KeyEvent.VK_DOWN) && (((this.y + this.height) + speed) <= 960)) {
				
			  this.y = this.y + speed;
		  }
		  
		  if (isTouching(drunk)) {
			  
			  
			  if (this.getInput().isKey(KeyEvent.VK_SPACE)) {
					
				  drunk.setStoppedByPlayer(true);
			  }
		  }
	}

	@Override
	public void render(Renderer r) {

		// TODO Auto-generated method stub
		r.drawImage(getImage(), this.x, this.y);
	}

	@Override
	public void contactCallback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noContactCallback() {
		// TODO Auto-generated method stub
		
	}

	public void setDrunk(Drunk drunk) {
		this.drunk = drunk;
	}
}
