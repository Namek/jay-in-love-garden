package net.namekdev.growing_love_garden.system;

import com.artemis.BaseSystem;
import com.artemis.managers.TagManager;

public class WorldInitSystem extends BaseSystem {
	EntityFactory factory;
	TagManager tags;

	private boolean initialized = false;
	

	private void init() {
		factory.createTree(150, 200, 200);
		factory.createTree(450, 100, 100);
		factory.createTree(420, 470, 470);
		factory.createTree(850, 400, 400);
		factory.createTree(1000, 120, 120);
		factory.createJay(150, 200);
	}

	@Override
	protected void processSystem() {
		if (!initialized) {
			init();
			initialized = true;
		}
	}
}
