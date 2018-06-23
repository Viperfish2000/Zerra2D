package com.zerra.game.entity;

import com.zerra.game.GameObject;
import com.zerra.game.world.World;
import com.zerra.gfx.renderer.EntityRenderer;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.util.ResourceLocation;

public abstract class Entity extends GameObject {
	
	public static final ResourceLocation ENTITY_TEXTURE_LOCATION = new ResourceLocation("textures/entities.png");

	protected World world;
	protected EntityType type;
	protected long ticksExisted = 0;
	protected int velX, velY;

	private boolean dead;

	public Entity() {
		this(0, 0, EntityType.NEUTRAL);
	}

	public Entity(float x, float y, EntityType type) {
		this.setX(x);
		this.setY(y);
		this.setType(type);
	}

	public void init(World world) {
		this.world = world;
	}

	@Override
	public void update() {
		this.ticksExisted++;
	}

	@Override
	public void render(MasterRenderer renderer, EntityRenderer entityRenderer) {
	}

	public void onRemove() {
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

	public boolean isDead() {
		return dead;
	}

	public void setDead() {
		this.dead = true;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
}
