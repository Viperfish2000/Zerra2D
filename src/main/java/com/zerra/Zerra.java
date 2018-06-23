package com.zerra;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

import com.zerra.game.manager.EntityManager;
import com.zerra.game.map.TileMap;
import com.zerra.gfx.GlWrapper;
import com.zerra.gfx.light.Light;
import com.zerra.gfx.post.PostProcessing;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.gfx.texture.TextureManager;
import com.zerra.object.Camera;
import com.zerra.util.Display;
import com.zerra.util.Loader;
import com.zerra.util.Maths;
import com.zerra.util.Timer;
import com.zerra.util.thread.ThreadPool;

public class Zerra implements Runnable {

	public static int WIDTH = 1280;
	public static int HEIGHT = 720;

	private static Logger logger = LogManager.getLogger();
	private static Zerra instance = new Zerra();
	private static final Timer TIMER = new Timer(60);

	private TextureManager textureManager;
	private MasterRenderer renderer;
	private Camera camera;
	private ThreadPool pool;

	private TileMap map;
	private float worldTime = 0.0F;
	private float time = 1.0F;

	private boolean running;

	public static EntityManager manager = new EntityManager();

	private Zerra() {
	}

	/**
	 * Sets the game's running status to true.
	 */
	public synchronized void start() {
		if (running)
			return;

		logger.info("Running game...");
		running = true;
	}

	/**
	 * Sets the game's running status to false.
	 */
	public synchronized void stop() {
		if (running)
			return;

		logger.info("Shutting down game...");
		running = false;
	}

	/**
	 * Initializes the game.
	 * 
	 * @throws Exception
	 *             May be unable to load missing world files.
	 */
	private void init() throws Exception {
		logger.info("Creating display...");
		Display.createDisplay("Zerra", WIDTH, HEIGHT);

		GlWrapper.enableDepth();
		GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
		PostProcessing.init();

		textureManager = new TextureManager();
		renderer = new MasterRenderer();
		camera = new Camera();
		pool = new ThreadPool(4);

		map = new TileMap();

		logger.info("Loading world...");

		/** This is where world loading is first called. */
		load("world");
		logger.info("Generating terrain...");
		map.generate();
	}

	/**
	 * This is the GAME LOOP. This regulates the game cycling. DO NOT TOUCH.
	 */
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

			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// logger.info("World Finished Loading: " + Game.worldFinishedLoading + " --- fps: " + frames);
				Display.setTitle("Zerra | fps: " + frames);
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

	/**
	 * Updates game logic and events.
	 */
	private void update() {
		camera.update();
		map.update();

		/** Day/Night Cycle **/
		time = (float) (Math.cos(worldTime) + 0.5);
		// System.out.println(time);
		time = (float) Maths.clamp(time, 0.2F, 1.0F);
		if (time > 1.0F || time < 0.2F) {
			worldTime += 0.0005F;
		} else {
			worldTime += 0.0001F;
		}
		if (Display.getMouseButton() == 0) {
			worldTime += 0.01f;
			System.out.println(worldTime);
		}
		if (Display.getMouseButton() == 1) {
			worldTime -= 0.01f;
			System.out.println(worldTime);
		}
	}

	/**
	 * Renders game.
	 */
	private void render() {
		renderer.renderLights(new Light(new Vector2f((float) Display.getMouseX() / MasterRenderer.SCALE + camera.getPosition().x, (float) Display.getMouseY() / MasterRenderer.SCALE + camera.getPosition().y), new Vector4f(1, 1, 1, 50), 25));
		renderer.setAmbientLight(time, time, time);
		map.render(renderer);
		renderer.render(camera);
	}

	/**
	 * Cleans up the game before the game loop has closed.
	 */
	private void cleanUp() {
		this.addTask(() -> {
			this.save("world");
		});
		Display.destroy();
		pool.join();
		PostProcessing.cleanUp();
		Loader.cleanUp();
		renderer.cleanUp();
		logger.info("Closing game...");
		System.exit(0);
	}

	/**
	 * Saves current world.
	 */
	private void save(String worldName) {
		try {
			File saveFolder = new File("data/saves");
			if (!saveFolder.exists()) {
				saveFolder.mkdirs();
			}
			logger.info("Saving world...");
			map.save(saveFolder, worldName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads up a world.
	 * 
	 * @param worldName
	 *            The name of the world to load up.
	 */
	private void load(String worldName) {
		try {
			File saveFolder = new File("data/saves");
			if (saveFolder.exists()) {
				map.load(saveFolder, worldName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a task to the thread pool.
	 * 
	 * @param task
	 *            The task to add.
	 */
	public void addTask(Runnable task) {
		pool.addScheduledTask(task);
	}

	/**
	 * @return The partial ticks of the game. Useful for smooth rendering.
	 */
	public float getRenderPartialTicks() {
		return TIMER.renderPartialTicks;
	}

	/**
	 * @return The texture manager for the game.
	 */
	public TextureManager getTextureManager() {
		return textureManager;
	}

	/**
	 * @return The master renderer for the game.
	 */
	public MasterRenderer getRenderer() {
		return renderer;
	}

	/**
	 * @return The camera for the game.
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * @return The logger for the game.
	 */
	public static Logger logger() {
		return logger;
	}

	/**
	 * @return The instance for the game.
	 */
	public static Zerra getInstance() {
		return instance;
	}

	/**
	 * The main method. This is where it all began...
	 * 
	 * @param args
	 *            You know what these do... or don't. Don't think it really matters, to be honest.
	 */
	public static void main(String[] args) {
		logger.info("Setting up game...");
		new Thread(Zerra.getInstance()).start();
		Zerra.getInstance().start();
	}
}