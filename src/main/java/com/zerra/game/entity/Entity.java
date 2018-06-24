package com.zerra.game.entity;

import javax.annotation.Nullable;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.zerra.game.GameObject;
import com.zerra.game.world.World;
import com.zerra.gfx.renderer.EntityRenderer;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.util.ResourceLocation;

public abstract class Entity extends GameObject {
	
	public static final ResourceLocation ENTITY_TEXTURE_LOCATION = new ResourceLocation("textures/entities.png");
	public static final ResourceLocation SMALL_ENTITY_TEXTURE_LOCATION = new ResourceLocation("textures/entities_small.png");

	protected World world;
	protected EntityType type;
	protected long ticksExisted = 0;
	protected int velX, velY;
	
	protected Vector3f rotation;
	protected float scale;

	private boolean dead;
	private boolean insideFrustum;

	public Entity() {
		this(0, 0, EntityType.NEUTRAL);
	}

	public Entity(float x, float y, EntityType type) {
		this.setX(x);
		this.setY(y);
		this.setType(type);
		this.rotation = new Vector3f();
		this.scale = 1;
	}

	public void init(World world) {
		this.world = world;
	}

	@Override
	public void update() {
		this.lastX = x;
		this.lastY = y;
		this.ticksExisted++;
	}

	@Override
	public void render(MasterRenderer renderer, EntityRenderer entityRenderer, float partialTicks) {
	}

	public void onRemove() {
	}
	
	@Nullable
	public Vector2f getRenderOffset() {
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
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public float getScale() {
		return scale;
	}

	public boolean isDead() {
		return dead;
	}
	
	public boolean isInsideFrustum() {
		return insideFrustum;
	}

	public void setDead() {
		this.dead = true;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public void setInsideFrustum(boolean insideFrustum) {
		this.insideFrustum = insideFrustum;
	}
}
