package com.zerra.gfx.renderer;

import org.joml.Vector2f;

import com.zerra.game.item.Item;
import com.zerra.game.item.ItemStack;
import com.zerra.gfx.gui.Gui;

public class ItemStackRenderer {

	public static void renderItemInGUI(Gui gui, ItemStack stack, float xPos, float yPos) {
		renderItemInGUI(gui, stack, xPos, yPos, 1);
	}

	public static void renderItemInGUI(Gui gui, ItemStack stack, float xPos, float yPos, float scale) {
		Item item = stack.getItem();
		if (!stack.isEmpty()) {
			for (int i = 0; i < 2; i++) {
				Vector2f textureCoords = item.getTextureCoords(i);
				gui.drawTexturedRect(item.getTexture(), xPos, yPos, 16, 16, textureCoords.x * item.getTextureWidth() * 16, textureCoords.y * item.getTextureWidth() * 16, item.getTextureWidth() * 16);
			}
		}
	}
}