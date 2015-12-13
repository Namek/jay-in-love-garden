package net.namekdev.growing_love_garden.system;

import net.mostlyoriginal.api.event.common.Subscribe;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.growing_love_garden.component.GameState;
import net.namekdev.growing_love_garden.component.LoveLeaf;
import net.namekdev.growing_love_garden.enums.C;
import net.namekdev.growing_love_garden.enums.LeafLifeStadium;
import net.namekdev.growing_love_garden.event.LeafVacuumedEvent;

import com.artemis.BaseSystem;
import com.artemis.EntityEdit;
import com.artemis.managers.TagManager;

public class GameStateSystem extends BaseSystem {
	M<GameState> mGameState;
	M<LoveLeaf> mLeaf;

	TagManager tags;

	public GameState gameState;


	@Override
	protected void initialize() {
		EntityEdit e = world.createEntity().edit();
		gameState = e.create(GameState.class);
		gameState.loveGoal = C.Levels.Goal[gameState.levelIndex];
	}

	@Override
	protected void processSystem() {
		float dt = world.getDelta();
		gameState.yearProgress += dt * C.Levels.YearProgressingSpeed[gameState.levelIndex];
	}

	public int valueLeaf(LoveLeaf leaf) {
		if (leaf.stadium == LeafLifeStadium.Bigger) {
			return C.Leaf.Value.Bigger;
		}
		else if (leaf.stadium == LeafLifeStadium.GettingYellow) {
			return C.Leaf.Value.GettingYellow;
		}

		return 0;
	}
	
	public int valueLeaf(int leafId) {
		return valueLeaf(mLeaf.get(leafId));
	}

	@Subscribe
	private void onLeafVacuumed(LeafVacuumedEvent evt) {
		int value = valueLeaf(evt.leafId);
		gameState.collectedLove += value;
	}
}
