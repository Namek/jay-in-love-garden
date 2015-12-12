package net.namekdev.net.growing_love_garden.system;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;

public class InputSystem extends BaseSystem {
	Input input;

	@Override
	protected void initialize() {
		input = Gdx.app.getInput();
	}

	@Override
	protected void processSystem() {
		if (Gdx.app.getType() == ApplicationType.Desktop) {
			if (input.isKeyJustPressed(Keys.ESCAPE)) {
				Gdx.app.exit();
			}
		}
	}

}
