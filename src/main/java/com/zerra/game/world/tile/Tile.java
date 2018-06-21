package com.zerra.game.world.tile;

import java.awt.Graphics;

import org.joml.Vector2f;

import com.zerra.util.AxisAlignedBB;
import com.zerra.util.ResourceLocation;

public abstract class Tile {

	private boolean removed;
	private float x, y;
	private ResourceLocation texture;
	public boolean shouldRender;

	public Tile(ResourceLocation texture) {
		this.texture = texture;
	}

	public abstract void update();

	public abstract Vector2f getTextureCoords();

	public AxisAlignedBB getCollisionBox() {
		return new AxisAlignedBB(0, 0, 16, 16);
	}

	public ResourceLocation getTexture() {
		return texture;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
}
