package com.zerra.game.entity;

import com.zerra.game.GameObject;
import com.zerra.gfx.renderer.EntityRenderer;
import com.zerra.gfx.renderer.MasterRenderer;

public abstract class Entity extends GameObject {

	private EntityType type;
	private long ticksExisted = 0;
	private int velX, velY;

	public Entity() {
		this(0, 0, EntityType.NEUTRAL);
	}

	public Entity(float x, float y, EntityType type) {
		this.setX(x);
		this.setY(y);
		this.setType(type);
	}

	@Override
	public void update() {
		this.ticksExisted++;
	}

	@Override
	public void render(MasterRenderer renderer, EntityRenderer entityRenderer) {
	}

	public EntityType getType() {
		return type;
	}

	public void setType(EntityType type) {
		this.type = type;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public long getTicksExisted() {
		return ticksExisted;
	}

	public void setTicksExisted(long ticksExisted) {
		this.ticksExisted = ticksExisted;
	}
}
