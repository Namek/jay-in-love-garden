package net.namekdev.growing_love_garden.story;

public class Talk {
	public int talker;
	public boolean leftSide;
	public String text;
	public float delayAfter = 0;
	public boolean waitForClick = true;
	
	public static Talk create(int talker, boolean leftSide, String text) {
		Talk t = new Talk();
		t.talker = talker;
		t.leftSide = leftSide;
		t.text = text;
		return t;
	}
	
	public static Talk c(int talker, boolean leftSide, String text) {
		return create(talker, leftSide, text);
	}
	
	public Talk delayAfter(float duration) {
		this.delayAfter = duration;
		return this;
	}
	
	public Talk dontWaitForClick(float disappearDelay) {
		delayAfter = disappearDelay;
		this.waitForClick = false;
		return this;
	}
	
	public Talk clone() {
		Talk t = create(talker, leftSide, text);
		t.delayAfter = delayAfter;
		t.waitForClick = waitForClick;
		return t;
	}
}
