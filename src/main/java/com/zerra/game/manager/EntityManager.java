package com.zerra.game.manager;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import com.zerra.game.entity.Entity;

@Deprecated
public class EntityManager {

	private static Map<Entity, Integer> entities = new HashMap<Entity, Integer>();

	/**
	 * @return The entities currently in the world.
	 */
	public Map<Entity, Integer> getEntities() {
		return entities;
	}

	/**
	 * Creates an entity.
	 * 
	 * @param entity
	 *            The entity to create in the world.
	 */
	public void createEntity(Entity entity) {
		// if(entity == null) {
		// entity = new Entity();
		// }
		entities.put(entity, entities.size());
	}

	/**
	 * Destroys an entity in the world.
	 * 
	 * @param entity
	 *            The entity to be removed.
	 */
	public void destroyEntity(Entity entity) {
		entities.remove(entity);
		entity = null;
	}

	/**
	 * Updates all entities in the world.
	 */
	public void updateEntities() {
		for (Entity entity : entities.keySet()) {
			entity.update();
		}
	}

	@Deprecated
	public void render(Graphics g) {
		for (Entity entity : entities.keySet()) {
			// entity.render(g);
		}
	}
}
