package net.namekdev.growing_love_garden;

import java.util.Stack;

import net.namekdev.growing_love_garden.screen.BaseScreen;
import net.namekdev.growing_love_garden.screen.GameScreen;
import net.namekdev.growing_love_garden.screen.TutorialScreen;
import net.namekdev.growing_love_garden.system.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;

public class MyGardenLoveGame extends ApplicationAdapter {
	private Stack<BaseScreen> screenStack = new Stack<BaseScreen>();
	private GameScreen gameScreen;
	private Assets assets;

	
	@Override
	public void create() {
		assets = new Assets();
		assets.loadAll();
		gameScreen = new GameScreen().init(this);

		pushScreen(gameScreen);
//		pushScreen(new TutorialScreen(this));
	}

	@Override
	public void render() {
		if (Gdx.app.getType() == ApplicationType.Desktop) {
			if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
				Gdx.app.exit();
			}
		}
		
		gameScreen.isPaused = screenStack.size() > 1;

		float dt = Math.min(1/15f, Gdx.graphics.getDeltaTime());
		for (int i = 0, n = screenStack.size(); i < n; ++i) {
			screenStack.get(i).render(dt);
		}
	}

	public void popScreen(BaseScreen screen) {
		if (screenStack.peek() != screen) {
			throw new RuntimeException("Popping another screen.");
		}
		
		screenStack.pop();
		screen.dispose();
	}
	
	public void pushScreen(BaseScreen screen) {
		screenStack.push(screen);
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}
	
	public Assets getAssets() {
		return assets;
	}
}
