package net.namekdev.net.growing_love_garden.enums;

public enum LeafLifeStadium {
	None,
	Small,
	Bigger,
	GettingYellow,
	GettingSmaller,
	Dead;


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
		return ordinal() == Dead.ordinal();
	}
	
	public boolean isPast(LeafLifeStadium stadium) {
		return ordinal() > getIndex(stadium);
	}
	
	public boolean isInOrPast(LeafLifeStadium stadium) {
		return ordinal() >= getIndex(stadium);
	}
	
	public boolean isBefore(LeafLifeStadium stadium) {		
		return ordinal() < getIndex(stadium);
	}
	
	public boolean isInOrBefore(LeafLifeStadium stadium) {		
		return ordinal() <= getIndex(stadium);
	}
	
	public boolean isBetweenExclusive(LeafLifeStadium left, LeafLifeStadium right) {
		return isPast(left) && isBefore(right);
	}
	
	public boolean isBetweenInclusive(LeafLifeStadium left, LeafLifeStadium right) {
		return isMin(left) && isMax(right);
	}
	
	public boolean isMin(LeafLifeStadium stadium) {
		return isInOrPast(stadium);
	}
	
	public boolean isMax(LeafLifeStadium stadium) {
		return isInOrBefore(stadium);
	}
	
	private int getIndex(LeafLifeStadium stadium) {
		LeafLifeStadium[] vals = values();

		for (int i = 0; i < vals.length; ++i) {
			if (vals[i] == stadium) {
				return i;
			}
		}
		
		return -1;
	}
}