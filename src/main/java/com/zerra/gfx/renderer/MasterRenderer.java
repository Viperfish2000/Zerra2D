package com.zerra.gfx.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import com.zerra.game.world.tile.TileEntry;
import com.zerra.gfx.GlWrapper;
import com.zerra.gfx.light.Light;
import com.zerra.gfx.post.Fbo;
import com.zerra.gfx.post.PostProcessing;
import com.zerra.gfx.shader.LightShader;
import com.zerra.gfx.shader.QuadShader;
import com.zerra.gfx.shader.TileShader;
import com.zerra.object.ICamera;
import com.zerra.object.Quad;
import com.zerra.util.Display;
import com.zerra.util.ResourceLocation;

public class MasterRenderer {

	public static final float SCALE = 3;
	private static final Matrix4f projectionMatrix = new Matrix4f().ortho(0, Display.getWidth() / SCALE, Display.getHeight() / SCALE, 0, 0.3f, 1000f);

	private TileShader tileShader;
	private TileRenderer tileRenderer;

	private QuadShader quadShader;
	private QuadRenderer quadRenderer;

	private LightShader lightShader;
	private LightRenderer lightRenderer;

	private Fbo fbo;
	private Fbo lightFbo;

	private Map<ResourceLocation, List<TileEntry>> tiles;
	private List<Quad> quads;
	private List<Light> lights;

	private static final Quad LIGHT = new Quad(new Vector3f(0, 0, -2), new Vector3f(), new Vector3f(Display.getWidth(), Display.getHeight(), 1), new Vector4f(0.2f, 0.2f, 0.2f, 1));

	public MasterRenderer() {
		this.tileShader = new TileShader();
		this.tileRenderer = new TileRenderer(tileShader);
		this.quadShader = new QuadShader();
		this.quadRenderer = new QuadRenderer(quadShader);
		this.lightShader = new LightShader();
		this.lightRenderer = new LightRenderer(lightShader);
		this.fbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_RENDER_BUFFER);
		this.lightFbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.NONE);
		this.tiles = new HashMap<ResourceLocation, List<TileEntry>>();
		this.quads = new ArrayList<Quad>();
		this.lights = new ArrayList<Light>();
	}

	public void render(ICamera camera) {
		this.quads.add(LIGHT);

		this.fbo.bindFrameBuffer();
		this.tileRenderer.render(this.tiles, camera);
		this.fbo.unbindFrameBuffer();

		
		this.lightFbo.bindFrameBuffer();
		this.quadRenderer.render(this.quads, camera);
		this.lightRenderer.render(this.lights, camera);
		this.lightFbo.unbindFrameBuffer();
		
		PostProcessing.doPostProcessing(fbo.getColorTexture(), lightFbo.getColorTexture());
		
		GlWrapper.disableDepth();

		this.tiles.clear();
		this.quads.clear();
		this.lights.clear();
	}

	public void cleanUp() {
		this.tileRenderer.cleanUp();
		this.quadRenderer.cleanUp();
		this.lightRenderer.cleanUp();
	}

	public void renderTile(TileEntry tile) {
		ResourceLocation texture = tile.getTile().getTexture();
		List<TileEntry> batch = tiles.get(texture);
		if (batch == null) {
			batch = new ArrayList<TileEntry>();
			this.tiles.put(texture, batch);
		}
		batch.add(tile);
	}

	public void renderQuads(Quad... quads) {
		for (int i = 0; i < quads.length; i++) {
			this.quads.add(quads[i]);
		}
	}

	public void renderLights(Light... lights) {
		for (int i = 0; i < lights.length; i++) {
			this.lights.add(lights[i]);
		}
	}

	public static Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
}