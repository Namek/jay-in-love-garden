package net.namekdev.net.growing_love_garden.system;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.net.growing_love_garden.component.LoveLeaf;
import net.namekdev.net.growing_love_garden.component.OrigPos;
import net.namekdev.net.growing_love_garden.component.Pos;
import net.namekdev.net.growing_love_garden.component.PosChild;
import net.namekdev.net.growing_love_garden.component.Renderable;
import net.namekdev.net.growing_love_garden.enums.C;
import net.namekdev.net.growing_love_garden.enums.LeafLifeStadium;
import net.namekdev.net.growing_love_garden.enums.LeafPositionState;

import com.artemis.Aspect;
import com.artemis.Aspect.Builder;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.math.MathUtils;

public class LeafLifeSystem extends EntityProcessingSystem {
	M<LoveLeaf> mLeaf;
	M<Pos> mPos;
	M<PosChild> mPosChild;
	M<Renderable> mRenderable;
	M<OrigPos> mOrigPos;
	
	AspectHelpers aspects;


	public LeafLifeSystem() {
		super(Aspect.all(LoveLeaf.class, Pos.class, PosChild.class));
	}

	@Override
	protected void process(Entity e) {
		float dt = world.getDelta();
		Pos pos = mPos.get(e);
		Renderable renderable = mRenderable.get(e);
		LoveLeaf leaf = mLeaf.get(e);


		if (leaf.stadiumJustChanged) {
			leaf.stadiumJustChanged = false;
		}
		
		if (leaf.state == LeafPositionState.Attached) {
			if (leaf.stadium == LeafLifeStadium.None) {
				leaf.growTempo = MathUtils.random(C.Leaf.GrowTempoMin, C.Leaf.GrowTempoMax);
				leaf.setStadium(LeafLifeStadium.Small);
				leaf.lifeProgress = MathUtils.random(C.Leaf.LifeProgressStartMin, C.Leaf.LifeProgressStartMax);
				
				// TODO dispatch event: LeafStartedGrowingEvent
			}
			else if (leaf.stadium.isLiving()) {
				leaf.lifeProgress += dt * leaf.growTempo;

				if (leaf.lifeProgress >= 1f) {
					leaf.setState(LeafPositionState.Falls);
					leaf.fallSpeed = C.Leaf.NaturalFallSpeed;
				}
				else {
					if (leaf.lifeProgress > C.Leaf.Stadium.GettingSmaller) {				
						leaf.setStadium(LeafLifeStadium.GettingSmaller);
					}
					else if (leaf.lifeProgress > C.Leaf.Stadium.GettingYellow) {
						leaf.setStadium(LeafLifeStadium.GettingYellow);
					}
					else if (leaf.lifeProgress > C.Leaf.Stadium.Bigger) {
						leaf.setStadium(LeafLifeStadium.Bigger);
					}
				}
			}
		}
		else if (leaf.state == LeafPositionState.Falls) {
			pos.y -= dt * leaf.fallSpeed;
		
			if (pos.y < 0) {
				leaf.setState(LeafPositionState.Lying);
				leaf.leftLyingTime = C.Leaf.LyingDuration;
			}
		}
		else if (leaf.state == LeafPositionState.Lying) {
			leaf.leftLyingTime -= dt;
			
			if (leaf.leftLyingTime <= 0) {
				OrigPos origPos = mOrigPos.get(e);
				pos.x = origPos.x;
				pos.y = origPos.y;
				leaf.reset();
				leaf.setStadium(LeafLifeStadium.None);
			}
		}
		
		renderable.visible = leaf.stadium != LeafLifeStadium.None;
	}

	public IntBag detachLeafs(int treeId) {
		IntBag leafs = aspects.getLeafs(treeId);
		
		for (int i = 0, n = leafs.size(); i < n; ++i) {
			int leafId = leafs.get(i);
			LoveLeaf leaf = mLeaf.get(leafId);
			
			if (canDetachLeaf(leaf)) {
				leaf.setState(LeafPositionState.Falls);
				leaf.fallSpeed = C.Leaf.StompFallSpeed;
			}
		}
		
		return leafs;
	}
	
	private boolean canDetachLeaf(LoveLeaf leaf) {
		return leaf.stadium.isMin(LeafLifeStadium.Bigger);
	}
}
