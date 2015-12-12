package net.namekdev.net.growing_love_garden.system;

import net.namekdev.net.growing_love_garden.component.LoveTree;

import com.artemis.Aspect;
import com.artemis.Aspect.Builder;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

public class TreeGrowSystem extends EntityProcessingSystem {

	public TreeGrowSystem() {
		super(Aspect.all(LoveTree.class));
		
	}

	@Override
	protected void process(Entity e) {
		// TODO Auto-generated method stub

	}

}
