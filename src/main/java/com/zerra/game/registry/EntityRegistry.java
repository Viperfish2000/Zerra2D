package com.zerra.game.registry;

import java.lang.reflect.Constructor;
import java.util.HashSet;

import com.zerra.Game;
import com.zerra.game.entity.Entity;

public class EntityRegistry {

	private static EntityRegistry instance;
	
	private static HashSet<Class<? extends Entity>> REGISTRY;
	
	private EntityRegistry() {
		REGISTRY = new HashSet<Class<? extends Entity>>();
	}
	
	public static EntityRegistry getRegistry() {
		if(instance == null) {
			instance = new EntityRegistry();
		}
		return instance;
	}
	
	
	public static boolean register(Class<? extends Entity> clazz) throws Exception {	
		Constructor<?> ctor = clazz.getConstructor(String.class);
		Entity ent = (Entity)ctor.newInstance(new Object[] {});
		String UL = ent.getUnlocalizedName();
		
		for(Class<? extends Entity> classes: REGISTRY) {
			Constructor<?> tempCtor = classes.getConstructor(String.class);
			Entity tempEnt = (Entity)tempCtor.newInstance(new Object[] {});
			String tempUL = tempEnt.getUnlocalizedName();
			
			if(UL.matches(tempUL)) {
				Game.logger().error("Entity with unlocalized name " + UL + " is already registered!");
				return false;
			}
		}
		return REGISTRY.add(clazz);
	}
	
	public static boolean unregister(Class<? extends Entity> clazz) throws Exception {
		Constructor<?> ctor = clazz.getConstructor(String.class);
		Entity ent = (Entity)ctor.newInstance(new Object[] {});
		String UL = ent.getUnlocalizedName();
		
		for(Class<? extends Entity> classes: REGISTRY) {
			Constructor<?> tempCtor = classes.getConstructor(String.class);
			Entity tempEnt = (Entity)tempCtor.newInstance(new Object[] {});
			String tempUL = tempEnt.getUnlocalizedName();
			
			if(UL.matches(tempUL)) {
				return REGISTRY.remove(clazz);
			}
		}
		Game.logger().error("Entity with unlocalized name " + UL + " could not be found in the registry. Removal canceled.");
		return false;
	}
}
