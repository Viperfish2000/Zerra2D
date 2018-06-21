package com.zerra.game.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

public class EntityPlayerMover extends Entity {

	public EntityPlayerMover() {
		this(10, 20);
	}
	
	public EntityPlayerMover(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setType(EntityType.PLAYER);
	}
	
	@Override
	public void tick() {
		super.tick();
		this.setX(this.getX() + this.getVelX());
		this.setY(this.getY() + this.getVelY());
	}
	
	@Override
	public void render(Graphics g) {
	}
	
	@Override
	public Rectangle getBounds() {
		return null;
	}
}
