package net.namekdev.net.growing_love_garden.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.namekdev.net.growing_love_garden.MyGardenLoveGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 768;
		new LwjglApplication(new MyGardenLoveGame(), config);
	}
}
