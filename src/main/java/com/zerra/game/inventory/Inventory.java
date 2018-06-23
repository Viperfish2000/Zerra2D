package com.zerra.game.inventory;

import java.util.Arrays;

import com.zerra.game.item.ItemStack;
import com.zerra.util.ISerializable;
import com.zerra.util.data.ByteDataContainer;

public class Inventory implements IInventory, ISerializable<ByteDataContainer> {

	private ItemStack[] items;
	private int firstEmptySlot;

	public Inventory(int size) {
		this.items = new ItemStack[size];
		this.clear();
		this.firstEmptySlot = -1;
	}

	public ItemStack addStackToInventory(ItemStack stack) {
		if (stack == ItemStack.EMPTY)
			return stack;
		ItemStack stackCopy = stack.copy();
		while (!stackCopy.isEmpty() && !this.isFull()) {
			int slot = this.getApplicableSlot(stackCopy);
			ItemStack stackInSlot = this.getStackInSlot(slot);
			if (slot < 0)
				break;
			if (stackInSlot.isEmpty()) {
				if (stackCopy.getCount() >= stackCopy.getItem().getMaxStackSize()) {
					items[slot] = new ItemStack(stackCopy.getItem(), stackCopy.getItem().getMaxStackSize());
					stackCopy.shrink(stackCopy.getItem().getMaxStackSize());
				} else {
					items[slot] = new ItemStack(stackCopy.getItem(), stackCopy.getCount());
					stackCopy.shrink(stackCopy.getCount());
				}
			} else {
				if (stackCopy.getCount() + items[slot].getCount() >= stackCopy.getItem().getMaxStackSize()) {
					int shrinkAmount = items[slot].getCount();
					items[slot].setCount(stackCopy.getItem().getMaxStackSize());
					stackCopy.shrink(shrinkAmount);
				} else {
					items[slot].grow(stackCopy.getCount());
					stackCopy.shrink(stackCopy.getCount());
				}
			}
		}
		return stackCopy;
	}

	public int getFirstEmptySlot() {
		if (firstEmptySlot < 0 || firstEmptySlot >= items.length || !items[firstEmptySlot].isEmpty()) {
			for (int i = 0; i < items.length; i++) {
				if (items[i].isEmpty()) {
					return i;
				}
			}
			return firstEmptySlot = -1;
		}
		return firstEmptySlot;
	}

	public int getApplicableSlot(ItemStack stack) {
		for (int i = 0; i < items.length; i++) {
			ItemStack tempStack = items[i];
			if ((tempStack.equals(stack) && tempStack.getCount() < items[i].getItem().getMaxStackSize()) || tempStack.isEmpty()) {
				return i;
			}
		}
		return this.getFirstEmptySlot();
	}

	@Override
	public int getSize() {
		return items.length;
	}

	@Override
	public boolean isFull() {
		for (int i = 0; i < items.length; i++) {
			ItemStack stack = items[i];
			if (stack.isEmpty() || stack.getCount() < items[i].getItem().getMaxStackSize()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot < 0 || slot >= items.length)
			return ItemStack.EMPTY;
		return items[slot];
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		if (slot < 0 || slot >= items.length)
			return;
		items[slot] = stack;
	}

	@Override
	public void clear() {
		for (int i = 0; i < items.length; i++) {
			items[i] = ItemStack.EMPTY;
		}
	}

	@Override
	public ByteDataContainer serialize() {
		ByteDataContainer container = new ByteDataContainer();
		for (int i = 0; i < items.length; i++) {
			container.setTag(Integer.toString(i), items[i].serialize());
		}
		return container;
	}

	@Override
	public void deserialize(ByteDataContainer data) {
		for (int i = 0; i < items.length; i++) {
			if (data.hasKey(Integer.toString(i), 3)) {
				items[i] = new ItemStack(data.getByteContainer(Integer.toString(i)));
			}
		}
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + Arrays.toString(this.items);
	}
}