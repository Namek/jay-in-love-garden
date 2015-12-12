package net.namekdev.net.growing_love_garden.component;

import com.artemis.PooledComponent;

public class Vacuumed extends PooledComponent {
	public float targetX;
	public float targetY;
	public float startDistance;


	@Override
	protected void reset() {
		targetX = targetY = 0;
		startDistance = 0;
	}

}
