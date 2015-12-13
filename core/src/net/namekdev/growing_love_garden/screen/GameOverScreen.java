package net.namekdev.growing_love_garden.screen;

import net.namekdev.growing_love_garden.MyGardenLoveGame;
import net.namekdev.growing_love_garden.component.GameState;

public class GameOverScreen extends BaseScreen {
	GameState state;

	public GameOverScreen(MyGardenLoveGame game, GameState state) {
		super(game);
		this.state = state;
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
	}
	
	
}
