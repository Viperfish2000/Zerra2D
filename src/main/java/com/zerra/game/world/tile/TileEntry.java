package com.zerra.game.world.tile;

import com.zerra.game.map.EnumBiome;
import com.zerra.util.ISerializable;
import com.zerra.util.data.ByteDataContainer;

public class TileEntry implements ISerializable<ByteDataContainer> {

	private Tile tile;
	private EnumBiome biome;
	private int layer;
	private float x;
	private float y;
	private boolean removed;

	public TileEntry(Tile tile, EnumBiome biome, int layer, float x, float y) {
		this.tile = tile;
		this.biome = biome;
		this.x = x;
		this.y = y;
		this.removed = false;
	}

	public Tile getTile() {
		return tile;
	}

	public EnumBiome getBiome() {
		return biome;
	}

	public int getLayer() {
		return layer;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	@Override
	public ByteDataContainer serialize() {
		ByteDataContainer container = new ByteDataContainer();
		container.setString("registryName", this.tile.getRegistryName());
		container.setByte("biome", (byte) this.biome.ordinal());
		return container;
	}

	@Override
	public void deserialize(ByteDataContainer data) {
		this.tile = Tile.byName(data.getString("registryName"));
		this.biome = EnumBiome.values()[data.getByte("biome")];
	}

	public static TileEntry fromTag(float x, float y, int layer, ByteDataContainer data) {
		TileEntry tile = new TileEntry(null, null, layer, x, y);
		tile.deserialize(data);
		return tile;
	}
}