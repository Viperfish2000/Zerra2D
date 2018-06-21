package com.zerra.object;

import org.joml.Vector3f;

import com.zerra.Game;
import com.zerra.gfx.ICamera;

public class Camera extends GameObject implements ICamera {

	@Override
	public void update() {
		super.update();
	}

	@Override
	public Vector3f getPosition() {
		renderPosition.x = lastPosition.x + (position.x - lastPosition.x) * Game.getInstance().getRenderPartialTicks();
		renderPosition.y = lastPosition.y + (position.y - lastPosition.y) * Game.getInstance().getRenderPartialTicks();
		renderPosition.z = lastPosition.z + (position.z - lastPosition.z) * Game.getInstance().getRenderPartialTicks();
		return renderPosition;
	}

	@Override
	public Vector3f getRotation() {
		renderRotation.x = lastRotation.x + (rotation.x - lastRotation.x) * Game.getInstance().getRenderPartialTicks();
		renderRotation.y = lastRotation.y + (rotation.y - lastRotation.y) * Game.getInstance().getRenderPartialTicks();
		renderRotation.z = lastRotation.z + (rotation.z - lastRotation.z) * Game.getInstance().getRenderPartialTicks();
		return renderRotation;
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