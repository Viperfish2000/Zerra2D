package com.zerra.game;

import com.zerra.gfx.renderer.EntityRenderer;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.util.AxisAlignedBB;
import com.zerra.util.ResourceLocation;

public abstract class GameObject {

	private float x, y;
	private String unlocalizedName;

	public abstract void update();

	public abstract void render(MasterRenderer renderer, EntityRenderer entityRenderer);

	public abstract AxisAlignedBB getCollisionBox();

	public abstract ResourceLocation getTexture();

	public abstract int getTextureWidth();

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	public void setUnlocalizedName(String unlocalizedName) {
		this.unlocalizedName = unlocalizedName;
	}
}
