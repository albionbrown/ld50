package ld50;

import com.albionbrown.rawge.Renderer;
import com.albionbrown.rawge.gfx.Font;
import com.albionbrown.rawge.gfx.Image;

public class Clock {

	private Font font;
	
	private int hour;
	private int minute;
	
	private long lastUpdate;
	
	private final long nanosInSec = 1000000000;
	
	private int x, y;
	
	private boolean stop;
	
	public Clock() {
		this.hour = 18;
		this.minute = 30;
		
		font = new Font();
		font.setFontImage(new Image(getClass().getResourceAsStream("/img/font_50.png")));
		font.setNumberOfCharacters(59);
		font.readImageCharacters();
		
		x = 50;
		y = 100;
		
		stop = false;
	}
	
	public void update() {
		
		long currentTime = System.nanoTime();
		
		// Has 1 second passed?
		if ((currentTime - lastUpdate) > nanosInSec && !stop) {
			increment();
			lastUpdate = System.nanoTime();
		}
	}
	
	private void increment() {
		
		if (minute == 59) {
			minute = 0;
			
			if (hour == 23) {
				hour = 0;
			}
			else {
				hour++;
			}
		}
		else {
			minute++;
		}
	}
	
	public void render(Renderer r) {
		
		StringBuilder time = new StringBuilder();
		
		if (hour < 10) {
			time.append("0");
		}
		
		time.append(String.valueOf(hour));
		time.append(":");
		
		if (minute < 10) {
			time.append("0");
		}
		
		time.append(String.valueOf(minute));
		
		r.drawSquare(0, x - 5, y - 5, 115, 70);
		r.drawText(time.toString(), font, x, y, 16777215);
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void stop() {
		this.stop = true;
	}
}
