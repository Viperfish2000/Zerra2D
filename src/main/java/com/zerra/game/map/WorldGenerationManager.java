package com.zerra.game.map;

import java.util.Random;

import com.zerra.game.world.tile.Tile;

public class WorldGenerationManager {

	private TileMap map;
	private Random random;
	private HeightGenerator worldGenerator;

	public WorldGenerationManager(TileMap map) {
		this.map = map;
		this.random = new Random();
		this.worldGenerator = new HeightGenerator();
	}

	public void generateTile(float x, float y) {
		Chunk chunk = map.getChunk(x, y);
		EnumBiome biome = chunk.getBiome();
		Tile tile = biome.getTile();
		generateDecorations(biome, x, y);
		map.addTile(tile, biome, 0, x, y);
	}

	private void generateDecorations(EnumBiome biome, float x, float y) {
		if (biome == EnumBiome.SAND) {
			if (worldGenerator.generateHeight(x, y, 40f, 1, 1f) > 12) {
				map.addTile(Tile.BOULDER, map.getChunk(x, y).getBiome(), 1, x, y);
			}
		}
	}

	private int[] getNumberOfBiomes(EnumBiome[] biomes) {
		int[] result = new int[EnumBiome.values().length];
		for (int i = 0; i < biomes.length; i++) {
			EnumBiome biome = biomes[i];
			result[biome.getId()]++;
		}
		return result;
	}

	public EnumBiome getBiome(int gridX, int gridY) {
		EnumBiome biome = EnumBiome.values()[random.nextInt(EnumBiome.values().length)];
		if(random.nextFloat() < biome.getSpawnChance()) {
			return biome;
		}
		return getBiome(gridX, gridY);
	}
}