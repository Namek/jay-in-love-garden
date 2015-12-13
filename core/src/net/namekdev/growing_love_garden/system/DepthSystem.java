package net.namekdev.growing_love_garden.system;

import java.util.Comparator;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.growing_love_garden.component.ZOrder;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.Bag;

/**
 * Manages depth between objects.
 */
public class DepthSystem extends EntitySystem {
	M<ZOrder> mZOrder;

	public DepthSystem() {
		super(Aspect.all(ZOrder.class));
	}

	@Override
	protected void processSystem() {
		Bag<Entity> entities = getEntities();
		entities.sort(zOrderComparator);
	}

	private Comparator<Entity> zOrderComparator = new Comparator<Entity>() {
		@Override
		public int compare(Entity e1, Entity e2) {
			return mZOrder.get(e1).z - mZOrder.get(e2).z;
		}
	};

}
