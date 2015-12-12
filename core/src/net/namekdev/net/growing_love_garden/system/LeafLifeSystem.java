package net.namekdev.net.growing_love_garden.system;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.net.growing_love_garden.component.LoveLeaf;
import net.namekdev.net.growing_love_garden.component.OrigPos;
import net.namekdev.net.growing_love_garden.component.Pos;
import net.namekdev.net.growing_love_garden.component.Renderable;
import net.namekdev.net.growing_love_garden.enums.C;
import net.namekdev.net.growing_love_garden.enums.LeafStadium;

import com.artemis.Aspect;
import com.artemis.Aspect.Builder;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.MathUtils;

public class LeafLifeSystem extends EntityProcessingSystem {
	M<LoveLeaf> mLeaf;
	M<Pos> mPos;
	M<Renderable> mRenderable;
	M<OrigPos> mOrigPos;


	public LeafLifeSystem() {
		super(Aspect.all(LoveLeaf.class));
	}

	@Override
	protected void process(Entity e) {
		float dt = world.getDelta();
		LoveLeaf leaf = mLeaf.get(e);
		Pos pos = mPos.get(e);
		Renderable renderable = mRenderable.get(e);

		
		if (leaf.stadiumJustChanged) {
			leaf.stadiumJustChanged = false;
		}
		
		if (leaf.stadium == LeafStadium.None) {
			leaf.growTempo = MathUtils.random(C.Leaf.GrowTempoMin, C.Leaf.GrowTempoMax);
			leaf.justStartedGrowing = true;
			leaf.setStadium(LeafStadium.Small);
		}
		else if (leaf.stadium.isLiving()) {
			leaf.growProgress += dt * leaf.growTempo;
			leaf.justStartedGrowing = false;
		}
		else if (leaf.stadium == LeafStadium.Lying) {
			leaf.leftLyingTime -= dt;
			
			if (leaf.leftLyingTime <= 0) {
				OrigPos origPos = mOrigPos.get(e);
				pos.x = origPos.x;
				pos.y = origPos.y;
				leaf.reset();
				leaf.stadium = LeafStadium.Small;
			}
		}

		if (leaf.stadium.isLiving()) {
			if (leaf.growProgress >= 1f) {
				leaf.setStadium(LeafStadium.Falls);
			}
			else {
				if (leaf.growProgress > C.Leaf.Stadium.GettingSmaller) {				
					leaf.setStadium(LeafStadium.GettingSmaller);
				}
				else if (leaf.growProgress > C.Leaf.Stadium.GettingYellow) {
					leaf.setStadium(LeafStadium.GettingYellow);
				}
				else if (leaf.growProgress > C.Leaf.Stadium.Bigger) {
					leaf.setStadium(LeafStadium.Bigger);
				}
			}
		}

		if (leaf.stadium == LeafStadium.Falls) {
			pos.y -= dt * C.Leaf.FallSpeed;
		
			if (pos.y < 0) {
				leaf.stadium = LeafStadium.Lying;
				leaf.leftLyingTime = C.Leaf.LyingDuration;
			}
		}
		
		renderable.visible = leaf.stadium != LeafStadium.Lying;
	}
}
