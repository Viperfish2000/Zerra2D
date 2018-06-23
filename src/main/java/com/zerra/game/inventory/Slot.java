package com.zerra.game.inventory;

import com.zerra.game.item.ItemStack;

public class Slot {

	private ItemStack itemStack;
	
	public Slot(ItemStack itemStack) {
		this.setItemStack(itemStack);
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}
	
	public boolean isEmpty() {
		return itemStack == null ? true : false;
	}
}
