package com.zerra.game.map;

import com.zerra.game.world.tile.Tile;

public enum EnumBiome {
	OCEAN(0, Tile.WATER), BEACH(1, Tile.SAND), DESERT(4, Tile.SAND), FOREST(2, Tile.GRASS), MOUNTAIN(3, Tile.STONE), SNOW(5, Tile.SNOW);

	private int id;
	private Tile tile;

	private EnumBiome(int id, Tile tile) {
		this.id = id;
		this.tile = tile;
	}

	public int getId() {
		return id;
	}

	public Tile getTile() {
		return tile;
	}

	public static EnumBiome byId(int id) {
		return EnumBiome.values()[id % EnumBiome.values().length];
	}
}