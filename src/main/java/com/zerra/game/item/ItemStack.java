package com.zerra.game.item;

public class ItemStack {

	private Item item;
	private int size = 0;
	private int maxSize = 100;
	
	public ItemStack(Item item, byte size) {
		this.setItem(item);
		this.setSize(size);
	}

	/**
	 * @return 
	 * 		The item the ItemStack contains.
	 */
	public Item getItem() {
		return item;
	}
	
	/**
	 * Sets the item that the ItemStack contains. This should not be accessible to the outside world.
	 * 
	 * @param item 
	 * 			The item that the ItemStack will hold.
	 */
	private void setItem(Item item) {
		this.item = item;
	}

	/**
	 * @return 
	 * 		The number of items in the ItemStack.
	 */
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return 
	 * 		Gets the max size an ItemStack can hold.
	 */
	public int getMaxSize() {
		return maxSize;
	}
	
	/**
	 * Adds 1 to the ItemStack size.
	 */
	public void incrementStack() {
		if(this.getSize() < 64) {
			this.setSize((byte) (this.getSize()+1));
		}
	}
	
	/**
	 * Subtracts 1 from the ItemStack size.
	 */
	public void decrementStack() {
		if(this.getSize() > 0) {
			this.setSize((byte) (this.getSize()-1));
		}
	}

	/**
	 * Increments the stack size by the specified amount.
	 * 
	 * @param amount
	 * 			The amount to add to the stack. If the amount added to the stack results in a stack size above 100, the stack size will simply be set to 100.
	 */
	public void incrementStackBy(int amount) {
		if(this.getSize() + amount < this.getMaxSize()) {
			this.setSize(this.getSize() + amount);
		}else {
			this.setSize(this.getMaxSize());
		}
	}

	/**
	 * Decrements the stack size by the specified amount.
	 * 
	 * @param amount
	 * 			The amount to remove from the stack. If the amount removed from the stack results in a stack size below 0, the stack size will simply be set to 0.
	 */
	public void decrementStackBy(int amount) {
		if(this.getSize() - amount > 0) {
			this.setSize(this.getSize() - amount);
		}else {
			this.setSize(0);
		}
	}
}
