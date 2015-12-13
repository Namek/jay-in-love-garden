package net.namekdev.growing_love_garden.component;

import com.artemis.PooledComponent;

public class Rotation extends PooledComponent {
	public float rotation;

	@Override
	protected void reset() {
		rotation = 0;
	}
}
