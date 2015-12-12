package net.namekdev.net.growing_love_garden.system;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.net.growing_love_garden.component.GameState;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStatsRenderSystem extends EntityProcessingSystem {
	M<GameState> mGameState;
	RenderSystem renderer;

	BitmapFont scoreFont;

	
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
		scoreFont.draw(batch, str, 20, 20);
		batch.end();
	}
}
