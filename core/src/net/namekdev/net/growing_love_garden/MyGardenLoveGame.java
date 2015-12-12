package net.namekdev.net.growing_love_garden;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.ExtendedComponentMapperPlugin;
import net.namekdev.net.growing_love_garden.system.*;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;

public class MyGardenLoveGame extends ApplicationAdapter {
	World world;
	
	@Override
	public void create() {
		WorldConfiguration cfg = new WorldConfigurationBuilder()
			.with(new ExtendedComponentMapperPlugin())
			.with(new AspectHelpers())
			.with(new EntityFactory())
			.with(new WorldInitSystem())
			.with(new TagManager())
	
			// loop systems
			.with(new PlayerStateSystem())
			.with(new PlayerStompSystem())
			.with(new CollisionSystem())
			.with(new LeafLifeSystem())
			.with(new LeafRenderSystem())
			.with(new CameraSystem())
			.with(new DepthSystem())
			.with(new RenderSystem())
			.with(new CollisionDebugSystem())
			.build();
		
		world = new World(cfg);
	}

	@Override
	public void render() {
		world.setDelta(Math.min(1/15f, Gdx.graphics.getDeltaTime()));
		world.process();
		
		if (Gdx.app.getType() == ApplicationType.Desktop) {
			if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
				Gdx.app.exit();
			}
		}
	}
}
