package com.zerra.game.world.tile;

import org.joml.Vector2f;

import com.zerra.util.ResourceLocation;

public class TileBase extends Tile {

	public static final ResourceLocation TILE_ATLAS = new ResourceLocation("textures/tiles.png");

	public TileBase(float x, float y) {
		super(TILE_ATLAS);
		this.setX(x);
		this.setY(y);
	}

	@Override
	public void update() {
	}

	@Override
	public Vector2f getTextureCoords() {
		return new Vector2f();
	}
}
