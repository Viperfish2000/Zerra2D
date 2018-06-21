package com.zerra.gfx.renderer;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.zerra.Game;
import com.zerra.game.world.tile.Tile;
import com.zerra.gfx.shader.TileShader;
import com.zerra.gfx.texture.ITexture;
import com.zerra.model.Model;
import com.zerra.util.Loader;
import com.zerra.util.Maths;

public class TileRenderer {

	private static final float[] POSITIONS = new float[] { -0.5f, 0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f };

	private Model quad;
	private TileShader shader;

	public TileRenderer(TileShader shader) {
		this.shader = shader;
		this.quad = Loader.loadToVAO(POSITIONS, 2);
	}

	public void render(Tile tile, Map<ITexture, List<Tile>> tiles) {
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		Game.getInstance().getTextureManager().bind(tile.getTexture());
		shader.loadTransformationMatrix(Maths.createTransformationMatrix(0, 0, 0, 0, 0, 0, 10, 10, 10));
		// shader.loadTextureCoords(gui.getTextureCoords());
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

	public void cleanUp() {
		shader.cleanUp();
	}
}