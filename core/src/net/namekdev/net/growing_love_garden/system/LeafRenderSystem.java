package net.namekdev.net.growing_love_garden.system;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.net.growing_love_garden.component.Colored;
import net.namekdev.net.growing_love_garden.component.LoveLeaf;
import net.namekdev.net.growing_love_garden.enums.C;
import net.namekdev.net.growing_love_garden.enums.LeafStadium;
import net.namekdev.net.growing_love_garden.utils.ActionSequenceTimer;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.systems.EntityProcessingSystem;

/**
 * System actually sets up the look of leafs,
 * rendering itself is batched to RenderSystem.
 * 
 * @author Namek
 */
public class LeafRenderSystem extends EntityProcessingSystem {
	M<LoveLeaf> mLeaf;
	M<Colored> mColored;
	
	ActionSequenceTimer leafColorTimer = new ActionSequenceTimer(
		C.Tree.PulseUpDuration,
		C.Tree.PulseDownDuration
	);


	public LeafRenderSystem() {
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
		LoveLeaf leaf = mLeaf.get(e);
		Colored col = mColored.get(e);
		
		if (leaf.justStartedGrowing) {
			// TODO animate scale tween
			// TODO would be better as event
		}
		
		// glow when ready to collect
		if (leaf.stadium.isGrowing()) {
			int ai = leafColorTimer.getCurrentActionIndex();
			float progress = leafColorTimer.getCurrentActionProgress();
			if (ai == 1) {
				progress = 1 - progress;
			}

			float c = progress;
			col.color.set(0, 0.8f + progress/5, 0, 1f);
		}
		else if (leaf.stadium.isInOrPast(LeafStadium.GettingYellow)) {
			col.color.set(1f, 0.88f, 0, 1);
		}
		else {
			col.color.set(0, 0.8f, 0, 1);
		}
		
		
	}
}
