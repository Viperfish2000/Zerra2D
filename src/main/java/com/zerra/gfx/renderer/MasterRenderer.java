package com.zerra.gfx.renderer;

import java.util.List;

import com.zerra.game.world.tile.Tile;
import com.zerra.gfx.shader.TileShader;

public class MasterRenderer {

	private TileShader tileShader;
	private TileRenderer tileRenderer;

	public MasterRenderer() {
		this.tileShader = new TileShader();
		this.tileRenderer = new TileRenderer(tileShader);
	}

	public void render(List<Tile> tiles) {
		this.tileRenderer.render(tiles.get(0), null);
	}

	public void cleanUp() {
		this.tileRenderer.cleanUp();
	}
}