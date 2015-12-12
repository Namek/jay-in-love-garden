package net.namekdev.net.growing_love_garden.system;

import net.mostlyoriginal.api.system.core.PassiveSystem;
import net.namekdev.net.growing_love_garden.component.*;

import com.artemis.Entity;
import com.artemis.EntityEdit;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EntityFactory extends PassiveSystem {
	AssetManager assets;
	TextureAtlas textures;
	
	TextureRegion treeTexture;
	
	private static final String TEXTURES_ATLAS = "textures.txt";
	

	@Override
	protected void initialize() {
		assets = new AssetManager();
		assets.load(TEXTURES_ATLAS, TextureAtlas.class);
		assets.finishLoading();

		textures = assets.get(TEXTURES_ATLAS);

		treeTexture = textures.findRegion("tree");
	}


	public Entity createTree(float x, float y, int z) {
		Entity entity = world.createEntity();
		EntityEdit e = entity.edit();
		
		e.create(LoveTree.class);
		e.create(Pos.class).xy(x, y);
		e.create(ZOrder.class).z = z;
		e.create(Scale.class);
		e.create(Renderable.class).setToSprite(treeTexture);
	
		return entity;
	}
}
