package net.namekdev.net.growing_love_garden.system;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.net.growing_love_garden.component.*;
import net.namekdev.net.growing_love_garden.enums.C;
import net.namekdev.net.growing_love_garden.enums.Tags;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerStateSystem extends BaseSystem {
	M<Pos> mPos;
	
	TagManager tags;
	
	Input input;


	@Override
	protected void initialize() {
		input = Gdx.app.getInput();
	}

	@Override
	protected void processSystem() {
		Entity e = tags.getEntity(Tags.Player);
		Pos pos = mPos.get(e);

		int horzDir = input.isKeyPressed(Keys.LEFT) ? -1
			: input.isKeyPressed(Keys.RIGHT) ? 1 : 0;
		int vertDir = input.isKeyPressed(Keys.DOWN) ? -1
				: input.isKeyPressed(Keys.UP) ? 1 : 0;
		
		boolean isMoving = horzDir != 0 || vertDir != 0;

		pos.x += C.Player.NormalMoveSpeed * horzDir;
		pos.y += C.Player.NormalMoveSpeed * vertDir;
		
		// TODO flip vertically if horzDir == 1
		
		if (isMoving) {
			// TODO animate walk
		}
	}

}
