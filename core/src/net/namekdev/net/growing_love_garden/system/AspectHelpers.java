
package net.namekdev.net.growing_love_garden.system;

import com.artemis.Aspect;
import com.artemis.Aspect.Builder;
import com.artemis.AspectSubscriptionManager;
import com.artemis.Entity;
import com.artemis.EntitySubscription;
import com.artemis.utils.IntBag;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.mostlyoriginal.api.system.core.PassiveSystem;
import net.namekdev.net.growing_love_garden.component.LoveLeaf;
import net.namekdev.net.growing_love_garden.component.LoveTree;
import net.namekdev.net.growing_love_garden.component.PosChild;
import net.namekdev.net.growing_love_garden.utils.Utils;
import net.namekdev.net.growing_love_garden.utils.Utils.IntBagPredicate;

public class AspectHelpers extends PassiveSystem {
	AspectSubscriptionManager aspects;
	
	M<PosChild> mPosChild;


	@Override
	protected void initialize() {
		aspects = world.getAspectSubscriptionManager();
	}
	
	public EntitySubscription getSubscription(Builder aspect) {
		return aspects.get(aspect);
	}

	public IntBag getEntities(Builder aspect) {
		return getSubscription(aspect).getEntities();
	}
	
	public IntBag getTrees() {
		return getEntities(Aspect.all(LoveTree.class));
	}
	
	public IntBag getAllLeafs() {
		return getEntities(Aspect.all(LoveLeaf.class));
	}
	
	public IntBag getLeafs(final int parentTreeId) {
		IntBag leafs = getAllLeafs();

		return Utils.filterBag(leafs, new IntBagPredicate() {
			@Override
			public boolean apply(int e) {
				return mPosChild.get(e).parent == parentTreeId;
			}
		});
	}
}
