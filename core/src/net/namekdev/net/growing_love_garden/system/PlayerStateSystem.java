package net.namekdev.net.growing_love_garden.system;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.net.growing_love_garden.component.*;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerStateSystem extends EntityProcessingSystem {
	M<Pos> mPos;
	

	public PlayerStateSystem() {
		super(Aspect.all(Jay.class, Pos.class));
	}


	@Override
	protected void process(Entity e) {
		
	}

}
