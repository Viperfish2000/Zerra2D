package com.zerra.game;

import javax.swing.Renderer;

import com.zerra.gfx.renderer.EntityRenderer;
import com.zerra.util.AxisAlignedBB;
import com.zerra.util.ResourceLocation;

public abstract class GameObject {

	private int x, y;
	private String unlocalizedName;

	public abstract void update();

	public abstract void render(Renderer renderer, EntityRenderer entityRenderer, float x, float y);

	public abstract AxisAlignedBB getCollisionBox();

	public abstract ResourceLocation getTexture();

	public abstract int getTextureWidth();

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	public void setUnlocalizedName(String unlocalizedName) {
		this.unlocalizedName = unlocalizedName;
	}
}
