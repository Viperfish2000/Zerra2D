package com.zerra.game.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

public class EntityLiving extends Entity {
	
	private int health, maxhealth;
	
	public EntityLiving() {
		super(0, 0, EntityType.NEUTRAL);
		this.setHealth(100);
		this.setMaxhealth(100);
	}
	
	public EntityLiving(int x, int y, EntityType type) {
		super(x, y, type);
		this.setHealth(100);
		this.setMaxhealth(100);
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void render(Graphics g) {
		
	}
	
	@Override
	public Rectangle getBounds() {
		return null;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMaxHealth() {
		return maxhealth;
	}

	public void setMaxhealth(int maxhealth) {
		this.maxhealth = maxhealth;
	}
}
