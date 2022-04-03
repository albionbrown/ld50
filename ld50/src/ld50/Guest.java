package ld50;

import com.albionbrown.rawge.Renderer;
import com.albionbrown.rawge.gfx.Image;
import com.albionbrown.rawge.gfx.InteractableSprite;

public class Guest extends InteractableSprite {

	public Guest(String id, Image image, int x, int y) {
		this.id = id;
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = image.getW();
		this.height = image.getH();
	}
	
	@Override
	public void contactCallback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noContactCallback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Renderer r) {
		// TODO Auto-generated method stub
		r.drawImage(image, x, y);
	}
}
