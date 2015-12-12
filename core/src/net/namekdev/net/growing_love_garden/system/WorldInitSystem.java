package net.namekdev.net.growing_love_garden.system;

import net.namekdev.net.growing_love_garden.component.GameState;
import net.namekdev.net.growing_love_garden.enums.C;

import com.artemis.BaseSystem;

public class WorldInitSystem extends BaseSystem {
	EntityFactory factory;

	private boolean initialized = false;
	

	private void init() {
		factory.createTree(150, 200, 0);
		factory.createTree(450, 100, 0);
		factory.createTree(420, 470, 0);
		factory.createTree(850, 400, 0);
		factory.createTree(1000, 120, 0);
		factory.createJay(150, 200);

		world.createEntity().edit()
			.create(GameState.class).loveGoal = C.Levels.Goal1;
	}

	@Override
	protected void processSystem() {
		if (!initialized) {
			init();
			initialized = true;
		}
	}
}
