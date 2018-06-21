package com.zerra.game.math;

public class MathUtils {

	public static int clamp(int value, int min, int max) {
		if(value < min) {
			value = min;
		}
		if(value > max) {
			value = max;
		}
		return value;
	}
}
