package com.zerra.gfx;

import org.joml.Vector3f;

public interface ICamera {

	Vector3f getPosition();

	Vector3f getRotation();

	Vector3f getScale();
}