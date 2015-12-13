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
	public final Vector2 yearBarPos = new Vector2(20, 50);
	public final Vector2 loveBarPos = new Vector2(20, 25);
	public final Vector2 statusTextPos = new Vector2(20, 20);
	private final Color nearGrassColor = new Color(0x05CC45FF);//taken from RenderSystem

	
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
			str = "$" + state.collectedLove + " / $" + state.loveGoal;
		}
		else {
			str = "$" + state.loveGoal + " + $" + (state.collectedLove - state.loveGoal);
		}
		
		scoreFont.setColor(Color.WHITE);
		scoreFont.draw(batch, str, statusTextPos.x, statusTextPos.y);
		batch.end();
		
		final ShapeRenderer shapes = renderer.shapes;
		shapes.begin(ShapeType.Filled);

		shapes.setColor(Color.WHITE);
		shapes.rect(yearBarPos.x, yearBarPos.y, 100, 15);
		shapes.setColor(Color.CORAL);
		shapes.rect(yearBarPos.x + 2, yearBarPos.y + 2, MathUtils.clamp(state.yearProgress, 0, 1)*96, 11);

		float loveProgress = state.collectedLove / (float)state.loveGoal;
		shapes.setColor(Color.WHITE);
		shapes.rect(loveBarPos.x, loveBarPos.y, 100, 15);
		shapes.setColor(nearGrassColor);
		shapes.rect(loveBarPos.x + 2, loveBarPos.y + 2, MathUtils.clamp(loveProgress, 0, 1)*96, 11);

		shapes.end();
	}
}
