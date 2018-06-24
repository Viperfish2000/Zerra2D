package com.zerra.game.entity;

import com.zerra.gfx.renderer.EntityRenderer;
import com.zerra.gfx.renderer.MasterRenderer;

public abstract class EntityMob extends Entity {

	public EntityMob() {
		this(10, 20);
	}

	public EntityMob(float x, float y) {
		this.setX(x);
		this.setY(y);
		this.setType(EntityType.ENEMY);
	}

	@Override
	public void render(MasterRenderer renderer, EntityRenderer entityRenderer, float partialTicks) {
		/*
		 * if(this.getY() < Zerra.HEIGHT - 32 && this.getY() > 0 - 32 && this.getX() < Zerra.WIDTH && this.getX() > 0 - 32) { g.setColor(Color.RED); g.fillRect(this.getX(), this.getY(), 16, 16); }
		 */
	}
}
