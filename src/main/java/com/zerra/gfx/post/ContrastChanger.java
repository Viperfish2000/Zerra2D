package com.zerra.gfx.post;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class ContrastChanger {

	private ImageRenderer renderer;
	private ContrastShader shader;
	
	public ContrastChanger(float contrast) {
		this.renderer = new ImageRenderer();
		this.shader = new ContrastShader();
		this.shader.start();
		this.shader.loadContrast(contrast);
		this.shader.stop();
	}
	
	public void render(int texture) {
		shader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		renderer.renderQuad();
		shader.stop();
	}
	
	public void cleanUp() {
		this.renderer.cleanUp();
		this.shader.cleanUp();
	}
}