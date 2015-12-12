package net.namekdev.net.growing_love_garden.system;

import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.net.growing_love_garden.component.LoveLeaf;
import net.namekdev.net.growing_love_garden.component.OrigPos;
import net.namekdev.net.growing_love_garden.component.OrigPosChild;
import net.namekdev.net.growing_love_garden.component.Pos;
import net.namekdev.net.growing_love_garden.component.PosChild;
import net.namekdev.net.growing_love_garden.component.Vacuumed;
import net.namekdev.net.growing_love_garden.enums.C;
import net.namekdev.net.growing_love_garden.enums.LeafPositionState;
import net.namekdev.net.growing_love_garden.event.LeafVacuumedEvent;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class LeafVacuumSystem extends EntityProcessingSystem {
	M<LoveLeaf> mLeaf;
	M<Pos> mPos;
	M<PosChild> mPosChild;
	M<OrigPos> mOrigPos;
	M<OrigPosChild> mOrigPosChild;
	M<Vacuumed> mVacuumed;

	EventSystem events;
	GameStatsRenderSystem gameStats;

	private final Vector2 v1 = new Vector2();
	private final Vector2 v2 = new Vector2();


	public LeafVacuumSystem() {
		super(Aspect.all(LoveLeaf.class, Vacuumed.class, Pos.class));
	}

	@Override
	public void inserted(Entity e) {
		Pos pos = mPos.get(e);
		int parentId = mOrigPosChild.create(e).parent = mPosChild.get(e).parent;
		Pos parentPos = mPos.get(world.getEntity(parentId));
		pos.x += parentPos.x;
		pos.y += parentPos.y;
		mPosChild.remove(e);

		Vacuumed vacuumed = mVacuumed.get(e);
		v1.set(pos.x, pos.y);
		v2.set(vacuumed.targetX, vacuumed.targetY);
		v2.sub(v1);
		float dist = v2.len();
		vacuumed.startDistance = dist;
	}

	@Override
	public void removed(Entity e) {
		int parentId = mPosChild.create(e).parent = mOrigPosChild.get(e).parent;
		Pos pos = mPos.get(e);
		OrigPos origPos = mOrigPos.get(e);
		pos.x = origPos.x;
		pos.y = origPos.y;
		mOrigPosChild.remove(e);
	}

	@Override
	protected void process(Entity e) {
		float dt = world.getDelta();

		Pos pos = mPos.get(e);
		Vacuumed vacuumed = mVacuumed.get(e);

		v1.set(pos.x, pos.y);
		v2.set(vacuumed.targetX, vacuumed.targetY);
		v2.sub(v1);

		float dist = v2.len();

		if (dist > C.Leaf.VacuumDisappearDistance) {
			float speed = Interpolation.swingOut.apply(1f - dist/vacuumed.startDistance);
			speed = MathUtils.lerp(C.Leaf.VacuumSpeedMin, C.Leaf.VacuumSpeedMax, speed);

			pos.x += v2.x * speed * dt;
			pos.y += v2.y * speed * dt;
		}
		else {
			mVacuumed.remove(e);
			events.dispatch(new LeafVacuumedEvent(e.getId()));
		}
	}

	public void vacuum(int leafId) {
		LoveLeaf leaf = mLeaf.get(leafId);
		leaf.state = LeafPositionState.FlyingToAccount;
		Vacuumed vac = mVacuumed.create(leafId);
		vac.targetX = gameStats.statusPos.x;
		vac.targetY = gameStats.statusPos.y;
	}
}
