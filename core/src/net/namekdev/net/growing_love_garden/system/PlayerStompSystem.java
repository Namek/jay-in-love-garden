package net.namekdev.net.growing_love_garden.system;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.net.growing_love_garden.component.Jay;
import net.namekdev.net.growing_love_garden.component.Stomp;
import net.namekdev.net.growing_love_garden.enums.C;

import com.artemis.Aspect;
import com.artemis.Aspect.Builder;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

public class PlayerStompSystem extends EntityProcessingSystem {
	M<Stomp> mStomp;
	
	CameraSystem cameraSystem;


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

		cameraSystem.shake(C.Player.StompDuration);
	}

}
