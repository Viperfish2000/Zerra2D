package com.zerra.object;

import org.joml.Vector3f;

import com.zerra.Zerra;

public class Camera extends GLObject implements ICamera {

	@Override
	public Vector3f getPosition() {
		renderPosition.x = lastPosition.x + (position.x - lastPosition.x) * Zerra.getInstance().getRenderPartialTicks();
		renderPosition.y = lastPosition.y + (position.y - lastPosition.y) * Zerra.getInstance().getRenderPartialTicks();
		renderPosition.z = lastPosition.z + (position.z - lastPosition.z) * Zerra.getInstance().getRenderPartialTicks();
		return renderPosition;
	}

	@Override
	public Vector3f getLastPosition() {
		return lastPosition;
	}

	@Override
	public Vector3f getRotation() {
		renderRotation.x = lastRotation.x + (rotation.x - lastRotation.x) * Zerra.getInstance().getRenderPartialTicks();
		renderRotation.y = lastRotation.y + (rotation.y - lastRotation.y) * Zerra.getInstance().getRenderPartialTicks();
		renderRotation.z = lastRotation.z + (rotation.z - lastRotation.z) * Zerra.getInstance().getRenderPartialTicks();
		return renderRotation;
	}

	@Override
	public Vector3f getLastRotation() {
		return lastRotation;
	}

	public float getPitch() {
		return rotation.x;
	}

	public float getYaw() {
		return rotation.y;
	}

	public float getRoll() {
		return rotation.z;
	}
}