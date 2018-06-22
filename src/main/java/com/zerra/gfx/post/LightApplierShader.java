package com.zerra.gfx.post;

import com.zerra.gfx.shader.ShaderProgram;

public class LightApplierShader extends ShaderProgram {

	private int location_colorTexture;
	private int location_lightTexture;

	public LightApplierShader() {
		super("post/lightapplier");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_colorTexture = super.getUniformLocation("colorTexture");
		location_lightTexture = super.getUniformLocation("lightTexture");
	}

	public void connectTextureUnits() {
		super.loadInt(location_colorTexture, 0);
		super.loadInt(location_lightTexture, 1);
	}
}