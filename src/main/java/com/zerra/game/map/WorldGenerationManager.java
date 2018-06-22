package com.zerra.game.map;

import java.util.Random;

import com.zerra.game.world.tile.Tile;

public class WorldGenerationManager {

	private TileMap map;
	private Random random;

	public WorldGenerationManager(TileMap map) {
		this.map = map;
		this.random = new Random();
	}

	public void generateTile(float x, float y) {
		Chunk chunk = map.getChunk(x, y);
		map.addTile(chunk.getBiome() == EnumBiome.SAND ? Tile.SAND : Tile.GRASS, x, y);
	}
	
	public EnumBiome getBiome(int gridX, int gridY) {
		return EnumBiome.values()[random.nextInt(EnumBiome.values().length)];
	}
}