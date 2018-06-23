package com.zerra.game.entity.item;

import com.zerra.game.entity.Entity;
import com.zerra.util.AxisAlignedBB;
import com.zerra.util.ResourceLocation;

public class EntityItem extends Entity {

	@Override
	public AxisAlignedBB getCollisionBox() {
		return null;
	}

	@Override
	public ResourceLocation getTexture() {
		return null;
	}

	@Override
	public int getTextureWidth() {
		return 0;
	}
}