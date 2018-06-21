package com.zerra.game.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.zerra.game.GameObject;

public class Entity extends GameObject {

	private EntityType type;
	public long ticksExisted = 0;
	
	public Entity() {
		this(0, 0, EntityType.NEUTRAL);
	}
	
	public Entity(int x, int y, EntityType type) {
		this.setX(x);
		this.setY(y);
		this.setType(type);
	}

	public EntityType getType() {
		return type;
	}

	public void setType(EntityType type) {
		this.type = type;
	}

	@Override
	public void tick() {
		ticksExisted++;
	}

	@Override
	public void render(Graphics g) {
		
	}
	
	@Override
	public Rectangle getBounds() {
		return null;
	}
}
