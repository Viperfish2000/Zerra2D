package com.zerra.gfx.renderer;

import java.util.List;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.zerra.Zerra;
import com.zerra.gfx.GlWrapper;
import com.zerra.gfx.gui.Gui;
import com.zerra.gfx.gui.GuiTexture;
import com.zerra.gfx.shader.GuiShader;
import com.zerra.model.Model;
import com.zerra.util.Display;
import com.zerra.util.Loader;

public class GuiRenderer {

	public static float scale = 3f;
	private static Matrix4f projectionMatrix = new Matrix4f().ortho(0, Display.getWidth() / scale, Display.getHeight() / scale, 0, 0.3f, 1000f);

	private static final float[] POSITIONS = new float[] { -1, 1, -1, -1, 1, 1, 1, -1 };

	private GuiShader shader;
	private Model quad;

	public GuiRenderer(GuiShader shader) {
		this.shader = shader;
		this.shader.start();
		this.shader.loadProjectionMatrix(projectionMatrix);
		this.shader.stop();
		this.quad = Loader.loadToVAO(POSITIONS, 2);
	}

	public void render(List<Gui> guis, double mouseX, double mouseY, float partialTicks) {
		this.bind();
		for (Gui gui : guis) {
			gui.render(Zerra.getInstance().getRenderer(), mouseX, mouseY, partialTicks);
			for (GuiTexture texture : gui.getTextures()) {
				shader.loadTransformationMatrix(texture.getTransformation());
				shader.loadTextureOffsets(texture.getTextureOffsets());
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTexture().getId());
				GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, this.quad.getVertexCount());
			}
		}
		this.unbind();
	}

	private void bind() {
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GlWrapper.enableBlend();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	private void unbind() {
		GlWrapper.disableBlend();
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

	public void cleanUp() {
		shader.cleanUp();
	}

	public void setScale(float scale) {
		GuiRenderer.scale = scale;
		GuiRenderer.projectionMatrix = new Matrix4f().ortho(0, Display.getWidth() / scale, Display.getHeight() / scale, 0, 0.3f, 1000f);
		this.shader.start();
		this.shader.loadProjectionMatrix(projectionMatrix);
		this.shader.stop();
	}

	public static Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
}