package com.zerra.game.world.tile;

import com.zerra.annotation.Review;
import com.zerra.game.world.map.EnumBiome;
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

	/**
	 * @return The tile in this tile entry.
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * @return The biome of this tile entry.
	 */
	public EnumBiome getBiome() {
		return biome;
	}

	/**
	 * @return The layer of this tile entry.
	 */
	public int getLayer() {
		return layer;
	}

	/**
	 * @return The x position of this tile entry.
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return The y position of this tile entry.
	 */
	public float getY() {
		return y;
	}

	/**
	 * @return Whether or not this tile entry is removed.
	 */
	@Review(desc = "What does removed mean in this context??")
	public boolean isRemoved() {
		return removed;
	}

	/**
	 * Sets the x position for the tile in this tile entry.
	 * 
	 * @param x
	 *            The x position to set the tile at.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Sets the y position for the tile in this tile entry.
	 * 
	 * @param y
	 *            The y position to set the tile at.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Sets whether or not this tile is removed.
	 * 
	 * @param removed
	 *            Whether or not the tile is removed.
	 */
	@Review(desc = "What does removed mean in this context??")
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	/**
	 * Writes tile entry data to a byte stream, to be stored in chunk data as binary.
	 */
	@Override
	public ByteDataContainer serialize() {
		ByteDataContainer container = new ByteDataContainer();
		container.setString("rn", this.tile.getRegistryName());
		container.setByte("b", (byte) this.biome.ordinal());
		return container;
	}

	/**
	 * Reads tile entry data from chunk files, to be loaded into the world once more.
	 */
	@Override
	public void deserialize(ByteDataContainer data) {
		this.tile = Tile.byName(data.getString("rn"));
		this.biome = EnumBiome.values()[data.getByte("b")];
	}

	/**
	 * Retrieves a tile entry based on a tag.s
	 * 
	 * @param x
	 *            The x position of the tile.
	 * 
	 * @param y
	 *            The y position of the tile.
	 * 
	 * @param layer
	 *            The number of layers the tile has.
	 * 
	 * @param data
	 *            The serialized data to deserialize. This gets the rest of the data a tile has, which includes biome type, tile type, etc.
	 * @return
	 */
	public static TileEntry fromTag(float x, float y, int layer, ByteDataContainer data) {
		TileEntry tile = new TileEntry(null, null, layer, x, y);
		tile.deserialize(data);
		return tile;
	}
}