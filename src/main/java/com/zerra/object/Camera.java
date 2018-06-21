package com.zerra.object;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import com.zerra.Game;
import com.zerra.util.Display;

public class Camera extends GLObject implements ICamera {

	@Override
	public void update() {
		super.update();

		float speed = 4.0f;

		if (Display.isKeyPressed(GLFW.GLFW_KEY_W)) {
			position.y -= speed;
		}

		if (Display.isKeyPressed(GLFW.GLFW_KEY_S)) {
			position.y += speed;
		}

		if (Display.isKeyPressed(GLFW.GLFW_KEY_A)) {
			position.x -= speed;
		}

		if (Display.isKeyPressed(GLFW.GLFW_KEY_D)) {
			position.x += speed;
		}
	}

	@Override
	public Vector3f getPosition() {
		renderPosition.x = lastPosition.x + (position.x - lastPosition.x) * Game.getInstance().getRenderPartialTicks();
		renderPosition.y = lastPosition.y + (position.y - lastPosition.y) * Game.getInstance().getRenderPartialTicks();
		renderPosition.z = lastPosition.z + (position.z - lastPosition.z) * Game.getInstance().getRenderPartialTicks();
		return renderPosition;
	}

	@Override
	public Vector3f getLastPosition() {
		return lastPosition;
	}

	@Override
	public Vector3f getRotation() {
		renderRotation.x = lastRotation.x + (rotation.x - lastRotation.x) * Game.getInstance().getRenderPartialTicks();
		renderRotation.y = lastRotation.y + (rotation.y - lastRotation.y) * Game.getInstance().getRenderPartialTicks();
		renderRotation.z = lastRotation.z + (rotation.z - lastRotation.z) * Game.getInstance().getRenderPartialTicks();
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