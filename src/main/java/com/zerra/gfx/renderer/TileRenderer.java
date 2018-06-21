package com.zerra.gfx.renderer;

import java.nio.FloatBuffer;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;

import com.zerra.Game;
import com.zerra.game.world.tile.Tile;
import com.zerra.gfx.shader.TileShader;
import com.zerra.model.Model;
import com.zerra.util.Loader;
import com.zerra.util.ResourceLocation;

public class TileRenderer {

	private static final float[] POSITIONS = new float[] { -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f };
	private static final int MAX_INSTANCES = 100;
	private static final int INSTANCE_DATA_LENGTH = 18;

	private static final FloatBuffer buffer = BufferUtils.createFloatBuffer(MAX_INSTANCES * INSTANCE_DATA_LENGTH);

	private Model quad;
	private int vboID;
	private int pointer;

	private TileShader shader;

	public TileRenderer(TileShader shader) {
		this.shader = shader;
		this.shader.start();
		this.shader.loadProjectionMatrix(MasterRenderer.getProjectionMatrix());
		this.shader.stop();
		this.quad = Loader.loadToVAO(POSITIONS, 2);
		this.vboID = Loader.createEmptyVBO(INSTANCE_DATA_LENGTH * MAX_INSTANCES);
		Loader.storeInstancedDataInAttributeList(quad.getVaoID(), vboID, 1, 4, INSTANCE_DATA_LENGTH, 0);
		Loader.storeInstancedDataInAttributeList(quad.getVaoID(), vboID, 2, 4, INSTANCE_DATA_LENGTH, 4);
		Loader.storeInstancedDataInAttributeList(quad.getVaoID(), vboID, 3, 4, INSTANCE_DATA_LENGTH, 8);
		Loader.storeInstancedDataInAttributeList(quad.getVaoID(), vboID, 4, 4, INSTANCE_DATA_LENGTH, 12);
		Loader.storeInstancedDataInAttributeList(quad.getVaoID(), vboID, 5, 2, INSTANCE_DATA_LENGTH, 16);
	}

	public void render(Map<ResourceLocation, List<Tile>> tiles) {
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(4);
		GL20.glEnableVertexAttribArray(2);
		GL20.glEnableVertexAttribArray(3);
		GL20.glEnableVertexAttribArray(5);

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		for (ResourceLocation texture : tiles.keySet()) {
			List<Tile> batch = tiles.get(texture);
			Game.getInstance().getTextureManager().bind(texture);
			for (Tile tile : batch) {
				//TODO: Change this.
				this.updateModelViewMatrix(0, 0, 0, 0, null, null);
			}
			GL31.glDrawArraysInstanced(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount(), batch.size());
		}

		// shader.loadTransformationMatrix(Maths.createTransformationMatrix(0, 0, -2, 0, 0, 0, 2, 2, 1));
		// GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());

		GL20.glDisableVertexAttribArray(5);
		GL20.glDisableVertexAttribArray(4);
		GL20.glDisableVertexAttribArray(3);
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

	private void updateModelViewMatrix(float x, float y, float rotation, float scale, Matrix4f viewMatrix, float[] vboData) {
		Matrix4f modelMatrix = new Matrix4f();
		modelMatrix.translate(x, y, 0, modelMatrix);
		modelMatrix.rotate((float) Math.toRadians(rotation), 0, 0, 1, modelMatrix);
		modelMatrix.scale(scale, modelMatrix);
		Matrix4f modelViewMatrix = viewMatrix.mul(modelMatrix, modelMatrix);
		storeMatrixData(modelViewMatrix, vboData);
	}

	private void storeMatrixData(Matrix4f matrix, float[] data) {
		data[pointer++] = matrix.m00();
		data[pointer++] = matrix.m01();
		data[pointer++] = matrix.m02();
		data[pointer++] = matrix.m03();
		data[pointer++] = matrix.m10();
		data[pointer++] = matrix.m11();
		data[pointer++] = matrix.m12();
		data[pointer++] = matrix.m13();
		data[pointer++] = matrix.m20();
		data[pointer++] = matrix.m21();
		data[pointer++] = matrix.m22();
		data[pointer++] = matrix.m23();
		data[pointer++] = matrix.m30();
		data[pointer++] = matrix.m31();
		data[pointer++] = matrix.m32();
		data[pointer++] = matrix.m33();
	}

	public void cleanUp() {
		shader.cleanUp();
	}
}