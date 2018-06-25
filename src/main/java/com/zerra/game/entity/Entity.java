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

	private World world;
	private EntityType type;
	private long ticksExisted;
	/** This is not used anywhere. It needs to be implemented later */
	private float velX;
	/** This is not used anywhere. It needs to be implemented later */
	private float velY;

	private Vector3f rotation;
	private float scale;

	private boolean dead;
	private boolean insideFrustum;
	
	public Entity(EntityType type) {
		this.type = type;
		this.ticksExisted = 0;
		this.velX = 0;
		this.velY = 0;
		this.rotation = new Vector3f();
		this.scale = 1;
	}

	public void init(World world) {
		this.world = world;
	}

	@Override
	public void update() {
		this.ticksExisted++;
		this.setLastX(this.getX());
		this.setLastY(this.getY());
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

	public World getWorld() {
		return world;
	}
	
	public EntityType getType() {
		return type;
	}
	
	public long getTicksExisted() {
		return ticksExisted;
	}
	
	public float getVelX() {
		return velX;
	}
	
	public float getVelY() {
		return velY;
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
	
	protected void setType(EntityType type) {
		this.type = type;
	}
	
	public void setVelocity(float velX, float velY) {
		this.velX = velX;
		this.velY = velY;
	}
	
	public void setVelocityX(float velocityX) {
		this.velX = velocityX;
	}
	
	public void setVelocityY(float velocityY) {
		this.velY = velocityY;
	}
	
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
	
	public void setRotation(float rotationX, float rotationY, float rotationZ) {
		this.rotation.set(rotationX, rotationY, rotationZ);
	}
	
	public void setScale(float scale) {
		this.scale = scale;
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