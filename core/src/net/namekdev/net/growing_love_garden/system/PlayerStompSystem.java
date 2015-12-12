package net.namekdev.net.growing_love_garden.system;

import net.mostlyoriginal.api.event.common.Subscribe;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.net.growing_love_garden.component.Jay;
import net.namekdev.net.growing_love_garden.component.LoveLeaf;
import net.namekdev.net.growing_love_garden.component.Stomp;
import net.namekdev.net.growing_love_garden.enums.C;
import net.namekdev.net.growing_love_garden.enums.LeafLifeStadium;
import net.namekdev.net.growing_love_garden.enums.LeafPositionState;
import net.namekdev.net.growing_love_garden.event.LeafVacuumedEvent;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class PlayerStompSystem extends EntityProcessingSystem {
	M<LoveLeaf> mLeaf;
	M<Stomp> mStomp;
	
	CameraSystem cameraSystem;
	LeafLifeSystem leafLifeSystem;
	LeafVacuumSystem leafVacuum;
	PlayerStateSystem playerState;


	public PlayerStompSystem() {
		super(Aspect.all(Stomp.class));
	}

	@Override
	protected void process(Entity e) {
		Stomp stomp = mStomp.get(e);
		stomp.progress += world.getDelta() / C.Player.StompDuration;
		
		if (stomp.progress >= 1f) {
			mStomp.remove(e);
			return;
		}

		final int treeId = playerState.findCloseTree();
		
		if (treeId >= 0) {
			cameraSystem.shake(C.Player.StompDuration);
			Timer.schedule(new Task() {
				@Override
				public void run() {
					IntBag leafs = leafLifeSystem.detachLeafs(treeId);
					collectLeafs(leafs);
				}
			}, C.World.TimeToDetachLeafs);
		}
	}
	
	private void collectLeafs(IntBag leafs) {
		for (int i = 0, n = leafs.size(); i < n; ++i) {
			int leafId = leafs.get(i);
			leafVacuum.vacuum(leafId);
		}
	}
	
	@Subscribe
	private void onLeafVacuumed(LeafVacuumedEvent evt) {
		LoveLeaf leaf = mLeaf.get(evt.leafId);
		leaf.state = LeafPositionState.Lying;
		leaf.stadium = LeafLifeStadium.None;
	}
}
