package net.namekdev.net.growing_love_garden.system;

import net.mostlyoriginal.api.system.core.PassiveSystem;
import net.namekdev.net.growing_love_garden.builder.LoveTreeBuilder;
import net.namekdev.net.growing_love_garden.component.*;

import com.artemis.Entity;
import com.artemis.EntityEdit;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EntityFactory extends PassiveSystem {
	private static final String TEXTURES_ATLAS = "textures.txt";

	AssetManager assets;
	TextureAtlas textures;
	
	LoveTreeBuilder treeBuilder;
	

	@Override
	protected void initialize() {
		assets = new AssetManager();
		assets.load(TEXTURES_ATLAS, TextureAtlas.class);
		assets.finishLoading();

		textures = assets.get(TEXTURES_ATLAS);
		treeBuilder = new LoveTreeBuilder(world, textures);
	}


	public Entity createTree(float x, float y, int z) {
		return treeBuilder.createTree(x, y, z);
	}
}
