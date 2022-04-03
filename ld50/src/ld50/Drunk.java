package ld50;

import java.util.ArrayList;

import com.albionbrown.rawge.Input;
import com.albionbrown.rawge.Renderer;
import com.albionbrown.rawge.gfx.ImageTile;
import com.albionbrown.rawge.gfx.InteractableSprite;

public class Drunk extends InteractableSprite {

	private int speed = 3;
	
	private ImageTile imageTile;
	private int imageX, imageY;
	
	private int frontX, frontY;
	
	private ArrayList<Table> tables;
	private Table lastTableVisited;
	private Table nextTable;
	
	private boolean travellingToTable, reachedTable;
	
	private int waitTimeInSecs = 5;
	private long waitTime;
	private long reachedTableAt;
	private long lastUpdate;
	
	
	private final long nanoToSec = 1000000000; 
	
	private Meter drunknessMeter;
	private boolean isDrinking;
	private long lastMeterChange;
	private long meterChangeNanoSecs;
	
	private boolean stoppedByPlayer;
	private AnimationState animationState;
	
	private Beer beer;
	
	private boolean isStuck;
	private int previousX, previousY;
	private int stuckCounter;

	public Drunk(String id, ImageTile image, int width, int height, int x, int y, Input input)
	{
		this.imageTile = image;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.input = input;
		
		this.imageX = 0;
		this.imageY = 0;
		
		this.frontX = x + (this.width / 2);
		this.frontY = y;
		
		lastTableVisited = null;
		nextTable = null;
		travellingToTable = false;
		reachedTable = false;
		isDrinking = false;
		waitTime = nanoToSec * waitTimeInSecs;
		lastMeterChange = 0;
		meterChangeNanoSecs = nanoToSec * 3;
		stoppedByPlayer = false;
		animationState = AnimationState.UP;
	}
	
	@Override
	public void update() {
		
		long currentSystemTime = System.nanoTime();
		
		
		if (reachedTable) {
			
			// Wait 5 seconds
			long timer = currentSystemTime - reachedTableAt;
			if (timer > waitTime || stoppedByPlayer) {
				isDrinking = false;
				travellingToTable = false;
				reachedTable = false;
			}
			// Add a 'grace' second
			else if (timer > nanoToSec) {
				isDrinking = true;
			}
		}
		else if (!travellingToTable) {
			
			// Pick a table at random that's not the current table
			pickTable();
			travellingToTable = true;
		}
		else {
			
			if (isStuck) {
				pickTable();
				isStuck = false;
				stuckCounter = 0;
			}
			else {
				travelToTable();
			}
			
			if (((x == nextTable.getPointX() && y == nextTable.getPointY())
				|| (x + this.getWidth() == nextTable.getPointX() && y == nextTable.getPointY())
				|| (x == nextTable.getPointX() && y + this.getHeight() == nextTable.getPointY()))
					&& !reachedTable) {
				reachedTable = true;
				reachedTableAt = System.nanoTime();
				travellingToTable = false;
			}
		}
		
		if (isDrinking) {
			
			if ((currentSystemTime - lastMeterChange) > meterChangeNanoSecs) {
				drunknessMeter.increment();
				lastMeterChange = currentSystemTime;
			}
		}
		
		
		calculateAnimationDependencies();
		stoppedByPlayer = false;
		lastUpdate = currentSystemTime;
	}

	@Override
	public void render(Renderer r) {
		
		r.drawImageTile(getImageTile(), this.x, this.y, this.imageX, this.imageY);
//		r.drawImage(beerImage, frontY, frontX);
	}
	
	private void pickTable() {
		int nextTableNo = (int) ((Math.random() * (5 - 0)) + 0);
		nextTable = tables.get(nextTableNo);
		while (nextTable == lastTableVisited) {
			nextTableNo = (int) ((Math.random() * (5 - 0)) + 0);
			nextTable = tables.get(nextTableNo);
		}
		
		System.out.println(nextTableNo);
	}
	
	private void travelToTable() {
		
		boolean movedUp = false;
		boolean movedDown = false;
		boolean movedRight = false;
		boolean movedLeft = false;
		
		// Move closer to the next table
		if (x + this.getWidth() < nextTable.getPointX()) {
			
			if ((x + this.getWidth() + speed) >= nextTable.getPointX()) {
				x = nextTable.getPointX() - this.getWidth();
			}
			else if (!wouldBeTouchingAnotherSprite(this.x + speed, this.y)) {
				x = x + speed;
			}
			
			movedRight = true;
		}
		
		if (x > nextTable.getPointX()) {
			
			if ((x - speed) <= nextTable.getPointX()) {
				x = nextTable.getPointX();
			}
			else if (!wouldBeTouchingAnotherSprite(this.x - speed, this.y)) {
				x = x - speed;
			}
			
			movedLeft = true;
		}
		
		if (y + this.getHeight() < nextTable.getPointY()) {
			
			if ((y + this.getHeight() + speed) >= nextTable.getPointY()) {
				y = nextTable.getPointY() - this.getHeight();
			}
			else if (!wouldBeTouchingAnotherSprite(this.x, this.y + speed)) {
				y = y + speed;
			}
			
			movedDown = true;
		}
		
		if (y > nextTable.getPointY()) {
			if ((y - speed) <= nextTable.getPointY()) {
				y = nextTable.getPointY();
			}
			else if (!wouldBeTouchingAnotherSprite(this.x, this.y - speed)) {
				y = y - speed;
			}
			
			movedUp = true;
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
		
		if (x == previousX && y == previousY) {
			stuckCounter++;
		}
		
		if (stuckCounter > 10) {
			isStuck = true;
		}
		
		previousX = x;
		previousY = y;
	}
	
	public int getDrunkness() {
		return drunknessMeter.getLevel();
	}

	public void setDrunkness(int drunkness) {
		drunknessMeter.setLevel(drunkness);
	}

	public void setTables(ArrayList<Table> tables) {
		this.tables = tables;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void setDrunknessMeter(Meter drunknessMeter) {
		this.drunknessMeter = drunknessMeter;
	}

	@Override
	public void contactCallback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noContactCallback() {
		// TODO Auto-generated method stub
		
	}

	public void setStoppedByPlayer(boolean stoppedByPlayer) {
		this.stoppedByPlayer = stoppedByPlayer;
	}
	
	public ImageTile getImageTile() {
		return imageTile;
	}

	public void setImageTile(ImageTile imageTile) {
		this.imageTile = imageTile;
	}

	private void calculateAnimationDependencies()
	{
		switch (animationState) {
			
			case UP:
				this.imageX = 0;
				this.imageY = 0;
				this.frontX = this.x + (this.width / 2);
				this.frontY = this.y;
				break;
				
			case DOWN:
				this.imageX = 1;
				this.imageY = 0;
				this.frontX = this.x + (this.width / 2);
				this.frontY = this.y + this.height;
				break;
				
			case RIGHT:
				this.imageX = 2;
				this.imageY = 0;
				this.frontX = this.x + this.width;
				this.frontY = this.y + (this.height / 2);
				break;
				
			case LEFT:
				this.imageX = 3;
				this.imageY = 0;
				this.frontX = this.x;
				this.frontY = this.y + (this.height / 2);
				break;
				
			case UP_RIGHT:
				this.imageX = 0;
				this.imageY = 1;
				this.frontX = this.x + this.width;
				this.frontY = this.y;
				break;
				
			case UP_LEFT:
				this.imageX = 1;
				this.imageY = 1;
				this.frontX = this.x;
				this.frontY = this.y;
				break;
				
			case DOWN_RIGHT:
				this.imageX = 2;
				this.imageY = 1;
				this.frontX = this.x + this.width;
				this.frontY = this.y + this.height;
				break;
				
			case DOWN_LEFT:
				this.imageX = 3;
				this.imageY = 1;
				this.frontX = this.x;
				this.frontY = this.y + this.height;
				break;
		}
	}
	
	private class Beer {
		
	}
}
