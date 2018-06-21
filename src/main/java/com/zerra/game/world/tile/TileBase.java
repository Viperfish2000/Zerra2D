package com.zerra.game.world.tile;

import java.awt.Graphics;

import org.joml.Vector4f;

import com.zerra.Game;
import com.zerra.util.ResourceLocation;

public abstract class TileBase extends Tile {

	public static final ResourceLocation TILE_ATLAS = new ResourceLocation("textures/tiles.png");

	public TileBase(float x, float y) {
		super(TILE_ATLAS);
		this.setX(x);
		this.setY(y);
	}

	@Override
	public void tick() {
		this.setX(this.getX() + Game.manager.getPlayerMover().getVelX());
		this.setY(this.getY() + Game.manager.getPlayerMover().getVelY());
	}

	@Override
	public void render(Graphics g) {
	}

	public abstract Vector4f getTextureCoords();
}
