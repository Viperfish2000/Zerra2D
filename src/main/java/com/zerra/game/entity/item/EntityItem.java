package com.zerra.game.entity.item;

import org.joml.Vector2f;

import com.zerra.game.entity.Entity;
import com.zerra.game.entity.EntityPlayer;
import com.zerra.game.entity.EntityType;
import com.zerra.game.item.ItemStack;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.util.AxisAlignedBB;
import com.zerra.util.ResourceLocation;

public class EntityItem extends Entity {

	private ItemStack stack;

	public EntityItem(ItemStack stack) {
		this(stack, 0, 0);
	}

	public EntityItem(ItemStack stack, float x, float y) {
		super(EntityType.NEUTRAL);
		this.setPosition(x, y);
		this.setLastPosition(x, y);
		this.setScale(1f / MasterRenderer.scale);
		this.setSize(4, 4);
		this.stack = stack;
	}

	@Override
	public void update() {
		super.update();
		EntityPlayer player = this.getWorld().getPlayer();
		if(player != null) {
			AxisAlignedBB playerBox = player.getCollisionBox();
			if(playerBox.intersects(this.getCollisionBox())) {
				ItemStack remaining = player.getInventory().addStackToInventory(this.stack);
				if(remaining.isEmpty()) {
					this.setDead();
				}
				this.stack = remaining;
			}
		}
	}

	@Override
	public Vector2f getRenderOffset() {
		return new Vector2f();
	}

	@Override
	public ResourceLocation getTexture() {
		return this.stack.getItem().getTexture();
	}

	@Override
	public Vector2f getTextureOffset() {
		return this.stack.getItem().getTextureCoords(0);
	}

	@Override
	public int getTextureWidth() {
		return this.stack.getItem().getTextureWidth();
	}
}