package net.namekdev.net.growing_love_garden.component;

import com.artemis.PooledComponent;

/**
 * {@link #x} is for center, {@link #y} is for bottom.
 */
public class Pos extends PooledComponent {
	public float x, y;

	@Override
	protected void reset() {
		x = y = 0;
	}

	public Pos xy(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}
}
