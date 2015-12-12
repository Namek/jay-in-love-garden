package net.namekdev.net.growing_love_garden.component;

import net.namekdev.net.growing_love_garden.enums.LeafLifeStadium;
import net.namekdev.net.growing_love_garden.enums.LeafPositionState;

import com.artemis.PooledComponent;

public class LoveLeaf extends PooledComponent {
	public LeafPositionState state = LeafPositionState.Attached;
	public LeafLifeStadium stadium = LeafLifeStadium.None;
	public boolean stadiumJustChanged = false;
	public float lifeProgress;
	public float growTempo;
	public float leftLyingTime;
	public float fallSpeed;


	@Override
	public void reset() {
		state = LeafPositionState.Attached;
		stadium = LeafLifeStadium.None;
		stadiumJustChanged = false;
		lifeProgress = 0;
		growTempo = 0;
		leftLyingTime = 0;
		fallSpeed = 0;
	}


	public void setStadium(LeafLifeStadium stadium) {
		this.stadium = stadium;
		stadiumJustChanged = true;
	}

	public void setState(LeafPositionState state) {
		this.state = state;
	}
}
