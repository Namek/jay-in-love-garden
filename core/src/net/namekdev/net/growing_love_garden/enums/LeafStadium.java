package net.namekdev.net.growing_love_garden.enums;

public enum LeafStadium {
	None,
	Small,
	Bigger,
	GettingYellow,
	GettingSmaller,
	Falls;

	public boolean isGrowing() {
		int val = ordinal();
		return val == Small.ordinal() || val == Bigger.ordinal();
	}
	
	public boolean isLiving() {
		int val = ordinal();
		return isGrowing()
			|| val == GettingYellow.ordinal()
			|| val == GettingSmaller.ordinal();
	}
}