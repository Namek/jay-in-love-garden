package net.namekdev.growing_love_garden.component;

import com.artemis.PooledComponent;

public class GameState extends PooledComponent {
	public int collectedLove;
	public int loveGoal;
	
	/** First is zero. */
	public int levelIndex = 0;

	/** Range in [0,1]. */
	public float yearProgress = 0;


	@Override
	protected void reset() {
		collectedLove = loveGoal = 0;
		levelIndex = 0;
		yearProgress = 0;
	}

	public boolean isThereEnoughLove() {
		return collectedLove >= loveGoal;
	}
}
