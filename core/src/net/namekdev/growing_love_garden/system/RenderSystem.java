package net.namekdev.growing_love_garden.system;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.growing_love_garden.component.Collider;
import net.namekdev.growing_love_garden.component.Colored;
import net.namekdev.growing_love_garden.component.Origin;
import net.namekdev.growing_love_garden.component.Pos;
import net.namekdev.growing_love_garden.component.PosChild;
import net.namekdev.growing_love_garden.component.Renderable;
import net.namekdev.growing_love_garden.component.Rotation;
import net.namekdev.growing_love_garden.component.Scale;
import net.namekdev.growing_love_garden.enums.C;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.Bag;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.NumberUtils;

/**
 * Renders entities that are sorted by {@link DepthSystem}.
 * 
 * @author Namek
 */
public class RenderSystem extends BaseSystem {
	M<Collider> mCollider;
	M<Colored> mColored;
	M<Origin> mOrigin;
	M<Pos> mPos;
	M<PosChild> mPosChild;
	M<Renderable> mRenderable;
	M<Rotation> mRotation;
	M<Scale> mScale;
	
	CameraSystem cameraSystem;
	CollisionSystem collisions;
	DepthSystem depthSystem;//companion system
	EntityFactory entityFactory;

	SpriteBatch batch;
	ShapeRenderer shapes;
	
	final Rectangle tmpRect = new Rectangle();
	private final Vector2 screenPos = new Vector2();
	
	private Color skyColor = new Color(0x7F99FFFF);
	private Color grassColor = new Color(0x55CC45FF);
	private Color nearGrassColor = new Color(0x05CC45FF);
	


	@Override
	protected void initialize() {
		batch = new SpriteBatch();
		shapes = new ShapeRenderer();
	}

	@Override
	protected void processSystem() {
        batch.setProjectionMatrix(cameraSystem.camera.combined);

		Gdx.gl.glClearColor(0, 0, 1, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// first render game background
		final float sw = Gdx.graphics.getWidth();
		final float sh = Gdx.graphics.getHeight();
		final TextureRegion horizon = entityFactory.assets.horizon;
		float w = horizon.getRegionWidth(), h = horizon.getRegionHeight();
		shapes.begin(ShapeType.Filled);

		shapes.setColor(skyColor);
		float bottom = C.World.TopHorizonGraphicBottom;
		cameraSystem.worldToScreen(screenPos.set(0, bottom));
		shapes.rect(0, screenPos.y, sw, sh);
		
		shapes.setColor(grassColor);
		cameraSystem.worldToScreen(screenPos.set(0, -C.World.ShakeHeightMax));
		shapes.rect(0, screenPos.y, sw, bottom + C.World.ShakeHeightMax);
		
		shapes.setColor(nearGrassColor);
		cameraSystem.worldToScreen(screenPos.set(0, C.World.LowerHorizonGraphicBottom));
		shapes.rect(0, 0, sw, screenPos.y);
		
		shapes.end();

		batch.begin();
		batch.setColor(Color.WHITE);
		batch.draw(horizon, 0, C.World.TopHorizonGraphicBottom);
		batch.setColor(NumberUtils.intToFloatColor(0xffffff0e));
		batch.draw(horizon, 0, C.World.LowerHorizonGraphicBottom, w/2, 0, w, h, -1, 1, 0);
		
		// then render all the other things
		shapes.begin(ShapeType.Line);
		
		Integer[] entities = depthSystem.sortedEntities;
		for (int i = 0, n = entities.length; i < n; i++) {
			process(entities[i]);
		}

		batch.end();
		shapes.end();
	}

	protected void process(int e) {
		Renderable renderable = mRenderable.get(e);
		Pos pos = mPos.get(e);
		Scale scale = mScale.getSafe(e);
		Origin origin = mOrigin.getSafe(e);
		Rotation rot = mRotation.getSafe(e);
		
		if (!renderable.visible) {
			return;
		}
		
		float scaleX = scale != null ? scale.x : 1f;
		float scaleY = scale != null ? scale.y : 1f;
		float originX = origin != null ? origin.x : Origin.DEFAULT_X;
		float originY = origin != null ? origin.y : Origin.DEFAULT_Y;
		float rotation = rot != null ? rot.rotation : 0;
		
		if (renderable.type == Renderable.Type.Sprite) {
			TextureRegion img = renderable.sprite;
			float w = img.getRegionWidth();
			float h = img.getRegionHeight();
			
			float x = pos.x, y = pos.y;
			if (mPosChild.has(e)) {
				Pos parentPos = mPos.get(world.getEntity(mPosChild.get(e).parent));
				x += parentPos.x;
				y += parentPos.y;
			}

			if (mColored.has(e)) {
				Colored col = mColored.get(e);
				batch.setColor(col.color);
			}
			else {
				batch.setColor(Color.WHITE);
			}
			
			float ox = originX*w;
			float oy = originY*h;

			batch.draw(img, x - ox, y - oy, ox, oy, w, h, scaleX, scaleY, rotation);

			if (renderable.debugFrame && mCollider.has(e)) {
				collisions.getRect(e, tmpRect);
				shapes.rect(tmpRect.x, tmpRect.y, tmpRect.width, tmpRect.height);
				renderable.debugFrame = false;
			}
		}
		else if (renderable.type == Renderable.Type.FrameAnim) {
			
		}
	}
}
