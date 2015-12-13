package net.namekdev.growing_love_garden.component;

import com.artemis.PooledComponent;

public class Scale extends PooledComponent {
	public float x = 1;
	public float y = 1;

	@Override
	protected void reset() {
		x = y = 1;
	}

	public Scale xy(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}
}
