package com.zerra.gfx.post.bloom;

import com.zerra.gfx.shader.ShaderProgram;

public class BrightFilterShader extends ShaderProgram {

	public BrightFilterShader() {
		super("post/bloom/simple_vert", "post/bloom/bright_frag");
	}

	@Override
	protected void getAllUniformLocations() {
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}