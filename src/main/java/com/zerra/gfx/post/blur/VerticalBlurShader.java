package com.zerra.gfx.post.blur;

import com.zerra.gfx.shader.ShaderProgram;

public class VerticalBlurShader extends ShaderProgram {

	private int location_targetHeight;

	protected VerticalBlurShader() {
		super("post/verticalblur_vert", "post/blur_frag");
	}

	protected void loadTargetHeight(float height) {
		super.loadFloat(location_targetHeight, height);
	}

	@Override
	protected void getAllUniformLocations() {
		location_targetHeight = super.getUniformLocation("targetHeight");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}
