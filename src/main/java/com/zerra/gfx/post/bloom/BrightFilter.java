package com.zerra.gfx.post.bloom;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.zerra.gfx.post.ImageRenderer;

public class BrightFilter {

	private ImageRenderer renderer;
	private BrightFilterShader shader;

	public BrightFilter(int width, int height) {
		shader = new BrightFilterShader();
		renderer = new ImageRenderer(width, height);
	}

	public void render(int texture) {
		shader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		renderer.renderQuad();
		shader.stop();
	}

	public void cleanUp() {
		renderer.cleanUp();
		shader.cleanUp();
	}

	public ImageRenderer getRenderer() {
		return renderer;
	}
}