package net.namekdev.growing_love_garden.screen;

import net.namekdev.growing_love_garden.MyGardenLoveGame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TutorialScreen extends BaseScreen {
	SpriteBatch batch;

	public TutorialScreen(MyGardenLoveGame game) {
		super(game);
		
		// init world
		game.getGameScreen().world.process();
	}
}
