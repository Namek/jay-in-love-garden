package net.namekdev.growing_love_garden.story;

import static net.namekdev.growing_love_garden.story.Talker.*;

import net.namekdev.growing_love_garden.enums.C;

public interface Talks {
	static final boolean LEFT = true;
	static final boolean RIGHT = false;
	
	public static final TalkSequence introTalk = new TalkSequence(
		Talk.c(MANAGER, LEFT, "Jay, you're new here. Let's setup the rules."),
		Talk.c(JAY, RIGHT, "... ?").dontWaitForClick(1f),
		Talk.c(MANAGER, LEFT, "This is a test for the starters. You need to collect $" + C.Levels.Goal[0] + "."),
		Talk.c(JAY, RIGHT, "Selling fruits?"),
		Talk.c(MANAGER, LEFT, "No. Just collecting them. You'll see."),
		Talk.c(MANAGER, LEFT, "Okay, Jay."),
		Talk.c(JAY, RIGHT, "Okay."),
		Talk.c(MANAGER, LEFT, "And Jay - don't stomp too often.")
	);
	
	public static final TalkSequence lostTalk = new TalkSequence(
		Talk.c(MANAGER, RIGHT, "You know what happened?"),
		Talk.c(JAY, LEFT, "... ?").dontWaitForClick(1f),
		Talk.c(MANAGER, RIGHT, "Well, maybe next time.")	
	);
	
	public static final TalkSequence nextLevelTalk = new TalkSequence(
		Talk.c(MANAGER, RIGHT, "Good! You collected $#{bonus} bonus."),
		Talk.c(MANAGER, RIGHT, "That's yours!"),
		Talk.c(JAY, LEFT, ":-)"),
		Talk.c(MANAGER, RIGHT, "Your next goal is to collect $#{goal}. Good luck!")
	);
}
