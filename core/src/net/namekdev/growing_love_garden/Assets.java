package net.namekdev.growing_love_garden;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	private static final String TEXTURES_ATLAS = "textures.txt";

	public BitmapFont talkFont;
	public AssetManager assets;
	public TextureAtlas textures;

	public TextureRegion instruction;
	public TextureRegion horizon;
	public TextureRegion jayTex, bucketTex;
	public TextureRegion manager, smile;
	public TextureRegion chatTop, chatMiddle, chatBottom;


	public void loadAll() {
		talkFont = new BitmapFont();
		
		assets = new AssetManager();
		assets.load(TEXTURES_ATLAS, TextureAtlas.class);
		assets.finishLoading();

		textures = assets.get(TEXTURES_ATLAS);
		instruction = textures.findRegion("instruction");
		horizon = textures.findRegion("horizon");
		jayTex = textures.findRegion("jay");
		bucketTex = textures.findRegion("bucket");
		manager = textures.findRegion("manager");
		smile = textures.findRegion("smile");
		chatTop = textures.findRegion("chat_top");
		chatMiddle = textures.findRegion("chat_middle");
		chatBottom = textures.findRegion("chat_bottom");
	}
}
