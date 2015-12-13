package net.namekdev.growing_love_garden.story;

import net.namekdev.growing_love_garden.enums.C;

public interface Talks {
	static final boolean LEFT = true;
	static final boolean RIGHT = false;
	
	public static final TalkSequence tutorialTalk = new TalkSequence(
		Talk.c(Talker.MANAGER, LEFT, "Jay, you're new here. Let's setup the rules."),
		Talk.c(Talker.JAY, RIGHT, "... ?").dontWaitForClick(1f),
		Talk.c(Talker.MANAGER, LEFT, "Company has a goal this year. You need to collect $" + C.Levels.Goal[0] + "."),
		Talk.c(Talker.JAY, RIGHT, "Selling fruits?"),
		Talk.c(Talker.MANAGER, LEFT, "No. Just collecting them. You'll see."),
		Talk.c(Talker.MANAGER, LEFT, "Okay, Jay."),
		Talk.c(Talker.JAY, RIGHT, "Okay."),
		Talk.c(Talker.MANAGER, LEFT, "And Jay - don't stomp too often.")
	);
	
	public static final TalkSequence lostTalk = new TalkSequence(
		Talk.c(Talker.MANAGER, RIGHT, "You know what happened?"),
		Talk.c(Talker.JAY, LEFT, "... ?").dontWaitForClick(1f),
		Talk.c(Talker.MANAGER, RIGHT, "Well, maybe next time.")	
	);
}
