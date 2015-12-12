package net.namekdev.net.growing_love_garden.system;

import net.mostlyoriginal.api.event.common.Subscribe;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.net.growing_love_garden.component.GameState;
import net.namekdev.net.growing_love_garden.enums.C;
import net.namekdev.net.growing_love_garden.enums.Tags;
import net.namekdev.net.growing_love_garden.event.LeafVacuumedEvent;

import com.artemis.BaseSystem;
import com.artemis.EntityEdit;
import com.artemis.managers.TagManager;

public class GameStateSystem extends BaseSystem {
	M<GameState> mGameState;
	TagManager tags;
	
	public GameState gameState;


	@Override
	protected void initialize() {
		EntityEdit e = world.createEntity().edit();
		gameState = e.create(GameState.class);
		gameState.loveGoal = C.Levels.Goal1;
	}

	@Override
	protected void processSystem() {
	}

	@Subscribe
	private void onLeafVacuumed(LeafVacuumedEvent evt) {
		// TODO love value depending on it's state
		gameState.collectedLove += 1;
	}
}
