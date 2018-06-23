package com.zerra.game.item;

import org.joml.Vector2f;

public class BasicItem extends Item {

	private Vector2f textureCoords;

	public BasicItem(String registryName, String unlocalizedName, Vector2f textureCoords) {
		super(registryName, unlocalizedName);
		this.textureCoords = textureCoords;
	}

	@Override
	public Vector2f getTextureCoords() {
		return textureCoords;
	}
}