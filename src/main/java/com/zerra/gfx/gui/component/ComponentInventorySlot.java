package com.zerra.gfx.gui.component;

import org.joml.Vector3f;
import org.joml.Vector4f;

import com.zerra.game.inventory.IInventory;
import com.zerra.game.item.ItemStack;
import com.zerra.gfx.gui.Component;
import com.zerra.gfx.renderer.ItemStackRenderer;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.object.Quad;

public class ComponentInventorySlot extends Component {

	private Quad quad;
	private IInventory inventory;
	private int slot;

	public ComponentInventorySlot(IInventory inventory, int slot, float x, float y) {
		super(x, y, 18, 18);
		this.quad = new Quad(new Vector3f(x, y, 0), new Vector3f(), new Vector3f(16, 16, 0), new Vector4f(1, 1, 1, 0.25f));
		this.inventory = inventory;
		this.slot = slot;
	}

	@Override
	protected void render(MasterRenderer renderer, double mouseX, double mouseY, float partialTicks) {
		ItemStackRenderer.renderItemInGUI(this.getParent(), this.getStack(), this.getX() + 1, this.getY() + 1);
		if (this.isHovered(mouseX, mouseY)) {
			renderer.renderQuads(this.quad);
		}
	}

	private ItemStack getStack() {
		return this.inventory.getStackInSlot(this.slot);
	}
}