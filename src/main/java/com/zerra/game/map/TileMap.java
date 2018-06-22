package com.zerra.game.map;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

	public static final int CHUNK_SIZE = 100;

	private File worldFolder;
	private FrustumCullingFilter filter;

	private ByteDataContainer chunksList;
	private List<Chunk> chunks;
	private List<TileEntry> tiles;

	private float width;
	private float height;

	public TileMap() {
		filter = new FrustumCullingFilter();
		chunksList = new ByteDataContainer();
		chunks = new ArrayList<Chunk>();
		tiles = new ArrayList<TileEntry>();
		width = Display.getWidth() / MasterRenderer.SCALE / 16;
		height = Display.getHeight() / MasterRenderer.SCALE / 16;
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
				this.getChunk(tile.getX(), tile.getY()).getContainer().setString(tile.getX() + "," + tile.getY(), tile.getTile().getRegistryName());
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
	}

	public void render(MasterRenderer renderer) {
		for (int i = 0; i < tiles.size(); i++) {
			renderer.renderTile(tiles.get(i));
		}
	}
	
	public void generate() {
		for (int x = -1; x < width + 2; x++) {
			for (int y = -1; y < height + 2; y++) {
				if (getTile(x * 16, y * 16) == null)
					this.addTile(Tile.SAND, x * 16, y * 16);
			}
		}
	}

	public void save(File saveFolder) throws IOException {
		String worldName = "world";// TODO add different names so you can have multiple saves

		worldFolder = new File(saveFolder, worldName);
		File tileDataFile = new File(worldFolder, "tiles.bit");
		if (!worldFolder.exists()) {
			worldFolder.mkdirs();
		}
		tileDataFile.createNewFile();

		ByteDataContainer chunks = new ByteDataContainer();
		for (Chunk chunk : this.chunks) {
			UUID chunkName = chunk.getId();
			ByteDataContainer chunkData = new ByteDataContainer();
			chunkData.setUUID("chunkName", chunkName);
			File file = new File(worldFolder, "chunks/" + chunkName.toString());
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			chunks.setTag(chunk.getGridX() + "," + chunk.getGridY(), chunkData);
			DataOutputStream tileStream = new DataOutputStream(new FileOutputStream(file));
			chunk.getContainer().write(tileStream);
		}
		DataOutputStream tileStream = new DataOutputStream(new FileOutputStream(tileDataFile));
		chunks.write(tileStream);
	}

	public void load(File saveFolder) throws IOException {
		String worldName = "world";// TODO add different names so you can have multiple saves

		worldFolder = new File(saveFolder, worldName);
		File tileDataFile = new File(worldFolder, "tiles.bit");
		if (tileDataFile.exists()) {
			DataInputStream tileStream = new DataInputStream(new FileInputStream(tileDataFile));
			chunksList.read(tileStream);
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
		if (this.getChunk(x, y).getContainer().getString(x + "," + y) != null) {
			tile = Tile.byName(this.getChunk(x, y).getContainer().getString(x + "," + y));
		}
		tiles.add(new TileEntry(tile, x, y));
	}

	private Chunk getChunk(float x, float y) {
		int gridX = (int) (x / CHUNK_SIZE / 16);
		int gridY = (int) (y / CHUNK_SIZE / 16);
		for (Chunk chunk : chunks) {
			if (chunk.getGridX() == gridX && chunk.getGridY() == gridY)
				return chunk;
		}
		Chunk chunk = null;
		try {
			chunk = this.tryLoadingChunk(gridX, gridY);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (chunk == null) {
			Game.logger().info("Created new chunk at " + gridX + "," + gridY);
			chunk = new Chunk(UUID.randomUUID(), gridX, gridY);
		}
		chunks.add(chunk);
		return chunk;
	}

	@Nullable
	private Chunk tryLoadingChunk(int gridX, int gridY) throws IOException {
		if (chunksList.getTag(gridX + "," + gridY) != null) {
			UUID chunkId = chunksList.getByteContainer(gridX + "," + gridY).getUUID("chunkName");
			if (chunkId != null) {
				File chunkFile = new File(worldFolder, "chunks/" + chunkId);
				if (chunkFile.exists()) {
					Chunk chunk = new Chunk(chunkId, gridX, gridY);
					DataInputStream stream = new DataInputStream(new FileInputStream(chunkFile));
					chunk.getContainer().read(stream);
					return chunk;
				}
			}
		}
		return null;
	}
}
