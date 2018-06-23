package com.zerra.game.world.tile;

import org.joml.Vector2f;

import com.zerra.util.ResourceLocation;

public class TileBase extends Tile {

	public static final ResourceLocation TILE_ATLAS = new ResourceLocation("textures/tiles.png");

	private Vector2f textureCoords;

	public TileBase(String registryName, String unlocalizedName, Vector2f textureCoords) {
		super(registryName, unlocalizedName);
		this.textureCoords = textureCoords;
	}

	/**
	 * Updates the tile.
	 */
	@Override
	public void update() {
	}

	/**
	 * @return The texture coordinates for this tile.
	 */
	@Override
	public Vector2f getTextureCoords() {
		return textureCoords;
	}

	/**
	 * @return The resource location of the texture this tile uses.
	 */
	@Override
	public ResourceLocation getTexture() {
		return TILE_ATLAS;
	}
}
