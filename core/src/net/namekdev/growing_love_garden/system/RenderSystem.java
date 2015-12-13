package net.namekdev.growing_love_garden.system;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.growing_love_garden.component.Collider;
import net.namekdev.growing_love_garden.component.Colored;
import net.namekdev.growing_love_garden.component.Origin;
import net.namekdev.growing_love_garden.component.Pos;
import net.namekdev.growing_love_garden.component.PosChild;
import net.namekdev.growing_love_garden.component.Renderable;
import net.namekdev.growing_love_garden.component.Scale;
import net.namekdev.growing_love_garden.enums.C;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.NumberUtils;

public class RenderSystem extends EntitySystem {
	M<Collider> mCollider;
	M<Colored> mColored;
	M<Origin> mOrigin;
	M<Pos> mPos;
	M<PosChild> mPosChild;
	M<Renderable> mRenderable;
	M<Scale> mScale;
	
	CameraSystem cameraSystem;
	CollisionSystem collisions;
	EntityFactory entityFactory;

	SpriteBatch batch;
	ShapeRenderer shapes;
	final Rectangle tmpRect = new Rectangle();
	
	private Color skyColor = new Color(0x7F99FFFF);
	private Color grassColor = new Color(0x55CC45FF);
	private Color nearGrassColor = new Color(0x05CC45FF);
	

	public RenderSystem() {
		super(Aspect.all(Renderable.class, Pos.class));
	}

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
		shapes.rect(0, bottom, sw, sh - bottom + C.World.ShakeHeightMax);
		
		shapes.setColor(grassColor);
		shapes.rect(0, 0 - C.World.ShakeHeightMax, sw, bottom + C.World.ShakeHeightMax);
		shapes.setColor(nearGrassColor);
		shapes.rect(0, 0, sw, C.World.LowerHorizonGraphicBottom + C.World.ShakeHeightMax);
		
		shapes.end();

		batch.begin();
		batch.setColor(Color.WHITE);
		batch.draw(horizon, 0, C.World.TopHorizonGraphicBottom);
		batch.setColor(NumberUtils.intToFloatColor(0xffffff0e));
		batch.draw(horizon, 0, C.World.LowerHorizonGraphicBottom, w/2, 0, w, h, -1, 1, 0);
		
		// then render all the other things
		shapes.begin(ShapeType.Line);
		Bag<Entity> entities = getEntities();
		Object[] array = entities.getData();
		for (int i = 0, s = entities.size(); s > i; i++) {
			process((Entity) array[i]);
		}
		
		batch.end();
		shapes.end();
	}

	protected void process(Entity e) {
		Renderable renderable = mRenderable.get(e);
		Pos pos = mPos.get(e);
		Scale scale = mScale.getSafe(e);
		Origin origin = mOrigin.getSafe(e);
		
		if (!renderable.visible) {
			return;
		}
		
		float scaleX = scale != null ? scale.x : 1f;
		float scaleY = scale != null ? scale.y : 1f;
		float originX = origin != null ? origin.x : Origin.DEFAULT_X;
		float originY = origin != null ? origin.y : Origin.DEFAULT_Y;
		
		if (renderable.type == Renderable.Type.Sprite) {
			TextureRegion img = renderable.sprite;
			float w = img.getRegionWidth();
			float h = img.getRegionHeight();
			float rotation = 0;
			
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
				collisions.getRect(e.getId(), tmpRect);
				shapes.rect(tmpRect.x, tmpRect.y, tmpRect.width, tmpRect.height);
				renderable.debugFrame = false;
			}
		}
		else if (renderable.type == Renderable.Type.FrameAnim) {
			
		}
	}
}
