package net.namekdev.growing_love_garden.system;

import com.badlogic.gdx.math.Rectangle;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.mostlyoriginal.api.system.core.PassiveSystem;
import net.namekdev.growing_love_garden.component.Collider;
import net.namekdev.growing_love_garden.component.Origin;
import net.namekdev.growing_love_garden.component.Pos;
import net.namekdev.growing_love_garden.component.PosChild;

public class CollisionSystem extends PassiveSystem {
	M<Collider> mCollider;
	M<Origin> mOrigin;
	M<Pos> mPos;
	M<PosChild> mPosChild;

	private final Rectangle rect1 = new Rectangle();
	private final Rectangle rect2 = new Rectangle();

	public boolean overlapAABB(int e1, int e2) {
		getRect(e1, rect1);
		getRect(e2, rect2);
		
		return rect2.x < rect1.x + rect1.width && rect2.x + rect2.width > rect1.x
			&& rect2.y < rect1.y + rect1.height && rect2.y + rect2.height > rect1.y;
	}

	public void getRect(int e, Rectangle rect) {
		Pos pos = mPos.get(e);
		Collider col = mCollider.get(e);

		float x = pos.x;
		float y = pos.y;
		float ox = Origin.DEFAULT_X;

		if (mPosChild.has(e)) {
			Pos parentPos = mPos.get(world.getEntity(mPosChild.get(e).parent));
			x += parentPos.x;
			y += parentPos.y;
		}

		if (mOrigin.has(e)) {
			Origin orig = mOrigin.get(e);
			ox = orig.x;
		}
		x -= ox*col.width;

		rect.set(x, y, col.width, col.height);
	}
}
