package net.namekdev.growing_love_garden.screen;

import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.ExtendedComponentMapperPlugin;
import net.namekdev.growing_love_garden.MyGardenLoveGame;
import net.namekdev.growing_love_garden.component.GameState;
import net.namekdev.growing_love_garden.enums.C;
import net.namekdev.growing_love_garden.event.LostGameEvent;
import net.namekdev.growing_love_garden.event.WonLevelEvent;
import net.namekdev.growing_love_garden.story.TalkSequence;
import net.namekdev.growing_love_garden.story.Talks;
import net.namekdev.growing_love_garden.system.AspectHelpers;
import net.namekdev.growing_love_garden.system.CameraSystem;
import net.namekdev.growing_love_garden.system.CollisionDebugSystem;
import net.namekdev.growing_love_garden.system.CollisionSystem;
import net.namekdev.growing_love_garden.system.DepthSystem;
import net.namekdev.growing_love_garden.system.EntityFactory;
import net.namekdev.growing_love_garden.system.GameStateSystem;
import net.namekdev.growing_love_garden.system.GameStatsRenderSystem;
import net.namekdev.growing_love_garden.system.LeafLifeSystem;
import net.namekdev.growing_love_garden.system.LeafRenderSystem;
import net.namekdev.growing_love_garden.system.LeafVacuumSystem;
import net.namekdev.growing_love_garden.system.PlayerStateSystem;
import net.namekdev.growing_love_garden.system.PlayerStompSystem;
import net.namekdev.growing_love_garden.system.RenderSystem;
import net.namekdev.growing_love_garden.system.SchedulerSystem;
import net.namekdev.growing_love_garden.system.WorldInitSystem;

import com.artemis.BaseSystem;
import com.artemis.SystemInvocationStrategy;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.managers.TagManager;
import com.artemis.utils.Bag;

public class GameScreen extends BaseScreen<GameScreen> {
	World world;
	public boolean isPaused;

	public GameScreen init(MyGardenLoveGame game) {
		super.init(game);

		EntityFactory entityFactory = new EntityFactory();
		entityFactory.assets = game.getAssets();

		WorldConfiguration cfg = new WorldConfigurationBuilder()
			.with(new ExtendedComponentMapperPlugin())
			.with(new AspectHelpers())
			.with(entityFactory)
			.with(new WorldInitSystem())
			.with(new TagManager())
	
			// loop systems
			.with(new GameStateSystem())
			.with(new PlayerStateSystem())
			.with(new PlayerStompSystem())
			.with(new CollisionSystem())
			.with(new LeafLifeSystem())
			.with(new LeafRenderSystem())
			.with(new CameraSystem())
			.with(new LeafVacuumSystem())
			.with(new DepthSystem())
			.with(new RenderSystem())
			.with(new GameStatsRenderSystem())
			.with(new CollisionDebugSystem())
			.with(new SchedulerSystem())
			.with(new EventSystem())
			.build();
		
		cfg.setInvocationStrategy(new SystemInvocationStrategy() {
			@Override
			protected void process(Bag<BaseSystem> systems) {
				Object[] systemsData = systems.getData();
				for (int i = 0, s = systems.size(); s > i; i++) {
					BaseSystem system = (BaseSystem) systemsData[i];
					if (!isPaused || system instanceof RenderSystem) {
						system.process();
					}
					updateEntityStates();
				}
			}
		});

		world = new World(cfg);
		world.getSystem(EventSystem.class).registerEvents(this);
		
		return this;
	}

	@Override
	public void render(float delta) {
		world.setDelta(!isPaused ? delta : 0);
		world.process();
	}
	
	@Subscribe
	private void onLostGame(LostGameEvent evt) {
		game.pushScreen(new GameOverScreen(evt.state).init(game));
	}
	
	@Subscribe
	private void onWonGame(WonLevelEvent evt) {
		final GameState state = evt.state;
		int nextLevelIndex = state.levelIndex+1;

		// Is there next level?
		if (nextLevelIndex < C.Levels.Goal.length) {
			int bonus = state.getBonus();
			int nextGoal = C.Levels.Goal[nextLevelIndex];
			boolean isLastLevel = nextLevelIndex == C.Levels.Goal.length-1;

			TalkSequence talk = (!isLastLevel ? Talks.nextLevel : Talks.finalMission).deepClone()
				.param("bonus", bonus)
				.param("goal", nextGoal);

			game.pushScreen(new TalkScreen(talk, setNextLevel).init(game));
		}

		// No more levels - You won whole game
		else {
			game.pushScreen(new WonGameScreen().init(game));
		}
	}
	
	private Runnable setNextLevel = new Runnable() {
		public void run() {
			world.getSystem(GameStateSystem.class).setNextLevel();
		}
	};
}
