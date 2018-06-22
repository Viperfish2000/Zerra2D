package com.zerra.gfx.post;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class LightApplier {

	private ImageRenderer renderer;
	private LightApplierShader shader;
	
	public LightApplier() {
		this.renderer = new ImageRenderer();
		this.shader = new LightApplierShader();
		this.shader.start();
		this.shader.connectTextureUnits();
		this.shader.stop();
	}
	
	public void render(int texture, int lightTexture) {
		this.shader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, lightTexture);
		this.renderer.renderQuad();
		this.shader.stop();
	}
	
	public void cleanUp() {
		this.renderer.cleanUp();
		this.shader.cleanUp();
	}
}