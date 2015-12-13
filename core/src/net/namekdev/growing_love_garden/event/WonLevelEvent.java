package net.namekdev.growing_love_garden.event;

import net.mostlyoriginal.api.event.common.Event;
import net.namekdev.growing_love_garden.component.GameState;

public class WonLevelEvent implements Event {
	public GameState state;
	
	public WonLevelEvent(GameState state) {
		this.state = state;
	}
}
