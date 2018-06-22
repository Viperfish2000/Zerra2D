package com.zerra.game.map;

import java.util.UUID;

import com.zerra.util.data.ByteDataContainer;

public class Chunk {

	private ByteDataContainer container;
	private UUID chunkId;
	private int gridX;
	private int gridY;

	public Chunk(UUID id, int gridX, int gridY) {
		this.container = new ByteDataContainer();
		this.chunkId = id;
		this.gridX = gridX;
		this.gridY = gridY;
	}

	public ByteDataContainer getContainer() {
		return container;
	}

	public int getGridX() {
		return gridX;
	}

	public int getGridY() {
		return gridY;
	}

	public UUID getId() {
		return chunkId;
	}
}