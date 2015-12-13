package net.namekdev.growing_love_garden.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class InstructionScreen extends BaseScreen<InstructionScreen> {
	@Override
	public void render(float delta) {
		darkenBackground();

		batch.begin();
		float x = (sw() - assets.instruction.getRegionWidth()) / 2;
		float y = (sh() - assets.instruction.getRegionHeight()) / 2;
		batch.draw(assets.instruction, x, y);
		batch.end();

		if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			popScreen();
		}
	}
}
