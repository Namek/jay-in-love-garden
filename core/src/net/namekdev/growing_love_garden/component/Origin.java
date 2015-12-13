package net.namekdev.growing_love_garden.component;

import com.artemis.PooledComponent;

/**
 * Origin in range [0, 1].
 * 
 * @author Namek
 */
public class Origin extends PooledComponent {
	public static final float DEFAULT_X = 0.5f;
	public static final float DEFAULT_Y = 0f;
	
	public float x = DEFAULT_X;
	public float y = DEFAULT_X;
	

	public void xy(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	protected void reset() {
		x = DEFAULT_X;
		y = DEFAULT_X;
	}

	
}
