package com.zerra.gfx.post;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import com.zerra.util.Fbo;

public class ImageRenderer {

	private Fbo fbo;

	public ImageRenderer(int width, int height) {
		this.fbo = new Fbo(width, height, Fbo.NONE);
	}

	public ImageRenderer() {}

	public void renderQuad() {
		if (fbo != null) {
			fbo.bindFrameBuffer();
		}
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		if (fbo != null) {
			fbo.unbindFrameBuffer();
		}
	}

	@Nullable
	public int getOutputTexture() {
		return fbo != null ? fbo.getColorTexture() : null;
	}

	public void cleanUp() {
		if (fbo != null) {
			fbo.cleanUp();
		}
	}
}