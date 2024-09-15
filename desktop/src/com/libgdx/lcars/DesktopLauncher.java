package com.libgdx.lcars;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("LCARS");
		config.setWindowedMode(1000, 600);
		config.setWindowIcon("windowIcon.png");
		config.useVsync(true);
		config.setResizable(false);
		config.setForegroundFPS(60);
		new Lwjgl3Application(new LCARS(), config);
	}
}
