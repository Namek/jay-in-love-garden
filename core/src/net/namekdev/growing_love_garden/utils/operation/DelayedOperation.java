package net.namekdev.growing_love_garden.utils.operation;

import net.mostlyoriginal.api.operation.temporal.DelayOperation;

public class DelayedOperation extends DelayOperation {
	@Override
	protected void end() {
		action.run();
	}

	public Runnable action;
}
