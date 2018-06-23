package com.zerra.game.world.map;

import com.zerra.game.world.tile.Tile;

public enum EnumBiome {
	OCEAN(0, Tile.WATER), BEACH(1, Tile.SAND), DESERT(4, Tile.SAND), FOREST(2, Tile.GRASS), MOUNTAIN(3, Tile.STONE), SNOW(5, Tile.SNOW);

	private int id;
	private Tile tile;

	private EnumBiome(int id, Tile tile) {
		this.id = id;
		this.tile = tile;
	}

	/**
	 * @return The ID of the biome type.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return The base tile of the biome.
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * Gets the biome type by ID.
	 * 
	 * @param id
	 * 		The ID of the biome type that is to be retrieved.
	 * 
	 * @return The biome type from the ID.
	 */
	public static EnumBiome byId(int id) {
		return EnumBiome.values()[id % EnumBiome.values().length];
	}
}