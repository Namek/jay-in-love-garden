package net.namekdev.growing_love_garden.screen;

import net.namekdev.growing_love_garden.MyGardenLoveGame;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class BaseScreen extends ScreenAdapter {
	protected MyGardenLoveGame game;
	protected SpriteBatch batch;
	
	public BaseScreen(MyGardenLoveGame game) {
		this.game = game;
		batch = new SpriteBatch();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	public void popScreen() {
		game.popScreen(this);
	}
}
