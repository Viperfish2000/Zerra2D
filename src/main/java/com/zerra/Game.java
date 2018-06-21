package com.zerra;

import java.awt.Rectangle;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

import com.zerra.game.handlers.EntityManager;
import com.zerra.game.world.tile.Tile;
import com.zerra.game.world.tile.TileBase;
import com.zerra.gfx.GlWrapper;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.gfx.texture.TextureManager;
import com.zerra.util.Display;
import com.zerra.util.Loader;
import com.zerra.util.Timer;

public class Game implements Runnable {

	public static int WIDTH = 1280;
	public static int HEIGHT = 720;

	private static Logger logger = LogManager.getLogger();
	private static Game instance = new Game();
	private static final Timer TIMER = new Timer(60);

	private TextureManager textureManager;
	private MasterRenderer renderer;

	private boolean running;

	public static EntityManager manager = new EntityManager();

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
		Display.createDisplay("test", WIDTH, HEIGHT);

		GlWrapper.disableDepth();

		textureManager = new TextureManager();
		renderer = new MasterRenderer();
	}

	@Override
	public void run() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}

		long timer = System.currentTimeMillis();
		int frames = 0;

		while (running) {
			if (!Display.isCloseRequested())
				Display.update();
			else
				running = false;

			TIMER.updateTimer();

			for (int i = 0; i < Math.min(10, TIMER.elapsedTicks); ++i) {
				update();
			}

			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			render();
			frames++;

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
		renderer.render(Arrays.asList(new Tile[] { new TileBase(0, 0) {
			@Override
			public Rectangle getBounds() {
				return null;
			}

			@Override
			public Vector4f getTextureCoords() {
				return new Vector4f(0, 0, 16, 16);
			}
		} }));
	}

	private void cleanUp() {
		Loader.cleanUp();
		renderer.cleanUp();
	}
	
	public float getRenderPartialTicks() {
		return TIMER.renderPartialTicks;
	}

	public TextureManager getTextureManager() {
		return textureManager;
	}

	public MasterRenderer getRenderer() {
		return renderer;
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