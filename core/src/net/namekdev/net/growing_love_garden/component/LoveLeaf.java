package net.namekdev.net.growing_love_garden.component;

import net.namekdev.net.growing_love_garden.enums.LeafStadium;

import com.artemis.PooledComponent;

public class LoveLeaf extends PooledComponent {
	public LeafStadium stadium = LeafStadium.None;
	public boolean stadiumJustChanged = false;
	public boolean justStartedGrowing;
	public float growProgress;
	public float growTempo;


	@Override
	protected void reset() {
		stadium = LeafStadium.None;
		stadiumJustChanged = false;
		justStartedGrowing = false;
		growProgress = 0;
		growTempo = 0;
	}


	public void setStadium(LeafStadium stadium) {
		this.stadium = stadium;
		stadiumJustChanged = true;
	}
}
