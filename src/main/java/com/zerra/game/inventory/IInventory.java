package com.zerra.game.inventory;

import com.zerra.game.item.ItemStack;

public interface IInventory {

	int getSize();

	boolean isFull();

	ItemStack getStackInSlot(int slot);

	void setStackInSlot(int slot, ItemStack stack);

	void clear();
}