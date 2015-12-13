package net.namekdev.growing_love_garden.screen;

import net.namekdev.growing_love_garden.MyGardenLoveGame;

import com.badlogic.gdx.ScreenAdapter;

public abstract class BaseScreen extends ScreenAdapter {
	protected MyGardenLoveGame game;
	
	public BaseScreen(MyGardenLoveGame game) {
		this.game = game;
	}
	
	public void popScreen() {
		game.popScreen(this);
	}
}
