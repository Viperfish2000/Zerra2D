package com.zerra.game.manager;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import com.zerra.game.entity.Entity;

public class EntityManager {

	private static Map<Entity, Integer> entities = new HashMap<Entity, Integer>();
	
	public Map<Entity, Integer> getEntities() {
		return entities;
	}
	
	public void createEntity(Entity entity) {
		if(entity == null) {
			entity = new Entity();
		}
		entities.put(entity, entities.size());
	}
	
	public void destroyEntity(Entity entity) {
		entities.remove(entity);
	}
	
	public void tick() {
		for (Entity entity : entities.keySet()) {
			entity.tick();
		}
	}
	
	public void render(Graphics g) {
		for (Entity entity : entities.keySet()) {
			entity.render(g);
		}
	}
}
