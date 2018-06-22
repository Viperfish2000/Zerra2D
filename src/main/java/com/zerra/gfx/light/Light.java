package com.zerra.gfx.light;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Light {

	private Vector2f position;
	private Vector3f color;
	private float size;

	public Light(Vector2f position, Vector3f color, float size) {
		this.position = position;
		this.color = color;
		this.size = size;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector3f getColor() {
		return color;
	}

	public float getSize() {
		return size;
	}
}