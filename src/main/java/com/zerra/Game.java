package com.zerra;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import com.zerra.game.manager.EntityManager;
import com.zerra.game.map.TileMap;
import com.zerra.gfx.GlWrapper;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.gfx.texture.TextureManager;
import com.zerra.object.Camera;
import com.zerra.util.Display;
import com.zerra.util.Loader;
import com.zerra.util.Timer;
import com.zerra.util.thread.ThreadPool;

public class Game implements Runnable {

	public static int WIDTH = 1280;
	public static int HEIGHT = 720;

	private static Logger logger = LogManager.getLogger();
	private static Game instance = new Game();
	private static final Timer TIMER = new Timer(60);

	private TextureManager textureManager;
	private MasterRenderer renderer;
	private Camera camera;
	private ThreadPool pool;

	private TileMap map;

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
		GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);

		textureManager = new TextureManager();
		renderer = new MasterRenderer();
		camera = new Camera();
		pool = new ThreadPool(4);

		map = new TileMap();
		load();
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
		camera.update();
		map.update();
	}

	private void render() {
		// TODO this is temporary code. Remove. Plz
		// for (int i = 0; i < 16; i++) {
		// for (int j = 0; j < 16; j++) {
		// renderer.renderTile(new TileBase(i * 16, j * 16) {
		// @Override
		// public Vector2f getTextureCoords() {
		// return new Vector2f(0, 1);
		// }
		// });
		// }
		// }
		map.render(renderer);
		renderer.render(camera);
	}

	private void cleanUp() {
		this.addTask(() -> {
			this.save();
		});
		Display.destroy();
		pool.join();
		Loader.cleanUp();
		renderer.cleanUp();
		System.exit(0);
	}

	private void save() {
		try {
			File saveFolder = new File("data/saves");
			if (!saveFolder.exists()) {
				saveFolder.mkdirs();
			}
			map.save(saveFolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void load() {
		try {
			File saveFolder = new File("data/saves");
			if (saveFolder.exists()) {
				map.load(saveFolder);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addTask(Runnable task) {
		pool.addScheduledTask(task);
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