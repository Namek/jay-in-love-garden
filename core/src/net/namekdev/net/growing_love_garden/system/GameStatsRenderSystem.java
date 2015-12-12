package net.namekdev.net.growing_love_garden.system;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.net.growing_love_garden.component.GameState;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameStatsRenderSystem extends EntityProcessingSystem {
	M<GameState> mGameState;
	RenderSystem renderer;

	BitmapFont scoreFont;
	public final Vector2 statusPos = new Vector2(20, 20);

	
	public GameStatsRenderSystem() {
		super(Aspect.all(GameState.class));
	}

	@Override
	protected void initialize() {
		scoreFont = new BitmapFont();
	}

	@Override
	protected void process(Entity e) {
		GameState state = mGameState.get(e);

		final SpriteBatch batch = renderer.batch;
		batch.begin();
		String str = state.collectedLove + " / " + state.loveGoal;
		scoreFont.setColor(Color.WHITE);
		scoreFont.draw(batch, str, statusPos.x, statusPos.y);
		batch.end();
	}
}
