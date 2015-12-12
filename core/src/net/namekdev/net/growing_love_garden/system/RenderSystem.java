package net.namekdev.net.growing_love_garden.system;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.net.growing_love_garden.component.Colored;
import net.namekdev.net.growing_love_garden.component.Origin;
import net.namekdev.net.growing_love_garden.component.Pos;
import net.namekdev.net.growing_love_garden.component.PosChild;
import net.namekdev.net.growing_love_garden.component.Renderable;
import net.namekdev.net.growing_love_garden.component.Scale;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RenderSystem extends EntitySystem {
	M<Colored> mColored;
	M<Origin> mOrigin;
	M<Pos> mPos;
	M<PosChild> mPosChild;
	M<Renderable> mRenderable;
	M<Scale> mScale;

	SpriteBatch batch;
	

	public RenderSystem() {
		super(Aspect.all(Renderable.class, Pos.class));
	}

	@Override
	protected void initialize() {
		batch = new SpriteBatch();
	}
	

	@Override
	protected void processSystem() {
		Gdx.gl.glClearColor(0, 0, 1, 0.97f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		Bag<Entity> entities = getEntities();
		Object[] array = entities.getData();
		for (int i = 0, s = entities.size(); s > i; i++) {
			process((Entity) array[i]);
		}
		
		batch.end();
	}

	protected void process(Entity e) {
		Renderable renderable = mRenderable.get(e);
		Pos pos = mPos.get(e);
		Scale scale = mScale.getSafe(e);
		Origin origin = mOrigin.getSafe(e);
		
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
		}
		else if (renderable.type == Renderable.Type.FrameAnim) {
			
		}
	}
}
