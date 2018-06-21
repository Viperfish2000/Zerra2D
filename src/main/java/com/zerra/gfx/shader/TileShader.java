package com.zerra.gfx.shader;

import org.joml.Matrix4f;

import com.zerra.gfx.ICamera;
import com.zerra.util.Maths;

public class TileShader extends ShaderProgram {

	private int location_projectionMatrix;
	private int location_transformationMatrix;
	private int location_viewMatrix;

	public TileShader() {
		super("tile");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
	}

	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	public void loadViewMatrix(ICamera camera) {
		super.loadMatrix(location_viewMatrix, Maths.createViewMatrix(camera));
	}
}