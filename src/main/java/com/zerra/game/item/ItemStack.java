package com.zerra.game.item;

import com.zerra.util.I18n;
import com.zerra.util.ISerializable;
import com.zerra.util.data.ByteDataContainer;

public class ItemStack implements ISerializable<ByteDataContainer> {

	public static final ItemStack EMPTY = new ItemStack((Item) null);

	private Item item;
	private int count;

	private ItemStack() {
	}

	public ItemStack(Item item) {
		this(item, 1);
	}

	public ItemStack(Item item, int count) {
		this.item = item;
		this.count = count;
	}

	public ItemStack(ByteDataContainer data) {
		this.deserialize(data);
	}

	public void shrink(int amount) {
		this.grow(-amount);
	}

	public void grow(int amount) {
		this.setCount(count + amount);
	}

	public boolean isEmpty() {
		return this.item == null || this.count <= 0;
	}

	public Item getItem() {
		return item;
	}

	public int getCount() {
		return count;
	}

	public void setItem(Item item) {
		if (this == ItemStack.EMPTY)
			return;
		this.item = item;
	}

	public void setCount(int count) {
		if (this == ItemStack.EMPTY)
			return;
		this.count = count;
	}

	public ItemStack copy() {
		return this.copy(new ItemStack());
	}

	public ItemStack copy(ItemStack stack) {
		stack.item = this.item;
		stack.count = this.count;
		return stack;
	}

	public ByteDataContainer serialize() {
		ByteDataContainer container = new ByteDataContainer();
		container.setString("id", this.item.getRegistryName());
		container.setInt("c", this.count);
		return container;
	}

	public void deserialize(ByteDataContainer data) {
		this.item = Item.byName(data.getString("id"));
		this.count = data.getInt("c");
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return this.copy();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemStack) {
			ItemStack stack = (ItemStack) obj;
			return stack.getItem() != null && stack.getItem().equals(this.getItem());
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return this.item == null ? I18n.format("item.null.name") : this.item + "x" + this.count;
	}
}