package com.zerra.util;

public enum EntityEquipmentSlot {
	HEAD(1), BODY(2), LEGS(3), HAND(0);

	private int index;
	
	private EntityEquipmentSlot(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
}