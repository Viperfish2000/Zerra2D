package com.zerra.game.world.tile;

import java.awt.Graphics;

import org.joml.Vector2f;

import com.zerra.util.ResourceLocation;

public abstract class Tile {

	private float x, y;
	private ResourceLocation texture;
	public boolean shouldRender = false;

	public Tile(ResourceLocation texture) {
		this.texture = texture;
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract Vector2f getTextureCoords();

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
}
