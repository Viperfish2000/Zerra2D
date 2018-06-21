package com.zerra.game.world.tile;

import java.awt.Graphics;

import org.joml.Vector4f;

import com.zerra.util.ResourceLocation;

public class TileBase extends Tile {

	public static final ResourceLocation TILE_ATLAS = new ResourceLocation("textures/tiles.png");

	public TileBase(float x, float y) {
		super(TILE_ATLAS);
		this.setX(x);
		this.setY(y);
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
	}

	@Override
	public Vector4f getTextureCoords() {
		return null;
	}
}
