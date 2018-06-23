package com.zerra.game.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.zerra.game.GameObject;

public class Entity extends GameObject {

	private EntityType type;
	private long ticksExisted = 0;
	private int velX, velY;
	
	public Entity() {
		this(0, 0, EntityType.NEUTRAL);
	}
	
	public Entity(int x, int y, EntityType type) {
		this.setX(x);
		this.setY(y);
		this.setType(type);
	}

	@Override
	public void onUpdate() {
		setTicksExisted(getTicksExisted() + 1);
	}

	@Override
	public void render(Graphics g) {
		
	}
	
	@Override
	public Rectangle getBounds() {
		return null;
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
