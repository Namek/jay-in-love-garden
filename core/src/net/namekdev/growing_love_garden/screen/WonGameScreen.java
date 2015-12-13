package net.namekdev.growing_love_garden.screen;

import net.namekdev.growing_love_garden.MyGardenLoveGame;
import net.namekdev.growing_love_garden.enums.C;
import net.namekdev.growing_love_garden.utils.ActionTimer;
import net.namekdev.growing_love_garden.utils.ActionTimer.TimerState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class WonGameScreen extends BaseScreen<WonGameScreen> {
	ActionTimer waitTimer = new ActionTimer(C.ThanksMinDuration);
	boolean wasQueuedToNextScreen = false;
	Runnable callbackExit;
	
	
	public WonGameScreen(Runnable callbackExit) {
		this.callbackExit = callbackExit;
	}

	@Override
	public WonGameScreen init(MyGardenLoveGame game) {
		waitTimer.start();
		return super.init(game);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		shapes.begin(ShapeType.Filled);
		shapes.setColor(0, 0.4f, 0, 0.95f);
		shapes.rect(0, 0, sw(), sh());
		shapes.end();
		
		batch.begin();
		float w = assets.thanks.getRegionWidth();
		float h = assets.thanks.getRegionHeight();
		batch.draw(assets.thanks, (sw()-w)/2, (sh()-h)/2);
		batch.end();
		
		if (waitTimer.update(delta) != TimerState.Active) {
			if (wasQueuedToNextScreen) {
				popScreen();
				callbackExit.run();
			}
		}
		
		final Input i = Gdx.input;
		if (i.isKeyJustPressed(Keys.SPACE) || i.isKeyJustPressed(Keys.ENTER) || i.isTouched()) {
			wasQueuedToNextScreen = true;
		}
	}

}
