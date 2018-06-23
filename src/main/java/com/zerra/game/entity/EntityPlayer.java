package com.zerra.game.entity;

import org.joml.Vector2f;

import com.zerra.game.inventory.PlayerInventory;
import com.zerra.util.AxisAlignedBB;
import com.zerra.util.ResourceLocation;

public class EntityPlayer extends EntityLiving {

	private int exp = 0;
	private int level = 0;
	private PlayerInventory inventory;

	private static final Vector2f TEXTURE_COORDS = new Vector2f(0, 0);

	public EntityPlayer() {
		this(0, 0);
	}

	public EntityPlayer(float x, float y) {
		this.setX(x);
		this.setY(y);
		this.setType(EntityType.PLAYER);
		this.inventory = new PlayerInventory(45);
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public PlayerInventory getInventory() {
		return inventory;
	}

	@Override
	public AxisAlignedBB getCollisionBox() {
		return new AxisAlignedBB(this.getX(), this.getY(), 32, 32);
	}

	@Override
	public ResourceLocation getTexture() {
		return ENTITY_TEXTURE_LOCATION;
	}

	@Override
	public Vector2f getTextureOffset() {
		return TEXTURE_COORDS;
	}

	@Override
	public int getTextureWidth() {
		return 8;
	}
}