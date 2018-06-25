package com.zerra.game.world;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nullable;

import com.zerra.Zerra;
import com.zerra.game.entity.Entity;
import com.zerra.game.entity.EntityPlayer;
import com.zerra.game.registry.EntityRegistry;
import com.zerra.game.world.map.TileMap;
import com.zerra.gfx.FrustumCullingFilter;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.object.ICamera;
import com.zerra.util.Display;
import com.zerra.util.Maths;

public class World {

	private static final Comparator<Entity> entitySorter = (Entity e1, Entity e2) -> {
		if (e1 instanceof EntityPlayer)
			System.out.println(e1.getRenderLayer() - e2.getRenderLayer());
		return e1.getRenderLayer() - e2.getRenderLayer();
	};

	private TileMap tileMap;

	private String worldName;
	private float worldTime;
	private float time;

	private FrustumCullingFilter filter;
	private List<Entity> entities;

	public World() {
		this.tileMap = new TileMap();

		this.worldName = "world";
		this.worldTime = 0.0F;
		this.time = 1.0F;

		this.filter = new FrustumCullingFilter();
		this.entities = new ArrayList<Entity>();
	}

	public void update() {
		/** Day/Night Cycle **/
		time = (float) (Math.cos(worldTime) + 0.5);
		time = (float) Maths.clamp(time, 0.2F, 1.0F);
		if (time > 1.0F || time < 0.2F) {
			worldTime += 0.0005F;
		} else {
			worldTime += 0.0001F;
		}
		if (Display.getMouseButton() == 0) {
			worldTime += 0.01f;
		}
		if (Display.getMouseButton() == 1) {
			worldTime -= 0.01f;
		}

		this.tileMap.update();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			entity.update();
			if (entity.isDead()) {
				entities.remove(i);
				entity.onRemove();
				i--;
			}
		}
	}

	public void render(MasterRenderer renderer, ICamera camera, float partialTicks) {
		renderer.setAmbientLight(time, time, time);

		this.filter.updateFrustum(MasterRenderer.getProjectionMatrix(), Maths.createViewMatrix(camera));
		this.filter.filterEntities(entities);
		Collections.sort(entities, entitySorter);

		this.tileMap.render(renderer);
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isInsideFrustum()) {
				renderer.renderEntity(entities.get(i));
			}
		}
	}

	public void generate() {
		Zerra.logger().info("Generating terrain...");
		this.tileMap.generate();
	}

	public void addEntity(Entity entity) {
		if (entity != null && EntityRegistry.validate(entity)) {
			entity.init(this);
			entities.add(entity);
		}
	}

	public void save(File saveFolder) throws IOException {
		Zerra.logger().info("Saving world...");
		this.tileMap.save(saveFolder, worldName);
	}

	public void load(File saveFolder) throws IOException {
		Zerra.logger().info("Loading World");
		this.tileMap.load(saveFolder, worldName);
	}

	public float getTime() {
		return time;
	}

	public float getWorldTime() {
		return worldTime;
	}

	@Nullable
	public EntityPlayer getPlayer() {
		for (Entity entity : entities) {
			if (entity instanceof EntityPlayer) {
				return (EntityPlayer) entity;
			}
		}
		return null;
	}
}