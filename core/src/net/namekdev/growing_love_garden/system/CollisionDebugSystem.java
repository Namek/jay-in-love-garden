package net.namekdev.growing_love_garden.system;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.growing_love_garden.component.Collider;
import net.namekdev.growing_love_garden.component.Pos;
import net.namekdev.growing_love_garden.component.Renderable;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.Bag;

public class CollisionDebugSystem extends EntitySystem {
	M<Collider> mCollider;
	M<Pos> mPos;
	M<Renderable> mRenderable;

	CollisionSystem collisions;

	public boolean enabled = false; 


	public CollisionDebugSystem() {
		super(Aspect.all(Collider.class, Pos.class));
	}

	@Override
	protected void processSystem() {
		if (!enabled) {
			return;
		}

		Bag<Entity> entities = getEntities();
		Object[] array = entities.getData();

		int n = entities.size();

		for (int i = 0; i < n; i++) {
			Entity e1 = (Entity) array[i];

			for (int j = i+1; j < n; ++j) {
				Entity e2 = (Entity) array[j];

				if (collisions.overlapAABB(e1.getId(), e2.getId())) {
					Renderable r1 = mRenderable.get(e1);
					Renderable r2 = mRenderable.get(e2);
					r1.debugFrame = true;
					r2.debugFrame = true;
				}
			}
		}
	}
}
