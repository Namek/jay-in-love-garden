package net.namekdev.net.growing_love_garden.system;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.namekdev.net.growing_love_garden.component.Pos;
import net.namekdev.net.growing_love_garden.component.Renderable;
import net.namekdev.net.growing_love_garden.component.Scale;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RenderSystem extends EntitySystem {
	M<Renderable> mRenderable;
	M<Pos> mPos;
	M<Scale> mScale;

	SpriteBatch batch;
	

	public RenderSystem() {
		super(Aspect.all(Renderable.class, Pos.class, Scale.class));
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
		Scale scale = mScale.get(e);
		
		if (renderable.type == Renderable.Type.Sprite) {
			TextureRegion img = renderable.sprite;
			float w = img.getRegionWidth();
			float h = img.getRegionHeight();
			float w2 = w / 2;
			float rotation = 0;
			batch.draw(img, pos.x - w2, pos.y, w2, 0, w, h, scale.x, scale.y, rotation);
		}
		else if (renderable.type == Renderable.Type.FrameAnim) {
			
		}
	}
}
