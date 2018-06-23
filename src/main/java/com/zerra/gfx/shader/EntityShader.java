package com.zerra.gfx.shader;

import org.joml.Matrix4f;

import com.zerra.object.ICamera;
import com.zerra.util.Maths;

public class EntityShader extends ShaderProgram {

	private int location_projectionMatrix;
	private int location_transformationMatrix;
	private int location_viewMatrix;
	private int location_numberOfRows;

	private int location_entityTexture;

	public EntityShader() {
		super("entity");
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
		location_numberOfRows = super.getUniformLocation("numberOfRows");

		location_entityTexture = super.getUniformLocation("tileTexture");
	}

	public void connectTextureUnits() {
		super.loadInt(location_entityTexture, 0);
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

	public void loadNumberOfRows(int numberOfRows) {
		super.loadFloat(location_numberOfRows, numberOfRows);
	}
}