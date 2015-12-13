package net.namekdev.growing_love_garden.utils.operation;

import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.operation.temporal.DelayOperation;

public class OperationFactory2 {
	private OperationFactory2() {
	}
	
	public static DelayedOperation delayedOperation(float delay, Runnable action) {
		DelayedOperation op = Operation.prepare(DelayedOperation.class);
		op.setDuration(delay);
		op.action = action;
		return op;
	}
}
