package com.zerra.util;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * Allows the easy distinction between equipment slots for entities.
 * 
 * @author Ocelot5836
 */
public enum EntityEquipmentSlot {
	HEAD(1), BODY(2), LEGS(3), FEET(4), HAND(0);

	private int index;

	private EntityEquipmentSlot(int index) {
		this.index = index;
	}

	/**
	 * @return The index of this equipment slot
	 */
	public int getIndex() {
		return index;
	}
}