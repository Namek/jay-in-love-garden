package net.namekdev.growing_love_garden.system;

import static net.mostlyoriginal.api.operation.OperationFactory.*;
import static net.namekdev.growing_love_garden.utils.operation.OperationFactory2.*;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.event.common.Subscribe;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.growing_love_garden.component.LoveLeaf;
import net.namekdev.growing_love_garden.enums.C;
import net.namekdev.growing_love_garden.enums.LeafLifeStadium;
import net.namekdev.growing_love_garden.enums.LeafPositionState;
import net.namekdev.growing_love_garden.event.LeafVacuumedEvent;
import net.namekdev.growing_love_garden.utils.Utils;
import net.namekdev.growing_love_garden.utils.Utils.IntBagPredicate;

import com.artemis.BaseSystem;
import com.artemis.utils.IntBag;

public class PlayerStompSystem extends BaseSystem {
	M<LoveLeaf> mLeaf;
	M<Schedule> mSchedule;
	
	CameraSystem cameraSystem;
	GameStateSystem gameState;
	LeafLifeSystem leafLifeSystem;
	LeafVacuumSystem leafVacuum;
	PlayerStateSystem playerState;
	SchedulerSystem scheduler;
	
	float stompProgress = 0;
	boolean stompBlocked = false;


	@Override
	protected void processSystem() {
		if (stompProgress >= 1f) {
			return;
		}
			
		stompProgress += world.getDelta() / C.Player.StompDuration;
	}

	public void tryStomp() {
		if (stompBlocked) {
			return;
		}
		
		final int treeId = playerState.findCloseTree();
		
		if (treeId < 0) {
			return;
		}

		stompBlocked = true;
		stompProgress = 0;
		cameraSystem.shake(C.Player.StompDuration);

		scheduler.schedule(
			delayedOperation(C.World.TimeToDetachLeafs, new Runnable() {
				public void run() {
					IntBag leafs = leafLifeSystem.detachLeafsOrMakeOlder(treeId);
					IntBag valuableLeafs = Utils.filterBag(leafs, new IntBagPredicate() {
						public boolean apply(int e) {
							return gameState.valueLeaf(e) > 0;
						}
					});
					collectLeafs(valuableLeafs);
				}
			})
		);
		
		// Block stomp for some time
		scheduler.schedule(
			delayedOperation(C.Player.StompCooldown, new Runnable() {
				public void run() {
					stompBlocked = false;
				}
			})
		);
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
