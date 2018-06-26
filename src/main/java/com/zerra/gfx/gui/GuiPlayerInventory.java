package com.zerra.gfx.gui;

import com.zerra.game.inventory.IInventory;
import com.zerra.gfx.gui.component.ComponentInventorySlot;
import com.zerra.gfx.renderer.MasterRenderer;

public class GuiPlayerInventory extends GuiContainer {

	public GuiPlayerInventory(IInventory inventory) {
		super(inventory);
	}

	@Override
	public void addSlots(IInventory inventory) {
		for (int i = 0; i < inventory.getSize(); i++) {
			this.addComponent(new ComponentInventorySlot(inventory, i, i * 20, 0));
		}
	}
	
	@Override
	public void render(MasterRenderer renderer, double mouseX, double mouseY, float partialTicks) {
		super.drawDefaultBackground(renderer);
		
		super.render(renderer, mouseX, mouseY, partialTicks);
	}
}