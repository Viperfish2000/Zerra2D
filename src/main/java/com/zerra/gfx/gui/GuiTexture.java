package com.zerra.gfx.gui;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import com.zerra.Zerra;
import com.zerra.gfx.texture.ITexture;
import com.zerra.util.ResourceLocation;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * A texture that is used to represent a single quad in a gui.
 * 
 * @author Ocelot5836
 */
public class GuiTexture {

	private ITexture texture;
	private Vector4f textureOffsets;
	private float textureSize;
	private Matrix4f transformation;

	protected GuiTexture(ResourceLocation texture, Vector4f textureOffsets, float textureSize, Matrix4f transformation) {
		Zerra.getInstance().getTextureManager().bind(texture);
		this.texture = Zerra.getInstance().getTextureManager().getTexture(texture);
		this.textureOffsets = textureOffsets;
		this.textureSize = textureSize;
		this.transformation = transformation;
	}

	protected GuiTexture(ITexture texture, Vector4f textureOffsets,float textureSize,  Matrix4f transformation) {
		this.texture = texture;
		this.textureOffsets = textureOffsets;
		this.textureSize = textureSize;
		this.transformation = transformation;
	}

	/**
	 * @return The actual texture of this quad. {@link ITexture} is used so you can render an image that is created in opengl if you wish
	 */
	public ITexture getTexture() {
		return texture;
	}
	
	/**
	 * @return The offsets used in the shaders when rendering the quad
	 */
	public Vector4f getTextureOffsets() {
		return textureOffsets;
	}
	
	/**
	 * @return The size of the texture
	 */
	public float getTextureSize() {
		return textureSize;
	}

	/**
	 * @return The transformation matrix for this quad
	 */
	public Matrix4f getTransformation() {
		return transformation;
	}
}