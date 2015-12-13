package net.namekdev.growing_love_garden.system;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.growing_love_garden.component.Pos;
import net.namekdev.growing_love_garden.component.Stomp;
import net.namekdev.growing_love_garden.enums.C;
import net.namekdev.growing_love_garden.enums.Tags;
import net.namekdev.growing_love_garden.component.*;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerStateSystem extends BaseSystem {
	M<Pos> mPos;
	M<Stomp> mStomp;
	
	AspectHelpers aspects;
	CollisionSystem collisions;
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

		int horzDir = input.isKeyPressed(Keys.LEFT) || input.isKeyPressed(Keys.A) ? -1
			: input.isKeyPressed(Keys.RIGHT) || input.isKeyPressed(Keys.D) ? 1 : 0;
		int vertDir = input.isKeyPressed(Keys.DOWN) || input.isKeyPressed(Keys.S) ? -1
				: input.isKeyPressed(Keys.UP) || input.isKeyPressed(Keys.W) ? 1 : 0;
		
		boolean isMoving = horzDir != 0 || vertDir != 0;

		pos.x += C.Player.NormalMoveSpeed * horzDir;
		pos.y += C.Player.NormalMoveSpeed * vertDir;
		
		// TODO flip vertically if horzDir == 1
		
		if (isMoving) {
			// TODO animate walk
		}
		
		if (input.isKeyJustPressed(Keys.SPACE)) {
			mStomp.create(e);
		}
	}

	public int findCloseTree() {
		Entity player = tags.getEntity(Tags.Player);
		
		IntBag trees = aspects.getTrees();
		for (int i = 0, n = trees.size(); i < n; ++i) {
			int treeId = trees.get(i);
			
			if (collisions.overlapAABB(player.getId(), treeId)) {
				return treeId;
			}
		}
		
		return -1;
	}

}
