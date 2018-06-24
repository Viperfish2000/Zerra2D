package com.zerra.game.world.map;

import com.zerra.game.world.tile.Tile;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * Handles generating tiles into the world based on many factors.
 * 
 * @author Ocelot5836
 */
public class WorldGenerationManager {

	private TileMap map;
	private HeightGenerator worldGenerator;

	public WorldGenerationManager(TileMap map) {
		this.map = map;
		this.worldGenerator = new HeightGenerator();
	}

	/**
	 * Generates a tile based on the height.
	 * 
	 * @param x
	 *            The x position the tile will be generated at.
	 * 
	 * @param y
	 *            The y position the tile will be generated at.
	 */
	public void generateTile(int x, int y) {
		float height = worldGenerator.generateHeight(x, y, 4f, 8, 0.3f);
		generateBiome(this.getBiome(height), x, y, height);
	}

	/**
	 * Generates a biome.
	 * 
	 * @param biome
	 *            The biome that will be generated.
	 * 
	 * @param x
	 *            The x position of the tile the biome will be at.
	 * 
	 * @param y
	 *            The y position of the tile the biome will be at.
	 * 
	 * @param height
	 *            The height used in determining generation.
	 */
	private void generateBiome(EnumBiome biome, int x, int y, float height) {
		Tile tile = biome.getTile();
		if (biome == EnumBiome.DESERT) {
			if (worldGenerator.generateHeight(x, y, 40f, 1, 1f) < 13) {
				map.addTile(Tile.BOULDER, biome, 1, x, y);
			}
		}

		if (biome == EnumBiome.MOUNTAIN && height > 3.85) {
			tile = Tile.SNOW;
		}

		if (tile == Tile.WATER) {
			tile = Tile.SAND;
			map.addTile(Tile.WATER, biome, 1, x, y);
		}

		map.addTile(tile, biome, 0, x, y);
	}

	/**
	 * 
	 * 
	 * @param height
	 *            The height value from the heightmap.
	 * 
	 * @return The biome type based on the height value.
	 */
	private EnumBiome getBiome(float height) {
		if (height < 2.8)
			return EnumBiome.OCEAN;
		else if (height < 2.95)
			return EnumBiome.BEACH;
		else if (height < 3.6)
			return EnumBiome.FOREST;
		else
			return EnumBiome.MOUNTAIN;
	}

	/**
	 * @return The world's random object seed
	 */
	public long getRandomSeed() {
		return this.worldGenerator.getRandomSeed();
	}

	/**
	 * @return The seed for the actual world generation
	 */
	public int getSeed() {
		return this.worldGenerator.getSeed();
	}

	/**
	 * Sets the seeds for the world.
	 * 
	 * @param randomSeed
	 *            Sets the world's random object seed
	 * 
	 * @param seed
	 *            Sets the seed for the actual world generation
	 */
	public void setSeeds(long randomSeed, int seed) {
		this.worldGenerator = new HeightGenerator(randomSeed, seed);
	}
}