package net.namekdev.growing_love_garden.system;

import com.artemis.Entity;

import net.mostlyoriginal.api.operation.common.Operation;

public class SchedulerSystem extends net.mostlyoriginal.api.system.SchedulerSystem {
	public void schedule(Operation operation) {
		Entity e = world.createEntity();
		mSchedule.create(e).operation.add(operation);
	}
}
