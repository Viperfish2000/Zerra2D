package com.zerra.game.entity;

import javax.swing.Renderer;

import com.zerra.gfx.renderer.EntityRenderer;

public abstract class EntityMob extends Entity {

	public EntityMob() {
		this(10, 20);
	}

	public EntityMob(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setType(EntityType.ENEMY);
	}

	@Override
	public void update() {
		super.update();

	}

	@Override
	public void render(Renderer renderer, EntityRenderer entityRenderer, float x, float y) {
		/*
		 * if(this.getY() < Zerra.HEIGHT - 32 && this.getY() > 0 - 32 && this.getX() < Zerra.WIDTH && this.getX() > 0 - 32) { g.setColor(Color.RED); g.fillRect(this.getX(), this.getY(), 16, 16); }
		 */
	}
}
