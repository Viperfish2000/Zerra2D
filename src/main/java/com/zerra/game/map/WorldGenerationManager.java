package com.zerra.game.map;

import com.zerra.game.world.tile.Tile;

public class WorldGenerationManager {

	private TileMap map;
	private HeightGenerator worldGenerator;

	public WorldGenerationManager(TileMap map) {
		this.map = map;
		this.worldGenerator = new HeightGenerator();
	}

	public void generateTile(float x, float y) {
		float height = worldGenerator.generateHeight(x, y, 4.001f, 8, 0.3f);
		System.out.println(height);
		generateBiome(this.getBiome(height), x, y, height);
	}

	private void generateBiome(EnumBiome biome, float x, float y, float height) {
		Tile tile = biome.getTile();
		if (biome == EnumBiome.DESERT) {
			if (worldGenerator.generateHeight(x, y, 40f, 1, 1f) < 13) {
				map.addTile(Tile.BOULDER, biome, 1, x, y);
			}
		}

		if (biome == EnumBiome.MOUNTAIN && height > 3.85) {
			tile = Tile.SNOW;
		}

		map.addTile(tile, biome, 0, x, y);
	}

	private EnumBiome getBiome(float random) {
		if (random < 2.8)
			return EnumBiome.OCEAN;
		else if (random < 2.95)
			return EnumBiome.BEACH;
		else if (random < 3.6)
			return EnumBiome.FOREST;
		else
			return EnumBiome.MOUNTAIN;
	}

	public long getRandomSeed() {
		return this.worldGenerator.getRandomSeed();
	}

	public int getSeed() {
		return this.worldGenerator.getSeed();
	}

	public void setSeeds(long randomSeed, int seed) {
		this.worldGenerator = new HeightGenerator(randomSeed, seed);
	}
}