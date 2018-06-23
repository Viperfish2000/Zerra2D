package com.zerra.game.registry;

import java.util.Map;

import com.google.common.collect.Maps;
import com.zerra.Zerra;
import com.zerra.annotation.Review;
import com.zerra.game.entity.Entity;

public class EntityRegistry {

	private static final Map<String, Class<? extends Entity>> REGISTRY = Maps.<String, Class<?extends Entity>>newHashMap();

	/**
	 * Registers an entity by using their class.
	 * 
	 * @param registryName
	 *            The name the entity is going to be associated with
	 * @param clazz
	 *            The class of the entity to be registered.
	 * 
	 * @return Whether or not the registration was a success.
	 * 
	 * @throws Exception
	 *             May throw multiple exceptions.
	 */
	@Review(desc = "This may or may not work, I have not been able to test it")
	public static boolean register(String registryName, Class<? extends Entity> clazz) throws Exception {
		if (REGISTRY.containsKey(registryName)) {
			throw new RuntimeException("Attempted to register an entity over another. OLD: " + registryName + ", NEW: " + registryName);
		} else {
			REGISTRY.put(registryName, clazz);
			return true;
		}
	}

	/**
	 * Unregisters an entity by using their class.
	 * 
	 * @param registryName
	 *            The registry name of the entity to be unregistered.
	 * 
	 * @return Whether or not the registration was a success.
	 * 
	 * @throws Exception
	 *             May throw multiple exceptions.
	 */
	public static boolean unregister(String registryName) throws Exception {
		if (REGISTRY.containsKey(registryName)) {
			REGISTRY.remove(registryName);
		}
		Zerra.logger().error("Entity with registry name " + registryName + " could not be found in the registry. Removal canceled.");
		return false;
	}

	/**
	 * Checks the registry for the entity's registry name.
	 * 
	 * @param entity
	 *            The entity to validate
	 * @return Whether or not the entity is actually valid
	 */
	public static boolean validate(Entity entity) {
		for (String registryName : REGISTRY.keySet()) {
			if (REGISTRY.get(registryName) == entity.getClass()) {
				return true;
			}
		}
		return false;
	}
}
