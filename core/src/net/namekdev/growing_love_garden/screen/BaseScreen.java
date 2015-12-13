package net.namekdev.growing_love_garden.screen;

import net.namekdev.growing_love_garden.Assets;
import net.namekdev.growing_love_garden.MyGardenLoveGame;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class BaseScreen<T extends BaseScreen<T>> extends ScreenAdapter {
	protected MyGardenLoveGame game;
	protected SpriteBatch batch;
	protected Assets assets;
	
	public T init(MyGardenLoveGame game) {
		this.game = game;
		batch = new SpriteBatch();
		assets = game.getAssets();
		
		return (T) this;
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	public void popScreen() {
		game.popScreen(this);
	}
}
