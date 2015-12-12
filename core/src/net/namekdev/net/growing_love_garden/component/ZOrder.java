package net.namekdev.net.growing_love_garden.component;

import com.artemis.PooledComponent;

public class ZOrder extends PooledComponent {
	public int z = 0;

	@Override
	protected void reset() {
		z = 0;
	}
}
