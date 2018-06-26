package com.zerra.gfx.gui;

import com.zerra.game.inventory.IInventory;

public abstract class GuiContainer extends Gui {

	private IInventory inventory;
	
	public GuiContainer(IInventory inventory) {
		this.inventory = inventory;
		this.addSlots(inventory);
	}
	
	public abstract void addSlots(IInventory inventory);
	
	public IInventory getInventory() {
		return inventory;
	}
}