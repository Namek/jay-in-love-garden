package net.namekdev.net.growing_love_garden.system;

import static com.badlogic.gdx.math.Interpolation.exp10In;
import static com.badlogic.gdx.math.Interpolation.exp10Out;
import static com.badlogic.gdx.math.Interpolation.pow4In;
import static net.namekdev.net.growing_love_garden.enums.LeafLifeStadium.*;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.net.growing_love_garden.component.Colored;
import net.namekdev.net.growing_love_garden.component.LoveLeaf;
import net.namekdev.net.growing_love_garden.component.Pos;
import net.namekdev.net.growing_love_garden.component.Scale;
import net.namekdev.net.growing_love_garden.enums.C;
import net.namekdev.net.growing_love_garden.enums.LeafLifeStadium;
import net.namekdev.net.growing_love_garden.utils.ActionSequenceTimer;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

/**
 * System actually sets up the look of leafs,
 * rendering itself is batched to RenderSystem.
 * 
 * @author Namek
 */
public class LeafRenderSystem extends EntityProcessingSystem {
	M<LoveLeaf> mLeaf;
	M<Colored> mColored;
	M<Pos> mPos;
	M<Scale> mScale;
	
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
		Pos pos = mPos.get(e);
		Scale scale = mScale.get(e);

		
		// glow when ready to collect
		if (leaf.stadium.isGrowing()) {
			int ai = leafColorTimer.getCurrentActionIndex();
			float progress = leafColorTimer.getCurrentActionProgress();
			if (ai == 1) {
				progress = 1 - progress;
			}

			float c = (ai == 0 ? exp10Out : exp10In).apply(progress);
			col.color.set(0, 0.8f + progress/5, 0, 1f);
			
			if (leaf.stadium == Small) {
				float growProgress = MathUtils.clamp(leaf.lifeProgress, 0, C.Leaf.Stadium.Bigger) / (C.Leaf.Stadium.Bigger - 0);
				scale.y = MathUtils.lerp(C.Leaf.ScaleYMin, C.Leaf.ScaleYMax, pow4In.apply(growProgress));
			}
			else if (leaf.stadium == Bigger) {
				scale.y = C.Leaf.ScaleYMax;
			}
		}
		else if (leaf.stadium == GettingYellow) {
			float bl = C.Leaf.Stadium.GettingYellow;
			float br = C.Leaf.Stadium.GettingSmaller;
			float w = br - bl;
			float yellowingProgress = MathUtils.clamp(leaf.lifeProgress - bl, 0, w) / w; 
			col.color.set(0, 1, 0, 1).lerp(Color.YELLOW, yellowingProgress);
		}
		else if (leaf.stadium == GettingSmaller) {
			// getting red
			float b = C.Leaf.Stadium.GettingSmaller;
			float dieProgress = MathUtils.clamp(leaf.lifeProgress - b, 0, 1f - b) / (1f - b);
			col.color.set(Color.YELLOW).lerp(Color.RED, dieProgress);
		}
		else {
			col.color.set(Color.RED);
		}
	}
}
