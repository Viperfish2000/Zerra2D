package com.zerra.game.item;

import org.joml.Vector2f;

import com.zerra.util.ResourceLocation;

public class BasicItem extends Item {

	public static final ResourceLocation ITEM_ATLAS = new ResourceLocation("textures/items.png");

	private Vector2f textureCoords;

	public BasicItem(String registryName, String unlocalizedName, Vector2f textureCoords) {
		super(registryName, unlocalizedName);
		this.textureCoords = textureCoords;
	}

	@Override
	public Vector2f getTextureCoords(int layer) {
		return layer == 0 ? textureCoords : null;
	}

	@Override
	public ResourceLocation getTexture() {
		return ITEM_ATLAS;
	}
	
	@Override
	public int getTextureWidth() {
		return 16;
	}
}