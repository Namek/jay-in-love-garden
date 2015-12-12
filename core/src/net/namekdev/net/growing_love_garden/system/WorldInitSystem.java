package net.namekdev.net.growing_love_garden.system;

import com.artemis.BaseSystem;

public class WorldInitSystem extends BaseSystem {
	EntityFactory factory;

	private boolean initialized = false;
	

	private void init() {
		// create: some trees, Jay
		factory.createTree(150, 200, 0);
	}

	@Override
	protected void processSystem() {
		if (!initialized) {
			init();
			initialized = true;
		}
	}
}
