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

	@Override
	public void update() {
	}
	
	@Override
	public Vector2f getTextureCoords() {
		return textureCoords;
	}

	@Override
	public ResourceLocation getTexture() {
		return TILE_ATLAS;
	}

	@Override
	public int getTextureWidth() {
		return 16;
	}
}
