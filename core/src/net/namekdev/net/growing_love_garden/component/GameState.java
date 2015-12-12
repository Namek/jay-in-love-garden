package net.namekdev.net.growing_love_garden.component;

import com.artemis.PooledComponent;

public class GameState extends PooledComponent {
	public int collectedLove;
	public int loveGoal;


	@Override
	protected void reset() {
		collectedLove = loveGoal = 0;
	}
}
