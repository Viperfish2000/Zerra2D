package com.zerra.game.map;

import com.zerra.game.world.tile.Tile;

public enum EnumBiome {
	GRASS(0, 0.75f, Tile.GRASS), SAND(1, 0.5f, Tile.SAND), STONE(2, 0.25f, Tile.STONE);

	private int id;
	private float spawnChance;
	private Tile tile;

	private EnumBiome(int id, float spawnChance, Tile tile) {
		this.id = id;
		this.spawnChance = spawnChance;
		this.tile = tile;
	}

	public int getId() {
		return id;
	}

	public float getSpawnChance() {
		return spawnChance;
	}
	
	public Tile getTile() {
		return tile;
	}

	public static EnumBiome byId(int id) {
		return EnumBiome.values()[id % EnumBiome.values().length];
	}
}