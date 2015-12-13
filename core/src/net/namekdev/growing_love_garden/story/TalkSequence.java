package net.namekdev.growing_love_garden.story;

public class TalkSequence {
	public Talk[] sequence;
	public int currentIndex = -1;

	public TalkSequence(Talk... talks) {
		sequence = talks;
	}
	
	public boolean hasNext() {
		return currentIndex+1 < sequence.length;
	}
	
	public Talk getNext() {
		return sequence[++currentIndex];
	}
	
	public boolean leftSideBegins() {
		return sequence[0].leftSide;
	}
}
