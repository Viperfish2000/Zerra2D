package com.zerra.game.entity;

import java.awt.Color;
import java.awt.Graphics;

import com.zerra.Game;

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
	public void tick() {
		super.tick();
		this.setX(this.getX() + Game.manager.getPlayerMover().getVelX());
		this.setY(this.getY() + Game.manager.getPlayerMover().getVelY());
	}
	
	@Override
	public void render(Graphics g) {
		if(this.getY() < Game.HEIGHT - 32 && this.getY() > 0 - 32 && this.getX() < Game.WIDTH && this.getX() > 0 - 32) {
			g.setColor(Color.RED);
			g.fillRect(this.getX(), this.getY(), 16, 16);
		}
	}
}
