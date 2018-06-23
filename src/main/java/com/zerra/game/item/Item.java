package com.zerra.game.item;

public class Item {

	private int damage = -1;
	private int maxDamage = -1;
	private boolean isStackable = true;
	private boolean shouldRenderDamageBar = false;
	
	public Item() {
		
	}
	
	/**
	 * @return
	 * 		The damage of the item.
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * @param damage
	 * 			Sets the damage of the item to the specified value.
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	/**
	 * @return The max damage the Item can hold.
	 */
	public int getMaxDamage() {
		return maxDamage;
	}
	
	/**
	 * @param maxDamage
	 * 				The value the max damage of the Item will be set to.
	 */
	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}
	
	/**
	 * @return Whether or not the Item is able to be damaged.
	 */
	public boolean isDamagable() {
		return maxDamage > 0 ? true : false;
	}

	/**
	 * @return
	 * 		Whether or not the Item is able to be stacked.
	 */
	public boolean isStackable() {
		return isStackable;
	}

	/**
	 * @param isStackable
	 * 				Determines whether or not the Item can be stacked.
	 */
	public void setStackable(boolean isStackable) {
		this.isStackable = isStackable;
	}
	
	/**
	 * @param shouldRender
	 * 				Sets whether or not the damage bar of the item should render.
	 */
	public void setRenderDamageBar(boolean shouldRender) {
		shouldRenderDamageBar = shouldRender;
	}
	
	/**
	 * @return Whether or not the damage bar of the item should be rendered.
	 */
	public boolean shouldRenderDamageBar() {
		return shouldRenderDamageBar;
	}
}
