package com.zerra.gfx.gui;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import com.zerra.Zerra;
import com.zerra.gfx.texture.ITexture;
import com.zerra.util.ResourceLocation;

public class GuiTexture {

	private ITexture texture;
	private Vector4f textureOffsets;
	private Matrix4f transformation;

	protected GuiTexture(ResourceLocation texture, Vector4f textureOffsets, Matrix4f transformation) {
		Zerra.getInstance().getTextureManager().bind(texture);
		this.texture = Zerra.getInstance().getTextureManager().getTexture(texture);
		this.textureOffsets = textureOffsets;
		this.transformation = transformation;
	}

	protected GuiTexture(ITexture texture, Vector4f textureOffsets, Matrix4f transformation) {
		this.texture = texture;
		this.textureOffsets = textureOffsets;
		this.transformation = transformation;
	}

	public ITexture getTexture() {
		return texture;
	}
	
	public Vector4f getTextureOffsets() {
		return textureOffsets;
	}

	public Matrix4f getTransformation() {
		return transformation;
	}
}