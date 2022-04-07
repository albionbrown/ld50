package ld50;

import com.albionbrown.rawge.Renderer;
import com.albionbrown.rawge.gfx.Image;
import com.albionbrown.rawge.gfx.ImageTile;

public class WanderingGuest extends Guest {
	
	private ImageTile imageTile;
	private int imageX, imageY;
	
	private int targetX, targetY;
	
	private int boundaryTopLeftX = 230;
	private int boundaryTopLeftY  = 230;
	private int boundaryBottomRightX = 1050;
	private int boundaryBottomRightY = 900;
	
	private long waitTime = 1000000000 * 5;
	private long reachedTargetAt;
	
	private boolean reachedTarget;
	
	private final int speed = 1;
	
	private AnimationState animationState;

	public WanderingGuest(ImageTile image, int x, int y) {
		super("", image, x, y);
		this.imageTile = image;

		this.width = 80;
		this.height = 80;
		
		reachedTarget = true;
		reachedTargetAt = 0;
		animationState = AnimationState.UP;
	}
	
	public void update() {
		
		if (!reachedTarget) {
			
			travelToTarget();
		}
		else {
			
			// Wait a few seconds
			long currentSystemTime = System.nanoTime();
			long timer = currentSystemTime - reachedTargetAt;
			
			if (timer > waitTime) {
				setTarget();
				reachedTargetAt = 0;
				reachedTarget = false;
			}
		}
	}
	
	private void setTarget() {
		
		targetX = (int) ((Math.random() * (boundaryBottomRightX - boundaryTopLeftX)) + boundaryTopLeftX);
		targetY = (int) ((Math.random() * (boundaryBottomRightY - boundaryTopLeftY)) + boundaryTopLeftY);
	}
	
	private void travelToTarget() {
		
		boolean movedUp = false;
		boolean movedDown = false;
		boolean movedRight = false;
		boolean movedLeft = false;
		
		// Is left of
		if (x + this.getWidth() < targetX) {
			
			if ((x + this.getWidth() + speed) >= targetX) {
				x = targetX - this.getWidth();
			}
			else if (!wouldBeTouchingAnotherSprite(this.x + speed, this.y)) {
				x = x + speed;
			}
			
			movedRight = true;
		}
		// Is Right of
		if (x > targetX) {
			
			if ((x - speed) <= targetX) {
				x = targetX;
			}
			else if (!wouldBeTouchingAnotherSprite(this.x - speed, this.y)) {
				x = x - speed;
			}
			
			movedLeft = true;
		}
		// Is above
		if (y + this.getHeight() < targetY) {
			
			if ((y + this.getHeight() + speed) >= targetY) {
				y = targetY - this.getHeight();
			}
			else if (!wouldBeTouchingAnotherSprite(this.x, this.y + speed)) {
				y = y + speed;
			}
			
			movedDown = true;
		}
		// Is below
		if (y > targetY) {
			if ((y - speed) <= targetY) {
				y = targetY;
			}
			else if (!wouldBeTouchingAnotherSprite(this.x, this.y - speed)) {
				y = y - speed;
			}
			
			movedUp = true;
		}
		
		if (!movedRight
			&& !movedLeft
			&& !movedDown
			&& !movedUp) {
			
			reachedTargetAt = System.nanoTime();
			reachedTarget = true;
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
		
		calculateAnimationDependencies();
//		
//		if (x == previousX && y == previousY) {
//			stuckCounter++;
//		}
//		
//		if (stuckCounter > 10) {
//			isStuck = true;
//		}
//		
//		previousX = x;
//		previousY = y;
	}
	
	private void calculateAnimationDependencies()
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
	
	public void render(Renderer r) {
		r.drawImageTile(imageTile, this.x, this.y, this.imageX, this.imageY);
	}
}
