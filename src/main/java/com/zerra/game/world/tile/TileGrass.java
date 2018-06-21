package com.zerra.game.world.tile;

import org.joml.Vector2f;

public class TileGrass extends TileBase {

	public TileGrass(float x, float y) {
		super(x, y);
	}

	@Override
	public Vector2f getTextureCoords() {
		return new Vector2f(0, 0);
	}
}
