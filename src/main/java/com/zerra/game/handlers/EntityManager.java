package com.zerra.game.handlers;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import com.zerra.game.entity.Entity;
import com.zerra.game.entity.EntityPlayerMover;

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
	
	public EntityPlayerMover getPlayerMover() {
		for (Entity entity : entities.keySet()) {
			if(entity instanceof EntityPlayerMover) {
				return (EntityPlayerMover) entity;
			}
		}
		return null;
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
