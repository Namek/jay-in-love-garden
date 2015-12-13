package net.namekdev.growing_love_garden.system;

import net.namekdev.growing_love_garden.enums.C;
import net.namekdev.growing_love_garden.utils.ActionTimer;
import net.namekdev.growing_love_garden.utils.ActionTimer.TimerState;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CameraSystem extends BaseSystem {
	OrthographicCamera camera;
	ActionTimer shakeTimer = new ActionTimer();
	
	public Vector2 offset = new Vector2();
	

	@Override
	protected void initialize() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	protected void processSystem() {
		float dt = world.getDelta();
		camera.position.set(camera.viewportWidth / 2f + offset.x, camera.viewportHeight / 2f + offset.y, 0);

		if (shakeTimer.update(dt) == TimerState.Active) {
			float w2 = (C.World.ShakeWidthMax - C.World.ShakeWidthMin)/2;
			float h2 = (C.World.ShakeHeightMax - C.World.ShakeHeightMin)/2;
			camera.position.x += MathUtils.random(C.World.ShakeWidthMin, C.World.ShakeWidthMax) - w2;
			camera.position.y += MathUtils.random(C.World.ShakeHeightMin, C.World.ShakeHeightMax) - h2;
		}
		
		camera.update();
	}

	public void shake(float duration) {
		shakeTimer.start(duration);
	}
}
