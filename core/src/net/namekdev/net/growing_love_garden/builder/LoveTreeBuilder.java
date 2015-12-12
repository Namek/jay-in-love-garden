package net.namekdev.net.growing_love_garden.builder;

import java.util.ArrayList;

import net.namekdev.net.growing_love_garden.component.*;

import com.artemis.Entity;
import com.artemis.EntityEdit;
import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LoveTreeBuilder {
	private static final int PINK = -696254465;
	
	World world;
	TextureRegion treeTex, leafTex;
	
	ArrayList<LeafInfo> leafPoints;
	
	
	public LoveTreeBuilder(World world, TextureAtlas textures) {
		this.world = world;
		treeTex = textures.findRegion("tree");
		leafTex = textures.findRegion("leaf");
		
		leafPoints = getLeafPoints(treeTex);
	}
	
	private static ArrayList<LeafInfo> getLeafPoints(TextureRegion treeTexture) {
		TextureData texData = treeTexture.getTexture().getTextureData();
		texData.prepare();
		Pixmap pixmap = texData.consumePixmap();
		
		float w = treeTexture.getRegionWidth();
		float h = treeTexture.getRegionHeight();
		
		ArrayList<LeafInfo> points = new ArrayList<LeafInfo>();
		
		
		for (int ix = 0; ix < w; ++ix) {
			for (int iy = 0; iy < h; ++iy) {
				int x = treeTexture.getRegionX() + ix;
				int y = treeTexture.getRegionY() + iy;
				int color = pixmap.getPixel(x, y);
				
				if (color == PINK) { 
					LeafInfo leaf = new LeafInfo();
					leaf.x = ix;
					leaf.y = iy;

					points.add(leaf);
				}
			}
		}
		
		// TODO free pixmap?
		
		return points;
	}

	public Entity createTree(float x, float y, int z) {
		Entity treeEntity = world.createEntity();
		EntityEdit e = treeEntity.edit();
		
		e.create(LoveTree.class);
		e.create(Pos.class).xy(x, y);
		e.create(ZOrder.class).z = z;
		e.create(Scale.class);
		e.create(Renderable.class).setToSprite(treeTex);
		
		float treeWidth = treeTex.getRegionWidth();
		float treeHeight = treeTex.getRegionHeight();
		float leafHeight = leafTex.getRegionHeight();
		
		// init leafs
		for (int i = 0, n = leafPoints.size(); i < n; ++i) {
			LeafInfo leafInfo = leafPoints.get(i);
			Entity leaf = world.createEntity();
			EntityEdit te = leaf.edit();
			
			float lx = leafInfo.x - treeWidth / 2;
			float ly = treeHeight - leafInfo.y - leafHeight;
			
			te.create(LoveLeaf.class);
			te.create(PosChild.class).parent = treeEntity.getId();
			te.create(Pos.class).xy(lx, ly);
			te.create(Renderable.class).setToSprite(leafTex);
			te.create(Scale.class);
		}
		
		return treeEntity;
	}


	private static class LeafInfo {
		public int x, y;

		// TODO direction?
	}
}
