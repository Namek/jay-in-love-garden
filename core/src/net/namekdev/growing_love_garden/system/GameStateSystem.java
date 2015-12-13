package net.namekdev.growing_love_garden.system;

import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.growing_love_garden.component.GameState;
import net.namekdev.growing_love_garden.component.LoveLeaf;
import net.namekdev.growing_love_garden.enums.C;
import net.namekdev.growing_love_garden.enums.LeafLifeStadium;
import net.namekdev.growing_love_garden.event.LeafVacuumedEvent;
import net.namekdev.growing_love_garden.event.LostGameEvent;
import net.namekdev.growing_love_garden.event.WonLevelEvent;

import com.artemis.BaseSystem;
import com.artemis.EntityEdit;
import com.artemis.managers.TagManager;

public class GameStateSystem extends BaseSystem {
	M<GameState> mGameState;
	M<LoveLeaf> mLeaf;

	EventSystem events;
	LeafLifeSystem leafLifeSystem;
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

		// Year has passed, notify
		if (gameState.yearProgress >= 1f) {
			gameState.yearProgress = 1f;

			if (gameState.isThereEnoughLove()) {
				events.dispatch(new WonLevelEvent(gameState));
			}
			else {
				events.dispatch(new LostGameEvent(gameState));
			}
		}
		
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
	
	public void setNextLevel() {
		int level = ++gameState.levelIndex;
		gameState.totalCollectedLove += gameState.getBonus();
		gameState.collectedLove = 0;
		gameState.loveGoal = C.Levels.Goal[level];
		gameState.yearProgress = 0;
		
		leafLifeSystem.resetAllLeafs();
	}

	public void setFirstLevel() {
		gameState.reset();
		gameState.loveGoal = C.Levels.Goal[gameState.levelIndex];

		leafLifeSystem.resetAllLeafs();
	}

	@Subscribe
	private void onLeafVacuumed(LeafVacuumedEvent evt) {
		int value = valueLeaf(evt.leafId);
		gameState.collectedLove += value;
	}
}
