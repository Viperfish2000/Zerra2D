package com.zerra.gfx.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;

import com.zerra.game.world.tile.TileEntry;
import com.zerra.gfx.light.Light;
import com.zerra.gfx.shader.LightShader;
import com.zerra.gfx.shader.TileShader;
import com.zerra.object.ICamera;
import com.zerra.util.Display;
import com.zerra.util.ResourceLocation;

public class MasterRenderer {

	public static final float SCALE = 3;
	private static final Matrix4f projectionMatrix = new Matrix4f().ortho(0, Display.getWidth() / SCALE, Display.getHeight() / SCALE, 0, 0.3f, 1000f);

	private TileShader tileShader;
	private TileRenderer tileRenderer;

	private LightShader lightShader;
	private LightRenderer lightRenderer;

	private Map<ResourceLocation, List<TileEntry>> tiles;
	private List<Light> lights;

	public MasterRenderer() {
		this.tileShader = new TileShader();
		this.tileRenderer = new TileRenderer(tileShader);
		this.lightShader = new LightShader();
		this.lightRenderer = new LightRenderer(lightShader);
		this.tiles = new HashMap<ResourceLocation, List<TileEntry>>();
		this.lights = new ArrayList<Light>();
	}

	public void render(ICamera camera) {
		this.tileRenderer.render(this.tiles, camera);
		this.lightRenderer.render(lights, camera);

		this.tiles.clear();
	}

	public void cleanUp() {
		this.tileRenderer.cleanUp();
		this.lightRenderer.cleanUp();
	}

	public void renderTile(TileEntry tile) {
		ResourceLocation texture = tile.getTile().getTexture();
		List<TileEntry> batch = tiles.get(texture);
		if (batch == null) {
			batch = new ArrayList<TileEntry>();
			this.tiles.put(texture, batch);
		}
		batch.add(tile);
	}

	public void renderLights(Light... lights) {
		for (int i = 0; i < lights.length; i++) {
			this.lights.add(lights[i]);
		}
	}

	public static Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
}