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
	private WorldGenerationManager worldGenerator;
	private FrustumCullingFilter filter;

	private ByteDataContainer chunksList;
	private List<Chunk> chunks;
	private List<TileEntry> tiles;

	private float width;
	private float height;

	public TileMap() {
		worldGenerator = new WorldGenerationManager(this);
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
				this.getChunk(tile.getX(), tile.getY()).getTileData().setTag(tile.getX() + "," + tile.getY() + "," + tile.getLayer(), tile.serialize());
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
							this.worldGenerator.generateTile(xPos * 16, yPos * 16);
					}
				}
			}

			if (x - lastX > 0) {
				for (int tileX = 4; tileX < 7; tileX++) {
					for (int tileY = -1; tileY < height + 1; tileY++) {
						int xPos = (int) (tileX + width + (Math.ceil(x - 64) / 16));
						int yPos = (int) (y / 16 + tileY);
						if (getTile(xPos * 16, yPos * 16) == null)
							this.worldGenerator.generateTile(xPos * 16, yPos * 16);
					}
				}
			}

			if (y - lastY < 0) {
				for (int tileX = -1; tileX < width + 1; tileX++) {
					for (int tileY = -2; tileY < 0; tileY++) {
						int xPos = (int) (x / 16 + tileX);
						int yPos = (int) Math.ceil(tileY + y / 16);
						if (getTile(xPos * 16, yPos * 16) == null)
							this.worldGenerator.generateTile(xPos * 16, yPos * 16);
					}
				}
			}

			if (y - lastY > 0) {
				for (int tileX = -1; tileX < width + 1; tileX++) {
					for (int tileY = 1; tileY < 3; tileY++) {
						int xPos = (int) (x / 16 + tileX);
						int yPos = (int) Math.floor(tileY + height + y / 16);
						if (getTile(xPos * 16, yPos * 16) == null)
							this.worldGenerator.generateTile(xPos*16, yPos*16);
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
					this.worldGenerator.generateTile(x * 16, y * 16);
			}
		}
	}

	public void save(File saveFolder) throws IOException {
		String worldName = "world";// TODO add different names so you can have multiple saves

		/** The folder the world is actually in */
		worldFolder = new File(saveFolder, worldName);
		/** The file the chunk files are able to be identified. ex. (0,0=741261b0-b0e9-4466-9df5-0c7f76101925) */
		File tileDataFile = new File(worldFolder, "tiles.bit");
		/** Creates the world folder if it does not yet exist */
		if (!worldFolder.exists()) {
			worldFolder.mkdirs();
		}
		/** Creates the tile data file and clears it if it already exists */
		tileDataFile.createNewFile();

		/** This is used to write the position and id of the chunks */
		ByteDataContainer chunks = new ByteDataContainer();
		for (Chunk chunk : this.chunks) {
			/** The id is used as the file name for the chunk */
			UUID chunkName = chunk.getId();
			/** The chunk data is used to specify chunk specific data */
			ByteDataContainer chunkData = new ByteDataContainer();
			/** Sets the name of the chunk file and associates it with a position */
			chunkData.setUUID("chunkName", chunkName);
			/** Sets the biome of the chunk */
			chunkData.setByte("biome", (byte) chunk.getBiome().ordinal());
			/** Creates the chunk file and folder if they don't exist */
			File file = new File(worldFolder, "chunks/" + chunkName.toString());
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			/** Sets the chunk data to the chunk pos */
			chunks.setTag(chunk.getGridX() + "," + chunk.getGridY(), chunkData);
			/** Writes the chunk tile data to file */
			DataOutputStream tileStream = new DataOutputStream(new FileOutputStream(file));
			chunk.getTileData().write(tileStream);
		}
		/** Writes the chunk identifiers to file */
		DataOutputStream tileStream = new DataOutputStream(new FileOutputStream(tileDataFile));
		chunks.write(tileStream);
	}

	public void load(File saveFolder) throws IOException {
		String worldName = "world";// TODO add different names so you can have multiple saves

		worldFolder = new File(saveFolder, worldName);
		File tileDataFile = new File(worldFolder, "tiles.bit");
		/** Attempts to load the data file to know what files to load on the fly */
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

	protected void addTile(Tile tile, EnumBiome biome, int layer, float x, float y) {
		TileEntry entry = null;
		if (this.getChunk(x, y).getTileData().hasKey(x + "," + y, 10)) {
			entry = TileEntry.fromTag(x, y, layer, this.getChunk(x, y).getTileData().getByteContainer(x + "," + y + "," + layer));
		}
		
		if(entry == null) {
			entry = new TileEntry(tile, biome,layer, x, y);
		}
		tiles.add(entry);
	}

	/**
	 * Loads a chunk from file if it has been generated or creates a new chunk if there is no file found.
	 * 
	 * @param x
	 *            The x position to get the chunk at
	 * @param y
	 *            The y position to get the chunk at
	 * @return The chunk that was either loaded or created
	 */
	protected Chunk getChunk(float x, float y) {
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
			chunk = new Chunk(UUID.randomUUID(), worldGenerator.getBiome(gridX, gridY), gridX, gridY);
		}
		chunks.add(chunk);
		return chunk;
	}

	/**
	 * Attempts to load a single chunk from file.
	 * 
	 * @param gridX
	 *            The grid x position of the chunk
	 * @param gridY
	 *            The grid y position of the chunk
	 * @return The chunk that was loaded or null if the chunk could not be loaded
	 * @throws IOException
	 *             If anything goes wrong when trying to load the chunk file
	 */
	@Nullable
	private Chunk tryLoadingChunk(int gridX, int gridY) throws IOException {
		ByteDataContainer container = chunksList.getByteContainer(gridX + "," + gridY);
		if (container != null) {
			UUID chunkId = container.getUUID("chunkName");
			EnumBiome biome = EnumBiome.values()[container.getByte("biome")];
			if (chunkId != null) {
				File chunkFile = new File(worldFolder, "chunks/" + chunkId);
				if (chunkFile.exists()) {
					Chunk chunk = new Chunk(chunkId, biome, gridX, gridY);
					DataInputStream stream = new DataInputStream(new FileInputStream(chunkFile));
					chunk.getTileData().read(stream);
					return chunk;
				}
			}
		}
		return null;
	}
}
