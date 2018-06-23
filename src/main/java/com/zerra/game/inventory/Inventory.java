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
	
	/**
	 * Gets the Item that is stored in the specified slot.
	 * 
	 * @param index
	 * 			The index of the slot that needs to be checked.
	 * @return
	 * 		The Item that is stored in the slot.
	 */
	public Item getItemInSlot(int index) {
		return slots[index].getItemStack().getItem();
	}
	
	/**
	 * Gets the ItemStack that is stored in the specified slot.
	 * 
	 * @param index
	 * 			The index of the slot that needs to be checked.
	 * @return
	 * 		The ItemStack that is stored in the slot.
	 */
	public ItemStack getItemStackInSlot(int index) {
		return slots[index].getItemStack();
	}
	
	/**
	 * @return The next slot in the inventory that is empty.
	 */
	public Slot getNextEmptySlot() {
		for(int i = 0; i < slots.length; i++) {
			if(slots[i].isEmpty()) {
				return slots[i];
			}
		}
		return null;
	}
}
