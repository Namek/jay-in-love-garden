package net.namekdev.growing_love_garden.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.utils.Align;

import net.namekdev.growing_love_garden.MyGardenLoveGame;
import net.namekdev.growing_love_garden.story.Talk;
import net.namekdev.growing_love_garden.story.TalkSequence;

public class TalkScreen extends BaseScreen<TalkScreen> {
	private static final float PADDING = 20;

	private boolean touchBlocked = false;
	TalkSequence talkSeq;
	Talk talk;
	Runnable callbackExit;

	GlyphLayout glyphs;

	
	public TalkScreen(TalkSequence talkSeq, Runnable callbackExit) {
		this.talkSeq = talkSeq;
		this.callbackExit = callbackExit;
	}
	
	@Override
	public TalkScreen init(MyGardenLoveGame game) {
		super.init(game);

		glyphs = new GlyphLayout();
		setNextOrExit();
		
		return this;
	}
	
	private void setNextOrExit() {
		if (!talkSeq.hasNext()) {
			popScreen();
			callbackExit.run();
			return;
		}
		
		this.talk = talkSeq.getNext();
		float targetWidth = assets.chatTop.getRegionWidth() - PADDING*2;
		glyphs.setText(assets.talkFont, talk.text, Color.BLACK, targetWidth, Align.left, true);
	}

	@Override
	public void render(float delta) {
		float sw = Gdx.graphics.getWidth();
		float sh = Gdx.graphics.getHeight();

		// darken background
		Gdx.gl.glEnable(GL20.GL_BLEND);
		shapes.begin(ShapeType.Filled);
		shapes.setColor(0, 0, 0, 0.8f);
		shapes.rect(0, 0, sw, sh);
		shapes.end();


		batch.begin();

		// top part
		float x = (sw - assets.chatBottom.getRegionWidth()) / 2;
		float top = talkSeq.leftSideBegins() == talk.leftSide ? 100 : 250;
		float partHeight = assets.chatTop.getRegionHeight();
		batch.draw(assets.chatTop, x, sh - top - partHeight);
		top += partHeight;

		// middle part		
		float partWidth = assets.chatMiddle.getRegionWidth();
		partHeight = assets.chatMiddle.getRegionHeight();

		float scaleY = (glyphs.height + 2*PADDING) / partHeight;
		float realHeight = partHeight * scaleY;
		float bottom = sh - top - realHeight;
		batch.draw(assets.chatMiddle, x, bottom, 0, 0, partWidth, partHeight, 1, scaleY, 0);

		assets.talkFont.draw(batch, glyphs, x + PADDING, sh - top - PADDING);

		// bottom part
		top += realHeight;
		partWidth = assets.chatBottom.getRegionWidth();
		partHeight = assets.chatBottom.getRegionHeight();
		batch.draw(assets.chatBottom, x, sh - top - partHeight, partWidth/2, 0, partWidth, partHeight, talk.leftSide ? 1 : -1, 1, 0);
		batch.end();


		if (Gdx.input.justTouched()) {
			setNextOrExit();
		}
	}
}
