package com.zerra.object;

import org.joml.Vector3f;

public interface ICamera {

	Vector3f getPosition();

	Vector3f getLastPosition();

	Vector3f getRotation();

	Vector3f getLastRotation();

	Vector3f getScale();

	Vector3f getDirection();
}