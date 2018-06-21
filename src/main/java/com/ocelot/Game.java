package com.ocelot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ocelot.util.Display;
import com.ocelot.util.Loader;

public class Game implements Runnable {

	private static Logger logger = LogManager.getLogger();
	private static Game instance = new Game();

	private boolean running;

	private Game() {
	}

	public synchronized void start() {
		if (running)
			return;

		running = true;
	}

	public synchronized void stop() {
		if (running)
			return;

		running = false;
	}

	private void init() throws Exception {
		Display.createDisplay("test", 1280, 720);
	}

	@Override
	public void run() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0D;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0.0D;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			if (!Display.isCloseRequested())
				Display.update();
			else
				running = false;
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
			}
			if (running) {
				render();
				frames++;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				logger.info("World Finished Loading: " + /* Game.worldFinishedLoading + */" --- FPS: " + frames);
				frames = 0;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		cleanUp();
	}

	private void update() {

	}

	private void render() {

	}

	private void cleanUp() {
		Loader.cleanUp();
	}

	public static Logger logger() {
		return logger;
	}

	public static Game getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		new Thread(Game.getInstance()).start();
		Game.getInstance().start();
	}
}