package ld50;

import java.awt.event.KeyEvent;
import java.util.HashSet;

import com.albionbrown.rawge.Input;
import com.albionbrown.rawge.Renderer;
import com.albionbrown.rawge.audio.SoundClip;
import com.albionbrown.rawge.gfx.ImageTile;
import com.albionbrown.rawge.gfx.Interactable;
import com.albionbrown.rawge.gfx.InteractableSprite;
import com.albionbrown.rawge.gfx.Sprite;

public class Player extends InteractableSprite {
	
	private int speed = 2;
	private Drunk drunk;
	
	private ImageTile imageTile;
	private int imageX, imageY;
	
	private SoundClip slapClip;
	
	private AnimationState animationState;

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Player(String id, ImageTile image, int width, int height, int x, int y, Input input)
	{
		super();
		this.imageTile = image;
		this.width = width;
		this.height = height;
		this.x = x; 
		this.y = y;
		this.input = input;
		
		this.imageX = 0;
		this.imageY = 0;
		animationState = AnimationState.UP;
		
		slapClip = new SoundClip(getClass().getResourceAsStream("/audio/slap.wav"));
	}
	
	@Override
	public void update() {
		
		  boolean movedUp = false;
		  boolean movedDown = false;
		  boolean movedRight = false;
		  boolean movedLeft = false;
		
		  if (this.getInput().isKey(KeyEvent.VK_LEFT) && ((this.x - speed) >= 0)) {
			  
			  if (!wouldBeTouchingAnotherSprite(this.x - speed, this.y)) {
				  this.x = this.x - speed;
				  movedLeft = true;
			  }
		  }

		  if (this.getInput().isKey(KeyEvent.VK_RIGHT) && (((this.x + this.width) + speed) <= 1280)) {
					
			  if (!wouldBeTouchingAnotherSprite(this.x + speed, this.y)) {
				  this.x = this.x + speed;
				  movedRight = true;
			  }
		  }
		  
		  if (this.getInput().isKey(KeyEvent.VK_UP) && ((this.y - speed) >= 0)) {
				
			  if (!wouldBeTouchingAnotherSprite(this.x, this.y - speed)) {
				  this.y = this.y - speed;
				  movedUp = true;
			  }
		  }
		  
		  if (this.getInput().isKey(KeyEvent.VK_DOWN) && (((this.y + this.height) + speed) <= 960)) {
				
			  if (!wouldBeTouchingAnotherSprite(this.x, this.y + speed)) {
				  this.y = this.y + speed;
				  movedDown = true;
			  }
		  }
		  
		  if (movedUp && !(movedLeft || movedRight)) {
				
				animationState = AnimationState.UP;
			}
			
			if (movedUp && movedLeft) {
				
				animationState = AnimationState.UP_LEFT;
			}
				
			if (movedUp && movedRight) {
				
				animationState = AnimationState.UP_RIGHT;
			}

			if (movedRight && !(movedUp || movedDown)) {
				
				animationState = AnimationState.RIGHT;
			}
			
			if (movedLeft && !(movedUp || movedDown)) {
				
				animationState = AnimationState.LEFT;
			}
			
			if (movedDown && !(movedLeft || movedRight)) {
				
				animationState = AnimationState.DOWN;
			}
			
			if (movedDown && movedLeft) {
				
				animationState = AnimationState.DOWN_LEFT;
			}	
				
			if (movedDown && movedRight) {
				
				animationState = AnimationState.DOWN_RIGHT;
			}
			
			mapAnimationStateToCoordinates();
		  
		  if (isTouching(drunk)) {
			  
			  
			  if (this.getInput().isKey(KeyEvent.VK_SPACE)) {
					
				  slap();
			  }
		  }  
	}

	@Override
	public void render(Renderer r) {

		r.drawImageTile(getImageTile(), this.x, this.y, this.imageX, this.imageY);
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
	
	public ImageTile getImageTile() {
		return imageTile;
	}

	public void setImageTile(ImageTile imageTile) {
		this.imageTile = imageTile;
	}

	private void mapAnimationStateToCoordinates()
	{
		switch (animationState) {
			
			case UP:
				this.imageX = 0;
				this.imageY = 0;
				break;
				
			case DOWN:
				this.imageX = 1;
				this.imageY = 0;
				break;
				
			case RIGHT:
				this.imageX = 2;
				this.imageY = 0;
				break;
				
			case LEFT:
				this.imageX = 3;
				this.imageY = 0;
				break;
				
			case UP_RIGHT:
				this.imageX = 0;
				this.imageY = 1;
				break;
				
			case UP_LEFT:
				this.imageX = 1;
				this.imageY = 1;
				break;
				
			case DOWN_RIGHT:
				this.imageX = 2;
				this.imageY = 1;
				break;
				
			case DOWN_LEFT:
				this.imageX = 3;
				this.imageY = 1;
				break;
		}
	}
	
	private void slap() {
		drunk.setStoppedByPlayer(true);
		slapClip.play();
	}
}
