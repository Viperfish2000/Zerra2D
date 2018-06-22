package com.zerra.gfx.renderer;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.zerra.gfx.light.Light;
import com.zerra.gfx.shader.LightShader;
import com.zerra.model.Model;
import com.zerra.object.ICamera;
import com.zerra.util.Loader;

public class LightRenderer {

	private static final float[] POSITIONS = new float[] { -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f };

	private LightShader shader;
	private Model quad;

	public LightRenderer(LightShader shader) {
		this.shader = shader;
		this.shader.start();
		this.shader.loadProjectionMatrix(MasterRenderer.getProjectionMatrix());
		this.shader.stop();
		this.quad = Loader.loadToVAO(POSITIONS, 2);
	}

	public void render(List<Light> lights, ICamera camera) {
		this.bind();
		shader.loadViewMatrix(camera);
		for (Light light : lights) {
			shader.loadLight(light);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, quad.getVertexCount());
		}
		this.unbind();
	}

	private void bind() {
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
	}

	private void unbind() {
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

	public void cleanUp() {
		shader.cleanUp();
	}
}