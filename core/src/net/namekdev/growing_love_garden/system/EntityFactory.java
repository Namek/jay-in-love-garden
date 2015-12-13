package net.namekdev.growing_love_garden.system;

import net.mostlyoriginal.api.system.core.PassiveSystem;
import net.namekdev.growing_love_garden.Assets;
import net.namekdev.growing_love_garden.builder.LoveTreeBuilder;
import net.namekdev.growing_love_garden.component.Collider;
import net.namekdev.growing_love_garden.component.Origin;
import net.namekdev.growing_love_garden.component.Pos;
import net.namekdev.growing_love_garden.component.PosChild;
import net.namekdev.growing_love_garden.component.Renderable;
import net.namekdev.growing_love_garden.enums.C;
import net.namekdev.growing_love_garden.enums.Tags;
import net.namekdev.growing_love_garden.component.*;

import com.artemis.Entity;
import com.artemis.EntityEdit;
import com.artemis.managers.TagManager;

public class EntityFactory extends PassiveSystem {
	TagManager tags;

	public Assets assets;
	LoveTreeBuilder treeBuilder;


	@Override
	protected void initialize() {
		treeBuilder = new LoveTreeBuilder(world, assets.textures);
	}

	public Entity createTree(float x, float y, int z) {
		return treeBuilder.createTree(x, y, z);
	}

	public Entity createJay(float x, float y) {
		Entity jay = world.createEntity();
		EntityEdit e = jay.edit();
		tags.register(Tags.Player, jay);

		float jayWidth = assets.jayTex.getRegionWidth();
		float jayHeight = assets.jayTex.getRegionHeight();

		e.create(Pos.class).xy(x, y);
		e.create(Collider.class).wh(C.Player.ColliderBottomWidth, C.Player.ColliderBottomHeight);
		e.create(Renderable.class).setToSprite(assets.jayTex);
		
		
		// bucket
		Entity bucket = world.createEntity();
		e = bucket.edit();
		tags.register(Tags.Bucket, bucket);
		
		float bx = C.Bucket.PosLeft - jayWidth/2;
		float by = jayHeight - C.Bucket.PosTop;
		
		e.create(PosChild.class).parent = jay.getId();
		e.create(Pos.class).xy(bx, by);
		e.create(Renderable.class).setToSprite(assets.bucketTex);
		e.create(Origin.class).xy(0.5f, 1f);

		return jay;
	}
}
