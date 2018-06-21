package com.zerra;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

import com.zerra.game.manager.EntityManager;
import com.zerra.game.world.tile.TileBase;
import com.zerra.gfx.GlWrapper;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.gfx.texture.TextureManager;
import com.zerra.object.Camera;
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
	private Camera camera;

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
		camera = new Camera();
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
				// logger.info("World Finished Loading: " + Game.worldFinishedLoading + " --- fps: " + frames);
				Display.setTitle("test | fps: " + frames);
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
		// TODO this is temporary code. Remove. Plz
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				renderer.renderTile(new TileBase(i * 16, j * 16) {
					@Override
					public Vector2f getTextureCoords() {
						return new Vector2f(0, 1);
					}
				});
			}
		}
		renderer.render(camera);
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

	public Camera getCamera() {
		return camera;
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