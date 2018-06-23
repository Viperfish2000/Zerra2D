package com.zerra.gfx.renderer;

import java.util.List;
import java.util.Map;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.zerra.Zerra;
import com.zerra.game.entity.Entity;
import com.zerra.gfx.shader.EntityShader;
import com.zerra.model.Model;
import com.zerra.object.ICamera;
import com.zerra.util.Loader;
import com.zerra.util.Maths;
import com.zerra.util.ResourceLocation;

public class EntityRenderer {

	private static final float[] POSITIONS = new float[] { 0, 0, 0, 1, 1, 0, 1, 1 };

	private EntityShader shader;
	private Model quad;

	public EntityRenderer(EntityShader shader) {
		this.shader.start();
		this.shader.connectTextureUnits();
		this.shader.loadProjectionMatrix(MasterRenderer.getProjectionMatrix());
		this.shader.stop();
		this.quad = Loader.loadToVAO(POSITIONS, 2);
	}

	public void render(Map<ResourceLocation, List<Entity>> entities, ICamera camera) {
		this.prepare();
		for (ResourceLocation texture : entities.keySet()) {
			this.bindTexture(texture);
			shader.loadViewMatrix(camera);
			List<Entity> batch = entities.get(texture);
			for (Entity entity : batch) {
				shader.loadTransformationMatrix(Maths.createTransformationMatrix(new Vector2f(entity.getX(), entity.getY()), new Vector2f(1)));
				shader.loadNumberOfRows(entity.getTextureWidth());
				entity.render(Zerra.getInstance().getRenderer(), this);
				GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
			}
		}
		this.unbind();
	}

	private void bindTexture(ResourceLocation texture) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		Zerra.getInstance().getTextureManager().bind(texture);
	}

	private void prepare() {
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