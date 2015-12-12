package net.namekdev.net.growing_love_garden.system;

import net.mostlyoriginal.api.system.core.PassiveSystem;
import net.namekdev.net.growing_love_garden.builder.LoveTreeBuilder;
import net.namekdev.net.growing_love_garden.component.*;
import net.namekdev.net.growing_love_garden.enums.C;
import net.namekdev.net.growing_love_garden.enums.Tags;

import com.artemis.Entity;
import com.artemis.EntityEdit;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EntityFactory extends PassiveSystem {
	private static final String TEXTURES_ATLAS = "textures.txt";
	
	TagManager tags;
	AssetManager assets;
	TextureAtlas textures;
	
	LoveTreeBuilder treeBuilder;
	TextureRegion jayTex, bucketTex;
	

	@Override
	protected void initialize() {
		assets = new AssetManager();
		assets.load(TEXTURES_ATLAS, TextureAtlas.class);
		assets.finishLoading();

		textures = assets.get(TEXTURES_ATLAS);
		treeBuilder = new LoveTreeBuilder(world, textures);
		jayTex = textures.findRegion("jay");
		bucketTex = textures.findRegion("bucket");
	}


	public Entity createTree(float x, float y, int z) {
		return treeBuilder.createTree(x, y, z);
	}


	public Entity createJay(float x, float y) {
		Entity jay = world.createEntity();
		EntityEdit e = jay.edit();
		tags.register(Tags.Player, jay);

		e.create(Pos.class).xy(x, y);
		e.create(Renderable.class).setToSprite(jayTex);
		
		float jayWidth = jayTex.getRegionWidth();
		float jayHeight = jayTex.getRegionHeight();
		
		// bucket
		Entity bucket = world.createEntity();
		e = bucket.edit();
		tags.register(Tags.Bucket, bucket);
		
		float bx = C.Player.BucketLeft - jayWidth/2;
		float by = jayHeight - C.Player.BucketTop;
		
		e.create(PosChild.class).parent = jay.getId();
		e.create(Pos.class).xy(bx, by);
		e.create(Renderable.class).setToSprite(bucketTex);
		e.create(Origin.class).xy(0.5f, 1f);

		return jay;
	}
}
