package net.namekdev.net.growing_love_garden.component;

import com.artemis.PooledComponent;

public class PosChild extends PooledComponent {
	/** Parent entity id. */
	public int parent = -1;
	
	@Override
	protected void reset() {
		parent = -1;
	}
}
