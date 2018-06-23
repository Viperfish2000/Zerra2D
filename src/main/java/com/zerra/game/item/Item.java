package com.zerra.game.item;

public class Item {

	private int damage = -1;
	private int maxDamage = -1;
	private boolean isStackable = true;
	private boolean shouldRenderDamageBar = false;
	
	public Item() {
		
	}
	
	/**
	 * Gets the damage of the item.
	 * 
	 * @return
	 * 		The damage of the item.
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * Sets the damage of the item.
	 * 
	 * @param damage
	 * 			Sets the damage of the item to the specified value.
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	/**
	 * Gets the max damage that the item can hold.
	 * 
	 * @return The max damage the item can hold.
	 */
	public int getMaxDamage() {
		return maxDamage;
	}
	
	/**
	 * @param maxDamage
	 * 				The value the max damage of the item will be set to.
	 */
	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}
	
	/**
	 * @return Whether or not the item is able to be damaged.
	 */
	public boolean isDamagable() {
		return maxDamage > 0 ? true : false;
	}

	/**
	 * @return
	 * 		Whether or not the item is able to be stacked.
	 */
	public boolean isStackable() {
		return isStackable;
	}

	/**
	 * @param isStackable
	 * 				Determines whether or not the item can be stacked.
	 */
	public void setStackable(boolean isStackable) {
		this.isStackable = isStackable;
	}
	
	/**
	 * Sets whether or not the damage bar of the item should render.
	 * 
	 * @param shouldRender
	 * 				Should the damage bar be rendered.
	 */
	public void setRenderDamageBar(boolean shouldRender) {
		shouldRenderDamageBar = shouldRender;
	}
	
	/**
	 * Gets whether or not this item should have its damage bar rendered.
	 * 
	 * @return 
	 * 		If the damage bar is being rendered or not.
	 */
	public boolean shouldRenderDamageBar() {
		return shouldRenderDamageBar;
	}
}
