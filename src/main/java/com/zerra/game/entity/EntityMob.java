package com.zerra.game.entity;

import java.awt.Color;
import java.awt.Graphics;

import com.zerra.Zerra;

public class EntityMob extends Entity {

	public EntityMob() {
		this(10, 20);
	}
	
	public EntityMob(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setType(EntityType.ENEMY);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
	}
	
	@Override
	public void render(Graphics g) {
		if(this.getY() < Zerra.HEIGHT - 32 && this.getY() > 0 - 32 && this.getX() < Zerra.WIDTH && this.getX() > 0 - 32) {
			g.setColor(Color.RED);
			g.fillRect(this.getX(), this.getY(), 16, 16);
		}
	}
}
