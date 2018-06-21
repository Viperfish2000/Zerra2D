package com.zerra.game.map;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import com.zerra.Game;
import com.zerra.game.world.tile.Tile;
import com.zerra.game.world.tile.TileBase;
import com.zerra.gfx.FrustumCullingFilter;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.util.Maths;

public class Map {

	private FrustumCullingFilter filter;
	private List<Tile> tiles;

	public Map() {
		filter = new FrustumCullingFilter();
		tiles = new ArrayList<Tile>();
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				tiles.add(new TileBase(i * 16, j * 16) {
					@Override
					public Vector2f getTextureCoords() {
						return new Vector2f(0, 1);
					}
				});
			}
		}
	}

	public void update() {
		filter.updateFrustum(MasterRenderer.getProjectionMatrix(), Maths.createViewMatrix(Game.getInstance().getCamera()));
		filter.filterTiles(tiles);
		for (int i = 0; i < tiles.size(); i++) {
			Tile tile = tiles.get(i);
			tile.update();
			if (tile.isRemoved()) {
				tiles.remove(i);
				i--;
			}
		}
	}

	public void render(MasterRenderer renderer) {
		for (int i = 0; i < tiles.size(); i++) {
			renderer.renderTile(tiles.get(i));
		}
	}
}
