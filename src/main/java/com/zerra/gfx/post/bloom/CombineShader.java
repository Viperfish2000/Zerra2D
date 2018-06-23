package com.zerra.gfx.post.bloom;

import com.zerra.gfx.shader.ShaderProgram;

public class CombineShader extends ShaderProgram {

	private int location_colourTexture;
	private int location_highlightTexture;

	protected CombineShader() {
		super("post/bloom/simple_vert", "post/bloom/combine_frag");
	}

	@Override
	protected void getAllUniformLocations() {
		location_colourTexture = super.getUniformLocation("colourTexture");
		location_highlightTexture = super.getUniformLocation("highlightTexture");
	}

	protected void connectTextureUnits() {
		super.loadInt(location_colourTexture, 0);
		super.loadInt(location_highlightTexture, 1);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}