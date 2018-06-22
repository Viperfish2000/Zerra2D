package com.zerra.gfx.post.contrast;

import com.zerra.gfx.shader.ShaderProgram;

public class ContrastShader extends ShaderProgram {

	private int location_contrast;
	
	public ContrastShader() {
		super("post/contrast");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_contrast = super.getUniformLocation("contrast");
	}
	
	public void loadContrast(float contrast) {
		super.loadFloat(location_contrast, contrast);
	}
}