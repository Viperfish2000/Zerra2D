package com.zerra.game.item;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joml.Vector2f;

import com.google.common.collect.Maps;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.util.I18n;

public class Item {

	private static final Map<String, Item> ITEMS = Maps.<String, Item>newHashMap();

	public static final Item TEST = new BasicItem("test", "test", new Vector2f(0, 0));

	private String registryName;
	private String unlocalizedName;
	private int maxStackSize;

	public Item(String registryName, String unlocalizedName) {
		this.registryName = registryName;
		this.unlocalizedName = unlocalizedName;
		this.maxStackSize = 64;
		registerItem(this);
	}

	public void update() {
	}

	public void render(MasterRenderer renderer, double x, double y, double scale) {
	}

	// TODO add the player and add all the mobs in
	// public void onClickUse(EntityPlayer player, int mouseButton, int mouseX, int mouseY) {
	// }
	//
	// public void onKeyUse(EntityPlayer player, int keyCode) {
	// }

	public void addTooltipInformation(List<String> tooltip) {
	}

	public String getRegistryName() {
		return registryName;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	public int getMaxStackSize() {
		return maxStackSize;
	}

	public String getLocalizedName() {
		return I18n.format("item." + this.getUnlocalizedName() + ".name");
	}

	public Vector2f getTextureCoords() {
		return new Vector2f();
	}

	protected Item setMaxStackSize(int stackSize) {
		this.maxStackSize = stackSize;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Item) {
			Item item = (Item) obj;
			return item.getRegistryName().equals(this.getRegistryName());
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "Item[" + this.getRegistryName() + ":" + this.getLocalizedName() + "]";
	}

	public static void registerItem(Item item) {
		if (ITEMS.containsKey(item.getRegistryName())) {
			throw new RuntimeException("Item \'" + item + "\' attempted to override item \'" + ITEMS.get(item.getRegistryName()) + "\'!");
		}
		ITEMS.put(item.getRegistryName(), item);
	}

	public static Item byName(String registryName) {
		return ITEMS.get(registryName);
	}

	public static Set<String> keySet() {
		return ITEMS.keySet();
	}
}