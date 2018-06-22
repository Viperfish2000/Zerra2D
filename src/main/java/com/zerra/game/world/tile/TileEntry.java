package com.zerra.game.world.tile;

public class TileEntry {

	private Tile tile;
	private float x;
	private float y;
	private boolean removed;
	
	public TileEntry(Tile tile, float x, float y) {
		this.tile = tile;
		this.x = x;
		this.y = y;
		this.removed = false;
	}
	
	public Tile getTile() {
		return tile;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
}