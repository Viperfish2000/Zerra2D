package com.zerra.gfx.renderer;

import java.nio.FloatBuffer;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;

import com.zerra.Zerra;
import com.zerra.game.world.tile.Tile;
import com.zerra.game.world.tile.TileEntry;
import com.zerra.gfx.shader.TileShader;
import com.zerra.model.Model;
import com.zerra.object.ICamera;
import com.zerra.util.Loader;
import com.zerra.util.Maths;
import com.zerra.util.ResourceLocation;

public class TileRenderer {

	private static final float[] POSITIONS = new float[] { 0, 0, 0, 1, 1, 0, 1, 1 };
	private static final int MAX_INSTANCES = 100000;
	private static final int INSTANCE_DATA_LENGTH = 18;

	private static final FloatBuffer buffer = BufferUtils.createFloatBuffer(MAX_INSTANCES * INSTANCE_DATA_LENGTH);

	private static final ResourceLocation GLOW_LOCATION = new ResourceLocation("textures/tile_glow.png");

	private Model quad;
	private int vboID;
	private int pointer;

	private TileShader shader;

	public TileRenderer(TileShader shader) {
		this.shader = shader;
		this.shader.start();
		this.shader.connectTextureUnits();
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

	public void render(Map<ResourceLocation, List<TileEntry>> tiles, ICamera camera, float partialTicks) {
		this.prepare();
		for (ResourceLocation texture : tiles.keySet()) {
			List<TileEntry> batch = tiles.get(texture);
			if(batch.size() > 0)
				this.bindTexture(batch.get(0).getTile());
			pointer = 0;
			float[] vboData = new float[batch.size() * INSTANCE_DATA_LENGTH];
			for (TileEntry tile : batch) {
				tile.getTile().render(tile.getX(), tile.getY(), Zerra.getInstance().getRenderer(), this);
				this.updateModelViewMatrix(tile.getX(), tile.getY(), tile.getLayer(), 0, 16, Maths.createViewMatrix(camera), vboData);
				this.updateTextureCoords(tile.getTile(), vboData);
			}
			Loader.updateVboData(vboID, vboData, buffer);
			GL31.glDrawArraysInstanced(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount(), batch.size());
		}
		this.unbind();
	}

	private void bindTexture(Tile tile) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		Zerra.getInstance().getTextureManager().bind(tile.getTexture());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		Zerra.getInstance().getTextureManager().bind(GLOW_LOCATION);
		shader.loadNumberOfRows(tile.getTextureWidth());
	}

	private void prepare() {
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(4);
		GL20.glEnableVertexAttribArray(2);
		GL20.glEnableVertexAttribArray(3);
		GL20.glEnableVertexAttribArray(5);
	}

	private void unbind() {
		GL20.glDisableVertexAttribArray(5);
		GL20.glDisableVertexAttribArray(4);
		GL20.glDisableVertexAttribArray(3);
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

	private void updateModelViewMatrix(float x, float y, int layer, float rotation, float scale, Matrix4f viewMatrix, float[] vboData) {
		Matrix4f modelMatrix = new Matrix4f();
		modelMatrix.translate(x, y, -100 + layer, modelMatrix);
		modelMatrix.rotate((float) Math.toRadians(rotation), 0, 0, 1, modelMatrix);
		modelMatrix.scale(scale, modelMatrix);
		Matrix4f modelViewMatrix = viewMatrix.mul(modelMatrix, modelMatrix);
		storeMatrixData(modelViewMatrix, vboData);
	}

	private void updateTextureCoords(Tile tile, float[] data) {
		float numberOfRows = tile.getTextureWidth();
		Vector2f textureCoords = tile.getTextureCoords();
		data[pointer++] = textureCoords.x / numberOfRows;
		data[pointer++] = textureCoords.y / numberOfRows;
	}

	private void storeMatrixData(Matrix4f matrix, float[] data) {
		matrix.get(data, pointer);
		pointer += 16;
	}

	public void cleanUp() {
		shader.cleanUp();
	}
}