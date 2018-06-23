package com.zerra.game.inventory;

import com.zerra.game.item.ItemStack;

public class Slot {

	private ItemStack itemStack;
	
	public Slot(ItemStack itemStack) {
		this.setItemStack(itemStack);
	}

	/**
	 * Gets the ItemStack stored in this slot.
	 * 
	 * @return 
	 * 		The ItemStack that is stored in the slot.
	 */
	public ItemStack getItemStack() {
		return itemStack;
	}

	/**
	 * Sets the ItemStack of this slot.
	 * 
	 * @param itemStack
	 * 				The ItemStack to place in the slot.
	 */
	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}
	
	/**
	 * Returns whether or not the slot is empty.
	 * 
	 * @return
	 * 		Whether or not the slot is empty.
	 */
	public boolean isEmpty() {
		return itemStack == null ? true : false;
	}
}
