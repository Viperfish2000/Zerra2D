package com.zerra.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class EntityPlayer extends EntityLiving {

	public EntityPlayer() {
		this(10, 20);
	}
	
	public EntityPlayer(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setType(EntityType.PLAYER);
	}
	
	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(this.getX(), this.getY(), 32, 32);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(this.getX(), this.getY(), 32, 32);
	}
}
