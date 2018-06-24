package com.zerra.gfx.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

import com.zerra.game.entity.Entity;
import com.zerra.game.world.tile.TileEntry;
import com.zerra.gfx.light.Light;
import com.zerra.gfx.post.PostProcessing;
import com.zerra.gfx.shader.EntityShader;
import com.zerra.gfx.shader.LightShader;
import com.zerra.gfx.shader.QuadShader;
import com.zerra.gfx.shader.TileShader;
import com.zerra.object.ICamera;
import com.zerra.object.Quad;
import com.zerra.util.Display;
import com.zerra.util.Fbo;
import com.zerra.util.ResourceLocation;

public class MasterRenderer {

	public static float scale = 3f;
	private static Matrix4f projectionMatrix = new Matrix4f().ortho(0, Display.getWidth() / scale, Display.getHeight() / scale, 0, 0.3f, 1000f);

	private TileShader tileShader;
	private TileRenderer tileRenderer;

	private EntityShader entityShader;
	private EntityRenderer entityRenderer;

	private QuadShader quadShader;
	private QuadRenderer quadRenderer;

	private LightShader lightShader;
	private LightRenderer lightRenderer;

	private Fbo fbo;
	private Fbo lightFbo;

	private Map<ResourceLocation, List<TileEntry>> tiles;
	private Map<ResourceLocation, List<Entity>> entities;
	private List<Quad> quads;
	private List<Light> lights;

	private static final Quad LIGHT = new Quad(new Vector3f(0, 0, -2), new Vector3f(), new Vector3f(Display.getWidth(), Display.getHeight(), 1), new Vector4f(0.2f, 0.2f, 0.2f, 1));

	public MasterRenderer() {
		this.tileShader = new TileShader();
		this.tileRenderer = new TileRenderer(tileShader);
		this.entityShader = new EntityShader();
		this.entityRenderer = new EntityRenderer(entityShader);
		this.quadShader = new QuadShader();
		this.quadRenderer = new QuadRenderer(quadShader);
		this.lightShader = new LightShader();
		this.lightRenderer = new LightRenderer(lightShader);
		this.fbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_RENDER_BUFFER, 2);
		this.lightFbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.NONE);
		this.tiles = new HashMap<ResourceLocation, List<TileEntry>>();
		this.entities = new HashMap<ResourceLocation, List<Entity>>();
		this.quads = new ArrayList<Quad>();
		this.lights = new ArrayList<Light>();
	}

	public void render(ICamera camera, float partialTicks) {
		this.quads.add(LIGHT);

		this.fbo.bindFrameBuffer();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		this.tileRenderer.render(this.tiles, camera, partialTicks);
		this.entityRenderer.render(entities, camera, partialTicks);
		this.fbo.unbindFrameBuffer();

		this.lightFbo.bindFrameBuffer();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		this.quadRenderer.render(this.quads, camera, partialTicks);
		this.lightRenderer.render(this.lights, camera, partialTicks);
		this.lightFbo.unbindFrameBuffer();

		PostProcessing.doPostProcessing(fbo.getColorTexture(0), fbo.getColorTexture(1), lightFbo.getColorTexture());

		this.tiles.clear();
		this.entities.clear();
		this.quads.clear();
		this.lights.clear();
	}

	public void cleanUp() {
		this.tileRenderer.cleanUp();
		this.entityShader.cleanUp();
		this.quadRenderer.cleanUp();
		this.lightRenderer.cleanUp();
		this.fbo.cleanUp();
		this.lightFbo.cleanUp();
	}

	public void renderTile(TileEntry tile) {
		ResourceLocation texture = tile.getTile().getTexture();
		List<TileEntry> batch = this.tiles.get(texture);
		if (batch == null) {
			batch = new ArrayList<TileEntry>();
			this.tiles.put(texture, batch);
		}
		batch.add(tile);
	}

	public void renderEntity(Entity entity) {
		ResourceLocation texture = entity.getTexture();
		List<Entity> batch = this.entities.get(texture);
		if (batch == null) {
			batch = new ArrayList<Entity>();
			this.entities.put(texture, batch);
		}
		batch.add(entity);
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

	public void setAmbientLight(float red, float green, float blue) {
		LIGHT.getColor().set(red, green, blue, 1);
	}

	public void setScale(float scale) {
		MasterRenderer.scale = scale;
		MasterRenderer.projectionMatrix = new Matrix4f().ortho(0, Display.getWidth() / scale, Display.getHeight() / scale, 0, 0.3f, 1000f);
		this.tileRenderer.updateProjectionMatrix(projectionMatrix);
		this.entityRenderer.updateProjectionMatrix(projectionMatrix);
		this.lightRenderer.updateProjectionMatrix(projectionMatrix);
	}

	public static Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
}