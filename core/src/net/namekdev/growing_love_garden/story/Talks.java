package net.namekdev.growing_love_garden.story;

import static net.namekdev.growing_love_garden.story.Talker.*;

import net.namekdev.growing_love_garden.enums.C;

public interface Talks {
	static final boolean LEFT = true;
	static final boolean RIGHT = false;
	
	public static final TalkSequence intro = new TalkSequence(
		Talk.c(MANAGER, LEFT, "Jay, you're new here. Let's setup the rules."),
		Talk.c(JAY, RIGHT, "... ?").dontWaitForClick(1f),
		Talk.c(MANAGER, LEFT, "This is a test for the starters. You need to collect $" + C.Levels.Goal[0] + ".").smile(),
		Talk.c(JAY, RIGHT, "Selling fruits?"),
		Talk.c(MANAGER, LEFT, "No. Just collecting them. You'll see."),
		Talk.c(MANAGER, LEFT, "Okay, Jay."),
		Talk.c(JAY, RIGHT, "Okay.").smile(),
		Talk.c(MANAGER, LEFT, "And Jay - don't stomp too often.").smile()
	);
	
	public static final TalkSequence lost = new TalkSequence(
		Talk.c(MANAGER, LEFT, "I told you not to stomp too often, didn't I?"),
		Talk.c(MANAGER, LEFT, "You earned $#{sum}. Go home."),
		Talk.c(MANAGER, LEFT, "And next time work harder!")
	);
	
	public static final TalkSequence lostZero = new TalkSequence(
		Talk.c(MANAGER, RIGHT, "You know what happened?"),
		Talk.c(JAY, LEFT, "... ?").dontWaitForClick(1f),
		Talk.c(MANAGER, RIGHT, "You earned nothing. Well, maybe next time.")	
	);
	
	public static final TalkSequence nextLevel = new TalkSequence(
		Talk.c(MANAGER, RIGHT, "Good! You collected $#{bonus} extra."),
		Talk.c(MANAGER, RIGHT, "That's yours!"),
		Talk.c(JAY, LEFT, ":-)").smile(),
		Talk.c(MANAGER, RIGHT, "Your next goal is to collect $#{goal}. Good luck!")
	);
	
	public static final TalkSequence finalMission = new TalkSequence(
		Talk.c(MANAGER, LEFT, "Amazing! $#{bonus} extra and it is yours again.").smile(),
		Talk.c(JAY, RIGHT, "\\o/"),
		Talk.c(MANAGER, LEFT, "It's your final mission. Are you ready?"),
		Talk.c(JAY, RIGHT, "Uuumm...").dontWaitForClick(1f),
		Talk.c(MANAGER, LEFT, "Some sweets await you.").smile(),
		Talk.c(JAY, RIGHT, "Ready!").smile(),
		Talk.c(MANAGER, LEFT, "Collect $#{goal}!")
	);
	
	public static final TalkSequence congrats = new TalkSequence(
		Talk.c(JAY, LEFT, "I did it!").smile(),
		Talk.c(MANAGER, RIGHT, "You earned $#{total}.").smile(),
		Talk.c(MANAGER, RIGHT, "I have no more job for you. Thanks for your help.")
	);
	
}
