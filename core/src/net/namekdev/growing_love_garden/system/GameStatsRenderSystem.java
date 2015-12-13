package net.namekdev.growing_love_garden.system;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.growing_love_garden.component.GameState;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class GameStatsRenderSystem extends EntityProcessingSystem {
	M<GameState> mGameState;
	RenderSystem renderer;

	BitmapFont scoreFont;
	public final Vector2 statusPos = new Vector2(20, 20);
	public final Vector2 yearPos = new Vector2(20, 40);

	
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
		String str = null;
		
		if (state.collectedLove <= state.loveGoal) { 
			str = state.collectedLove + " / " + state.loveGoal;
		}
		else {
			str = state.loveGoal + " + " + (state.collectedLove - state.loveGoal);
		}
		
		scoreFont.setColor(Color.WHITE);
		scoreFont.draw(batch, str, statusPos.x, statusPos.y);
		batch.end();
		
		final ShapeRenderer shapes = renderer.shapes;
		shapes.begin(ShapeType.Filled);
		shapes.setColor(Color.WHITE);
		shapes.rect(yearPos.x, yearPos.y, 100, 15);
		shapes.setColor(Color.CORAL);
		shapes.rect(yearPos.x + 2, yearPos.y + 2, MathUtils.clamp(state.yearProgress, 0, 1)*96, 11);
		shapes.end();
	}
}
