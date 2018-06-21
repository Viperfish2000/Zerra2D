package com.zerra.gfx.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;

import com.zerra.game.world.tile.Tile;
import com.zerra.gfx.shader.TileShader;
import com.zerra.util.Display;
import com.zerra.util.ResourceLocation;

public class MasterRenderer {

	private static final float scale = 4;
	private static final Matrix4f projectionMatrix = new Matrix4f().ortho(0, Display.getWidth() / scale, Display.getHeight() / scale, 0, 0.3f, 1000f);

	private TileShader tileShader;
	private TileRenderer tileRenderer;

	private Map<ResourceLocation, List<Tile>> tiles;

	public MasterRenderer() {
		this.tileShader = new TileShader();
		this.tileRenderer = new TileRenderer(tileShader);
		this.tiles = new HashMap<ResourceLocation, List<Tile>>();
	}

	public void render(List<Tile> tiles) {
		this.tileRenderer.render(this.tiles);

		this.tiles.clear();
	}

	public void cleanUp() {
		this.tileRenderer.cleanUp();
	}

	public void renderTile(Tile tile) {
		List<Tile> batch = tiles.get(tile.getTexture());
		if (batch == null) {
			batch = new ArrayList<Tile>();
			this.tiles.put(tile.getTexture(), batch);
		}
		batch.add(tile);
	}

	public static Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
}