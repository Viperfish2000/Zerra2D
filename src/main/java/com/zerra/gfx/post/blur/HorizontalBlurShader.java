package com.zerra.gfx.post.blur;

import com.zerra.gfx.shader.ShaderProgram;

public class HorizontalBlurShader extends ShaderProgram {

	private int location_targetWidth;

	protected HorizontalBlurShader() {
		super("post/horizontalblur_vert", "post/blur_frag");
	}

	protected void loadTargetWidth(float width) {
		super.loadFloat(location_targetWidth, width);
	}

	@Override
	protected void getAllUniformLocations() {
		location_targetWidth = super.getUniformLocation("targetWidth");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}