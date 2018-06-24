package com.zerra.game.world.map;

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

import com.zerra.Zerra;
import com.zerra.game.world.tile.Tile;
import com.zerra.game.world.tile.TileEntry;
import com.zerra.gfx.FrustumCullingFilter;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.object.ICamera;
import com.zerra.util.Display;
import com.zerra.util.Maths;
import com.zerra.util.data.ByteDataContainer;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * Handles all the tiles in a level.
 * 
 * @author Ocelot5836, Hypeirochus
 */
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

	/**
	 * Updates the world tiles. Manages whether or not tiles should be loaded/unloaded based on their location on/off screen.
	 */
	public void update() {
		ICamera camera = Zerra.getInstance().getCamera();
		filter.updateFrustum(MasterRenderer.getProjectionMatrix(), Maths.createViewMatrix(camera));
		filter.filterTiles(tiles);

		for (int i = 0; i < tiles.size(); i++) {
			TileEntry tile = tiles.get(i);
			tile.getTile().update();
			if (tile.isRemoved()) {
				tiles.remove(i);
				i--;
				tile.getTile().onTileDestroyed(this, tile.getX(), tile.getY());
				this.getChunk(tile.getX(), tile.getY()).getTileData().setTag(tile.getX() + "," + tile.getY() + "," + tile.getLayer(), tile.serialize());
			}
		}

		Vector3f position = camera.getPosition();
		for (int x = (int) Math.floor(position.x / 16 - 1); x < position.x / 16 + width + 2; x++) {
			for (int y = (int) Math.floor(position.y / 16 - 1); y < (int) position.y / 16 + height + 2; y++) {
				if (getTile(x * 16, y * 16) == null)
					this.worldGenerator.generateTile(x * 16, y * 16);
			}
		}

		// if (hasRemovedTiles) {
		// Vector3f cameraDirection = camera.getDirection();
		// float x = camera.getPosition().x;
		// float y = camera.getPosition().y;
		// if (cameraDirection.x < 0) {
		// for (int tileX = 1; tileX < 5; tileX++) {
		// for (int tileY = -1; tileY < height + 1; tileY++) {
		// int xPos = (int) (tileX + (Math.ceil(x - 64) / 16));
		// int yPos = (int) (y / 16 + tileY);
		// if (getTile(xPos * 16, yPos * 16) == null)
		// this.worldGenerator.generateTile(xPos * 16, yPos * 16);
		// }
		// }
		// }
		//
		// if (cameraDirection.x > 0) {
		// for (int tileX = 4; tileX < 7; tileX++) {
		// for (int tileY = -1; tileY < height + 1; tileY++) {
		// int xPos = (int) (tileX + width + (Math.ceil(x - 64) / 16));
		// int yPos = (int) (y / 16 + tileY);
		// if (getTile(xPos * 16, yPos * 16) == null)
		// this.worldGenerator.generateTile(xPos * 16, yPos * 16);
		// }
		// }
		// }
		//
		// if (cameraDirection.y < 0) {
		// for (int tileX = -1; tileX < width + 1; tileX++) {
		// for (int tileY = -2; tileY < 0; tileY++) {
		// int xPos = (int) (x / 16 + tileX);
		// int yPos = (int) Math.ceil(tileY + y / 16);
		// if (getTile(xPos * 16, yPos * 16) == null)
		// this.worldGenerator.generateTile(xPos * 16, yPos * 16);
		// }
		// }
		// }
		//
		// if (cameraDirection.y > 0) {
		// for (int tileX = -1; tileX < width + 1; tileX++) {
		// for (int tileY = 1; tileY < 3; tileY++) {
		// int xPos = (int) (x / 16 + tileX);
		// int yPos = (int) Math.floor(tileY + height + y / 16);
		// if (getTile(xPos * 16, yPos * 16) == null)
		// this.worldGenerator.generateTile(xPos * 16, yPos * 16);
		// }
		// }
		// }
		// }
	}

	/**
	 * Updates the rendering for all tiles on screen.
	 * 
	 * @param renderer
	 *            The master renderer used to update tile rendering.
	 */
	public void render(MasterRenderer renderer) {
		for (int i = 0; i < tiles.size(); i++) {
			renderer.renderTile(tiles.get(i));
		}
	}

	/**
	 * Saves the world (how heroic sounding).
	 * 
	 * @param saveFolder
	 *            The location to save the world to.
	 * 
	 * @throws IOException
	 *             Potentially thrown due to world files not being found.
	 */
	public void save(File saveFolder, String worldName) throws IOException {
		/** The folder the world is actually in */
		worldFolder = new File(saveFolder, worldName);
		/** The file the chunk files are able to be identified. ex. (0,0=741261b0-b0e9-4466-9df5-0c7f76101925) */
		File tileDataFile = new File(worldFolder, "tiles.bit");
		/** The file that defines what id corresponds to each tile */
		File tileIndexFile = new File(worldFolder, "index.bit");
		/** Creates the world folder if it does not yet exist */
		if (!worldFolder.exists()) {
			worldFolder.mkdirs();
		}
		/** Creates the tile data file and clears it if it already exists */
		tileDataFile.createNewFile();
		/** Creates a new index file */
		tileIndexFile.createNewFile();

		/** This is used to write the position and id of the chunks */
		ByteDataContainer chunks = new ByteDataContainer();
		for (Chunk chunk : this.chunks) {
			/** The id is used as the file name for the chunk */
			UUID chunkName = chunk.getId();
			/** The chunk data is used to specify chunk specific data */
			ByteDataContainer chunkData = new ByteDataContainer();
			/** Sets the name of the chunk file and associates it with a position */
			chunkData.setUUID("chunkName", chunkName);
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

		ByteDataContainer tileIndex = new ByteDataContainer();
		tileIndex.setLong("randomSeed", worldGenerator.getRandomSeed());
		tileIndex.setInt("seed", worldGenerator.getSeed());

		DataOutputStream tileIndexStream = new DataOutputStream(new FileOutputStream(tileIndexFile));
		tileIndex.write(tileIndexStream);
	}

	/**
	 * Loads the world.
	 * 
	 * @param saveFolder
	 *            The save folder to load the world from.
	 * 
	 * @param worldName
	 *            The name of the world to load.
	 * 
	 * @throws IOException
	 *             Potentially thrown due to world files not being found.
	 */
	public void load(File saveFolder, String worldName) throws IOException {

		worldFolder = new File(saveFolder, worldName);
		File tileDataFile = new File(worldFolder, "tiles.bit");
		File tileIndexFile = new File(worldFolder, "index.bit");

		/** Attempts to load the data file to know what files to load on the fly */
		if (tileDataFile.exists()) {
			DataInputStream tileStream = new DataInputStream(new FileInputStream(tileDataFile));
			chunksList.read(tileStream);
		}

		if (tileIndexFile.exists()) {
			DataInputStream tileIndexStream = new DataInputStream(new FileInputStream(tileIndexFile));
			ByteDataContainer tileIndex = new ByteDataContainer();
			tileIndex.read(tileIndexStream);
			worldGenerator.setSeeds(tileIndex.getLong("randomSeed"), tileIndex.getInt("seed"));
		}
	}

	/**
	 * Gets a tile at the specified position.
	 * 
	 * @param x
	 *            The x position of the tile.
	 * 
	 * @param y
	 *            The y position of the tile.
	 * 
	 * @return The tile at the position.
	 */
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

	/**
	 * Places a tile by adding it to the tile list.
	 * 
	 * @param tile
	 *            The tile to add
	 * @param biome
	 *            The biome the tile is in
	 * @param layer
	 *            The layer of the tile
	 * @param x
	 *            The x position of the tile
	 * @param y
	 *            The y position of the tile
	 */
	protected void addTile(Tile tile, EnumBiome biome, int layer, int x, int y) {
		TileEntry entry = null;
		if (this.getChunk(x, y).getTileData().hasKey(x + "," + y, 10)) {
			entry = TileEntry.fromTag(x, y, layer, this.getChunk(x, y).getTileData().getByteContainer(x + "," + y + "," + layer));
		}

		if (entry == null) {
			entry = new TileEntry(tile, biome, layer, x, y);
		}
		entry.getTile().onTilePlaced(this, x, y);
		tiles.add(entry);
	}

	/**
	 * Loads a chunk from file if it has been generated or creates a new chunk if there is no file found.
	 * 
	 * @param x
	 *            The x position to get the chunk at
	 * @param y
	 *            The y position to get the chunk at
	 * 
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
			Zerra.logger().info("Created new chunk at " + gridX + "," + gridY);
			chunk = new Chunk(UUID.randomUUID(), gridX, gridY);
		}
		chunks.add(chunk);
		return chunk;
	}

	/**
	 * Attempts to load a single chunk from file.
	 * 
	 * @param gridX
	 *            The grid x position of tdhe chunk
	 * @param gridY
	 *            The grid y position of the chunk
	 * 
	 * @return The chunk that was loaded or null if the chunk could not be loaded
	 * 
	 * @throws IOException
	 *             If anything goes wrong when trying to load the chunk file
	 */
	@Nullable
	private Chunk tryLoadingChunk(int gridX, int gridY) throws IOException {
		ByteDataContainer container = chunksList.getByteContainer(gridX + "," + gridY);
		if (container != null) {
			UUID chunkId = container.getUUID("chunkName");
			if (chunkId != null) {
				File chunkFile = new File(worldFolder, "chunks/" + chunkId);
				if (chunkFile.exists()) {
					Chunk chunk = new Chunk(chunkId, gridX, gridY);
					DataInputStream stream = new DataInputStream(new FileInputStream(chunkFile));
					chunk.getTileData().read(stream);
					Zerra.logger().info("Attempting to load chunk at x: " + gridX + " y: " + gridY);
					return chunk;
				}
				Zerra.logger().error("Unable to find chunk file!");
			}
		}
		return null;
	}
}
