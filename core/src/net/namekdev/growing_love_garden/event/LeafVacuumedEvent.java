package net.namekdev.growing_love_garden.event;

import net.mostlyoriginal.api.event.common.Event;

public class LeafVacuumedEvent implements Event {
	public int leafId;
	
	public LeafVacuumedEvent(int leafId) {
		this.leafId = leafId;
	}
}
