package net.namekdev.net.growing_love_garden;

import net.mostlyoriginal.api.plugin.extendedcomponentmapper.ExtendedComponentMapperPlugin;
import net.namekdev.net.growing_love_garden.system.*;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class MyGardenLoveGame extends ApplicationAdapter {
	World world;
	
	@Override
	public void create() {
		WorldConfiguration cfg = new WorldConfigurationBuilder()
			.with(new ExtendedComponentMapperPlugin())
			.with(new EntityFactory())
			.with(new WorldInitSystem())
	
			// loop systems
			.with(new InputSystem())
			.with(new PlayerStateSystem())
			.with(new LeafColoringSystem())
			.with(new DepthSystem())
			.with(new RenderSystem())
			.build();
		
		world = new World(cfg);
	}

	@Override
	public void render() {
		world.setDelta(Gdx.graphics.getDeltaTime());
		world.process();
	}
}
