package com.zerra.gfx.shader;

import org.joml.Matrix4f;
import org.joml.Vector4f;

public class GuiShader extends ShaderProgram {

	private int location_projectionMatrix;
	private int location_transformationMatrix;

	private int location_textureOffsets;

	public GuiShader() {
		super("gui");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");

		location_textureOffsets = super.getUniformLocation("textureOffsets");
	}

	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	public void loadTextureOffsets(Vector4f textureOffsets) {
		super.loadVector(location_textureOffsets, textureOffsets);
	}
}