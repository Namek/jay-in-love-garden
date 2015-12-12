package net.namekdev.net.growing_love_garden.system;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.net.growing_love_garden.component.Jay;
import net.namekdev.net.growing_love_garden.component.Stomp;
import net.namekdev.net.growing_love_garden.enums.C;

import com.artemis.Aspect;
import com.artemis.Aspect.Builder;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class PlayerStompSystem extends EntityProcessingSystem {
	M<Stomp> mStomp;
	
	CameraSystem cameraSystem;
	LeafLifeSystem leafLifeSystem;
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
					leafLifeSystem.detachLeafs(treeId);
				}
			}, C.World.TimeToDetachLeafs);
		}
	}
}
