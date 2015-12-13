package net.namekdev.growing_love_garden.component;

import com.artemis.PooledComponent;

public class Stomp extends PooledComponent {
	public float progress = 0;

	@Override
	protected void reset() {
		progress = 0;
	}

}
