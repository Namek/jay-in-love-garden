package net.namekdev.growing_love_garden.component;

import com.artemis.PooledComponent;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Renderable extends PooledComponent {
	public enum Type {
		FrameAnim,
		Sprite
	}
	
	public boolean visible = true; 
	public Type type = Type.Sprite;
	public TextureRegion sprite;
	public boolean debugFrame;

	@Override
	protected void reset() {
		visible = true;
		type = Type.Sprite;
		sprite = null;
		debugFrame = false;
	}

	public void setToSprite(TextureRegion texture) {
		this.sprite = texture;
		type = Type.Sprite;
	}

}
