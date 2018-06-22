package com.zerra.gfx.post;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.zerra.gfx.GlWrapper;
import com.zerra.gfx.post.contrast.ContrastChanger;
import com.zerra.gfx.post.light.LightApplier;
import com.zerra.model.Model;
import com.zerra.util.Loader;

public class PostProcessing {

	private static final float[] POSITIONS = { -1, 1, -1, -1, 1, 1, 1, -1 };
	private static Model quad;
	
	private static ContrastChanger contrastChanger;
	private static LightApplier lightApplier;
	
	public static void init() {
		quad = Loader.loadToVAO(POSITIONS, 2);
		
		contrastChanger = new ContrastChanger(0.3f);
		lightApplier = new LightApplier();
	}

	public static void doPostProcessing(int colorTexture, int lightColorTexture) {
		start();
		contrastChanger.render(colorTexture);
		lightApplier.render(contrastChanger.getRenderer().getOutputTexture(), lightColorTexture);
		end();
	}

	public static void cleanUp() {
		contrastChanger.cleanUp();
		lightApplier.cleanUp();
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