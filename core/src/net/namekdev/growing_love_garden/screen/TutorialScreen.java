package net.namekdev.growing_love_garden.screen;

import net.namekdev.growing_love_garden.MyGardenLoveGame;

public class TutorialScreen extends BaseScreen {
	public TutorialScreen(MyGardenLoveGame game) {
		super(game);
		
		// init world
		game.getGameScreen().world.process();
	}
}
