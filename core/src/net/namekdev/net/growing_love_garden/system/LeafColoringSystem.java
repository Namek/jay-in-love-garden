package net.namekdev.net.growing_love_garden.system;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.net.growing_love_garden.component.Colored;
import net.namekdev.net.growing_love_garden.component.LoveLeaf;
import net.namekdev.net.growing_love_garden.enums.C;
import net.namekdev.net.growing_love_garden.utils.ActionSequenceTimer;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.systems.EntityProcessingSystem;

public class LeafColoringSystem extends EntityProcessingSystem {
	M<LoveLeaf> mLeaf;
	M<Colored> mColored;
	
	ActionSequenceTimer leafColorTimer = new ActionSequenceTimer(
		C.Tree.PulseUpDuration,
		C.Tree.PulseDownDuration
	);


	public LeafColoringSystem() {
		super(Aspect.all(LoveLeaf.class));
	}

	@Override
	protected void begin() {
		if (!leafColorTimer.isRunning()) {
			leafColorTimer.start();
		}
		
		leafColorTimer.update(world.getDelta());
	}

	@Override
	protected void process(Entity e) {
		Colored col = mColored.get(e);
		
		int ai = leafColorTimer.getCurrentActionIndex();
		float progress = leafColorTimer.getCurrentActionProgress();
		if (ai == 1) {
			progress = 1 - progress;
		}
		
		float c = progress;
		col.color.set(c, c, c, 1f);
	}
}
