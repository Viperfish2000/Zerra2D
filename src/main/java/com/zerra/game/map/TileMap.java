package com.zerra.game.map;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.joml.Vector3f;

import com.zerra.Game;
import com.zerra.game.world.tile.Tile;
import com.zerra.game.world.tile.TileEntry;
import com.zerra.gfx.FrustumCullingFilter;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.object.ICamera;
import com.zerra.util.Display;
import com.zerra.util.Maths;
import com.zerra.util.data.ByteDataContainer;

public class TileMap {

	private ByteDataContainer container;

	private FrustumCullingFilter filter;
	private List<TileEntry> tiles;

	private float width;
	private float height;

	public TileMap() {
		container = new ByteDataContainer();
		filter = new FrustumCullingFilter();
		tiles = new ArrayList<TileEntry>();
		width = Display.getWidth() / MasterRenderer.SCALE / 16;
		height = Display.getHeight() / MasterRenderer.SCALE / 16;
		for (int x = -1; x < width + 2; x++) {
			for (int y = -1; y < height + 2; y++) {
				this.addTile(Tile.SAND, x * 16, y * 16);
			}
		}
	}

	public void update() {
		ICamera camera = Game.getInstance().getCamera();
		filter.updateFrustum(MasterRenderer.getProjectionMatrix(), Maths.createViewMatrix(camera));
		filter.filterTiles(tiles);

		boolean hasRemovedTiles = false;
		for (int i = 0; i < tiles.size(); i++) {
			TileEntry tile = tiles.get(i);
			tile.getTile().update();
			if (tile.isRemoved()) {
				tiles.remove(i);
				i--;
				hasRemovedTiles = true;
				container.setString(tile.getX() + "," + tile.getY(), tile.getTile().getRegistryName());
			}
		}

		if (hasRemovedTiles) {
			Vector3f cameraPosition = camera.getPosition();
			Vector3f cameraLastPosition = camera.getLastPosition();
			float x = cameraPosition.x;
			float y = cameraPosition.y;
			float lastX = cameraLastPosition.x;
			float lastY = cameraLastPosition.y;
			if (x - lastX < 0) {
				System.out.println("LEFT");
				for (int tileX = 1; tileX < 5; tileX++) {
					for (int tileY = -1; tileY < height + 1; tileY++) {
						int xPos = (int) (tileX + (Math.ceil(x - 64) / 16));
						int yPos = (int) (y / 16 + tileY);
						if (getTile(xPos * 16, yPos * 16) == null)
							this.addTile(Tile.GRASS, xPos * 16, yPos * 16);
					}
				}
			}

			if (x - lastX > 0) {
				System.out.println("RIGHT");
				for (int tileX = 4; tileX < 7; tileX++) {
					for (int tileY = -1; tileY < height + 1; tileY++) {
						int xPos = (int) (tileX + width + (Math.ceil(x - 64) / 16));
						int yPos = (int) (y / 16 + tileY);
						if (getTile(xPos * 16, yPos * 16) == null)
							this.addTile(Tile.GRASS, xPos * 16, yPos * 16);
					}
				}
			}

			if (y - lastY < 0) {
				System.out.println("UP");
				for (int tileX = -1; tileX < width + 1; tileX++) {
					for (int tileY = -2; tileY < 0; tileY++) {
						int xPos = (int) (x / 16 + tileX);
						int yPos = (int) Math.ceil(tileY + y / 16);
						if (getTile(xPos * 16, yPos * 16) == null)
							this.addTile(Tile.GRASS, xPos * 16, yPos * 16);
					}
				}
			}

			if (y - lastY > 0) {
				System.out.println("DOWN");
				for (int tileX = -1; tileX < width + 1; tileX++) {
					for (int tileY = 1; tileY < 3; tileY++) {
						int xPos = (int) (x / 16 + tileX);
						int yPos = (int) Math.floor(tileY + height + y / 16);
						if (getTile(xPos * 16, yPos * 16) == null)
							this.addTile(Tile.GRASS, xPos * 16, yPos * 16);
					}
				}
			}
		}
		System.out.println(container.getSize());
	}

	public void render(MasterRenderer renderer) {
		for (int i = 0; i < tiles.size(); i++) {
			renderer.renderTile(tiles.get(i));
		}
	}

	@Nullable
	public Tile getTile(float x, float y) {
		for (int i = 0; i < tiles.size(); i++) {
			TileEntry tile = tiles.get(i);
			if (tile.getX() == x && tile.getY() == y) {
				return tile.getTile();
			}
		}
		return null;
	}

	private void addTile(Tile tile, float x, float y) {
		if(container.getString(x + "," + y) != null) {
			tile = Tile.byName(container.getString(x + "," + y));
		}
		tiles.add(new TileEntry(tile, x, y));
	}
}
