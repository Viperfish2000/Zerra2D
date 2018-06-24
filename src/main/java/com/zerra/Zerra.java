package com.zerra;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.zerra.game.entity.EntityFirefly;
import com.zerra.game.entity.EntityPlayer;
import com.zerra.game.registry.EntityRegistry;
import com.zerra.game.world.World;
import com.zerra.gfx.GlWrapper;
import com.zerra.gfx.light.Light;
import com.zerra.gfx.post.PostProcessing;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.gfx.texture.TextureManager;
import com.zerra.object.Camera;
import com.zerra.util.Display;
import com.zerra.util.Loader;
import com.zerra.util.LoadingUtils;
import com.zerra.util.ResourceLocation;
import com.zerra.util.Timer;
import com.zerra.util.thread.ThreadPool;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * The main game class. Handles all of the game's functions.
 * 
 * @author Ocelot5836, Hypeirochus
 */
public class Zerra implements Runnable {

	public static int WIDTH = 1280;
	public static int HEIGHT = 720;

	private static Logger logger = LogManager.getLogger("Zerra");
	private static Zerra instance = new Zerra();
	private static final Timer TIMER = new Timer(60);

	private TextureManager textureManager;
	private MasterRenderer renderer;
	private Camera camera;
	private ThreadPool pool;

	private World world;

	private boolean running;

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
		Display.setIcon(LoadingUtils.loadImage("icon", new ResourceLocation("icons/32.png").getInputStream()));

		GlWrapper.enableDepth();
		GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
		PostProcessing.init();

		textureManager = new TextureManager();
		renderer = new MasterRenderer();
		camera = new Camera();
		pool = new ThreadPool(4);

		EntityRegistry.register("player", EntityPlayer.class);
		EntityRegistry.register("firefly", EntityFirefly.class);

		world = new World();
		world.add(new EntityPlayer());

		logger.info("Loading game...");
		/** This is where game loading is first called. */
		load();
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

		while (true) {
			try {
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
			} catch (Exception e) {
				e.printStackTrace();
				stop();
			}
			cleanUp();
			break;
		}
	}

	/**
	 * Updates game logic and events.
	 */
	private void update() {
		world.update();
		camera.update();

		if (Display.getMouseButton() == 2) {
			world.add(new EntityFirefly());
		}

		if (Display.isKeyPressed(GLFW.GLFW_KEY_MINUS)) {
			renderer.setScale(MasterRenderer.scale - 1f);
		}

		if (Display.isKeyPressed(GLFW.GLFW_KEY_EQUAL)) {
			renderer.setScale(MasterRenderer.scale + 1f);
		}
		if(MasterRenderer.scale < 3)
			renderer.setScale(3);
	}

	/**
	 * Renders game.
	 */
	private void render() {
		/** Debug Code */
		renderer.renderLights(new Light(new Vector2f((float) Display.getMouseX() / MasterRenderer.scale + camera.getPosition().x, (float) Display.getMouseY() / MasterRenderer.scale + camera.getPosition().y), new Vector4f(1, 1, 1, 50), 25));
		/** Actual code that will stay */
		world.render(renderer, camera, TIMER.renderPartialTicks);
		renderer.render(camera, TIMER.renderPartialTicks);
	}

	/**
	 * Cleans up the game before the game loop has closed.
	 */
	private void cleanUp() {
		this.addTask(() -> {
			this.save();
		});
		Display.destroy();
		logger.info("Closing Current Processes");
		StopWatch watch = StopWatch.createStarted();
		pool.join();
		logger.info("Closed Processes in " + watch.getTime(TimeUnit.MILLISECONDS) + "ms");
		logger.info("Cleaning up resources");
		PostProcessing.cleanUp();
		Loader.cleanUp();
		renderer.cleanUp();
		logger.info("Closing game...");
		System.exit(0);
	}

	/**
	 * Saves current game.
	 */
	private void save() {
		try {
			File saveFolder = new File("data/saves");
			if (!saveFolder.exists()) {
				saveFolder.mkdirs();
			}
			world.save(saveFolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads up the game.
	 */
	private void load() {
		try {
			File saveFolder = new File("data/saves");
			if (saveFolder.exists()) {
				world.load(saveFolder);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		world.generate();
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