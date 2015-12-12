package net.namekdev.net.growing_love_garden.enums;

public enum LeafStadium {
	None,
	Small,
	Bigger,
	GettingYellow,
	GettingSmaller,
	Falls,
	Lying;


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
	
	public boolean isDead() {
		int val = ordinal();
		return val == Falls.ordinal() || val == Lying.ordinal();
	}
	
	public boolean isPast(LeafStadium stadium) {
		return ordinal() > getIndex(stadium);
	}
	
	public boolean isInOrPast(LeafStadium stadium) {
		return ordinal() >= getIndex(stadium);
	}
	
	public boolean isBefore(LeafStadium stadium) {		
		return ordinal() < getIndex(stadium);
	}
	
	public boolean isInOrBefore(LeafStadium stadium) {		
		return ordinal() <= getIndex(stadium);
	}
	
	public boolean isBetweenExclusive(LeafStadium left, LeafStadium right) {
		return isPast(left) && isBefore(right);
	}
	
	public boolean isBetweenInclusive(LeafStadium left, LeafStadium right) {
		return isMin(left) && isMax(right);
	}
	
	public boolean isMin(LeafStadium stadium) {
		return isInOrPast(stadium);
	}
	
	public boolean isMax(LeafStadium stadium) {
		return isInOrBefore(stadium);
	}
	
	private int getIndex(LeafStadium stadium) {
		LeafStadium[] vals = values();

		for (int i = 0; i < vals.length; ++i) {
			if (vals[i] == stadium) {
				return i;
			}
		}
		
		return -1;
	}
}