package net.namekdev.growing_love_garden.screen;

import net.namekdev.growing_love_garden.Assets;
import net.namekdev.growing_love_garden.MyGardenLoveGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public abstract class BaseScreen<T extends BaseScreen<T>> extends ScreenAdapter {
	protected MyGardenLoveGame game;
	protected SpriteBatch batch;
	protected ShapeRenderer shapes;
	protected Assets assets;
	
	public T init(MyGardenLoveGame game) {
		this.game = game;
		batch = new SpriteBatch();
		shapes = new ShapeRenderer();
		assets = game.getAssets();
		
		return (T) this;
	}

	@Override
	public void dispose() {
		batch.dispose();
		shapes.dispose();
	}

	public void popScreen() {
		game.popScreen(this);
	}
	
	protected void darkenBackground() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		shapes.begin(ShapeType.Filled);
		shapes.setColor(0, 0, 0, 0.8f);
		shapes.rect(0, 0, sw(), sh());
		shapes.end();
	}
	
	protected float sw() {
		return Gdx.graphics.getWidth();
	}
	
	protected float sh() {
		return Gdx.graphics.getHeight();
	}
}
