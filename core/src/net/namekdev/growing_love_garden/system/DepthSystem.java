package net.namekdev.growing_love_garden.system;

import java.util.Comparator;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.growing_love_garden.component.Pos;
import net.namekdev.growing_love_garden.component.Renderable;
import net.namekdev.growing_love_garden.component.ZOrder;
import net.namekdev.growing_love_garden.utils.Utils;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.EntitySubscription;
import com.artemis.EntitySubscription.SubscriptionListener;
import com.artemis.utils.IntBag;
import com.artemis.utils.Sort;

/**
 * Manages depth between objects.
 */
public class DepthSystem extends BaseSystem implements SubscriptionListener {
	M<ZOrder> mZOrder;
	AspectHelpers aspects;

	/**
	 * Sorted by depth, ready to render by RenderSystem.
	 */
	public Integer[] sortedEntities;
	public boolean dirtyOrder = false;
	private boolean dirtySize = true;

	private final Aspect.Builder renderablesAspect = Aspect.all(Renderable.class, Pos.class);
	private EntitySubscription renderablesSubscription;


	@Override
	protected void initialize() {
		renderablesSubscription = world.getAspectSubscriptionManager().get(renderablesAspect);
		renderablesSubscription.addSubscriptionListener(this);
	}

	@Override
	protected void processSystem() {
		// TODO ugly, I know: primitive boxing, no array pooling
		if (dirtySize) {
			sortedEntities = Utils.cloneIntBagToArray(renderablesSubscription.getEntities());
			dirtySize = false;
		}
	
		if (dirtyOrder) {
			Sort.instance().sort(sortedEntities, zOrderComparator);
		}
	}

	@Override
	public void inserted(IntBag entities) {
		dirtySize = true;
	}

	@Override
	public void removed(IntBag entities) {
		dirtySize = true;
	}

	private Comparator<Integer> zOrderComparator = new Comparator<Integer>() {
		@Override
		public int compare(Integer e1, Integer e2) {
			ZOrder e1z = mZOrder.getSafe(e1);
			ZOrder e2z = mZOrder.getSafe(e2);
			
			int e1zo = e1z != null ? e1z.z : 0;
			int e2zo = e2z != null ? e2z.z : 0;
			
			return e2zo - e1zo;
		}
	};
}
