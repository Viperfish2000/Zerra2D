package com.zerra.game.world.tile;

import org.joml.Vector2f;

public class TileRock extends TileBase {

	public TileRock(float x, float y) {
		super(x, y);
	}

	@Override
	public Vector2f getTextureCoords() {
		return new Vector2f(1, 0);
	}
}
