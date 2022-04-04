package ld50;

import com.albionbrown.rawge.Renderer;
import com.albionbrown.rawge.gfx.*;

public class Meter extends Sprite {
	
	private int level;
	private int min, max;
	private int increment;
	private Font font;

	public Meter(int min, int max) {
		this.min = min;
		this.max = max;
		this.level = min;
		this.increment = 1;
		
		font = new Font();
		font.setFontImage(new Image(getClass().getResourceAsStream("/img/font_30.png")));
		font.setNumberOfCharacters(59);
		font.readImageCharacters();
	}
	
	@Override
	public void update() {
		
		// Add danger! text when too close?
	}

	@Override
	public void render(Renderer r) {

		r.drawText("Drunkness", this.font, 80, 80, 16777215);
		// Background
		r.drawSquare(0, 80, 113, (max * 2 + 4), 14);
		// Level
		r.drawSquare(2440609, 82, 115, level * 2, 10);
	}
	
	public void increment() {
		this.level = this.level + increment;
	}

	public void decrement() {
		this.level = this.level - increment;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getIncrement() {
		return increment;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}
}
