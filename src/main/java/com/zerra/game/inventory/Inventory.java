package com.zerra.game.inventory;

import com.zerra.game.item.Item;
import com.zerra.game.item.ItemStack;

public class Inventory {
	
	private Slot[] slots;
	
	public Inventory(int slotCount) {
		slots = new Slot[slotCount];
	}

	/**
	 * @return The slots the inventory has.
	 */
	public Slot[] getSlots() {
		return slots;
	}
	
	public Item getItemInSlot(int index) {
		return slots[index].getItemStack().getItem();
	}
	
	public ItemStack getItemStackInSlot(int index) {
		return slots[index].getItemStack();
	}
	
	public Slot getNextEmptySlot() {
		for(int i = 0; i < slots.length; i++) {
			if(slots[i].isEmpty()) {
				return slots[i];
			}
		}
		return null;
	}
}
