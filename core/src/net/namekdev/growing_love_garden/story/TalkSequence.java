package net.namekdev.growing_love_garden.story;

public class TalkSequence {
	public Talk[] sequence;
	public int currentIndex = -1;

	public TalkSequence(Talk... talks) {
		sequence = talks;
	}
	
	protected TalkSequence() {
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

	public TalkSequence deepClone() {
		TalkSequence talk = new TalkSequence();
		talk.sequence = new Talk[sequence.length];

		for (int i = 0; i < sequence.length; ++i) {
			talk.sequence[i] = sequence[i].clone();
		}

		return talk;
	}
	
	/**
	 * Changes pattern like #{name} into given text value.
	 * Remember to use {@link #deepClone()} before calling this.
	 */
	public TalkSequence param(String name, String value) {
		for (int i = 0; i < sequence.length; ++i) {
			Talk talk = sequence[i];
			talk.text = talk.text.replace("#{" + name + "}", value);
		}
		
		return this;
	}
	
	public TalkSequence param(String name, int value) {
		return param(name, value+"");
	}
}
