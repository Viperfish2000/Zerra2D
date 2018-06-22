package com.zerra.gfx.post;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.zerra.gfx.GlWrapper;
import com.zerra.model.Model;
import com.zerra.util.Loader;

public class PostProcessing {

	private static final float[] POSITIONS = { -1, 1, -1, -1, 1, 1, 1, -1 };
	private static Model quad;

	private static ContrastChanger contrastChanger;
	
	public static void init() {
		quad = Loader.loadToVAO(POSITIONS, 2);
		
		contrastChanger = new ContrastChanger(0.3f);
	}

	public static void doPostProcessing(int colorTexture, int lightColorTexture) {
		start();
		contrastChanger.render(colorTexture);
		end();
	}

	public static void cleanUp() {
		contrastChanger.cleanUp();
	}

	private static void start() {
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GlWrapper.disableDepth();
	}

	private static void end() {
		GlWrapper.enableDepth();
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
}