package net.namekdev.net.growing_love_garden.system;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.mostlyoriginal.api.system.core.PassiveSystem;
import net.namekdev.net.growing_love_garden.component.Pos;

public class CollisionSystem extends PassiveSystem {
	M<Pos> mPos;
	
	public boolean checkOverlap(int e1, int e2) {
		Pos pos1 = mPos.get(e1);
		Pos pos2 = mPos.get(e2);
//		Size size1 = mSize.get(e1);
//		Size size2 = mSize.get(e2);
		
		// TODO aabb overlap algorithm
		
		return true;
	}
}
