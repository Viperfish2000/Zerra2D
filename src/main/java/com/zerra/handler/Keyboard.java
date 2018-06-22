package com.zerra.handler;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard extends GLFWKeyCallback {

	public static final Keyboard INSTANCE = new Keyboard();

	private Keyboard() {
	}

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if (action == GLFW.GLFW_PRESS) {
			if (key == GLFW.GLFW_KEY_F11) {
				// if (Display.isFullscreen()) {
				// Display.setWindowed();
				// } else {
				// Display.setFullscreen();
				// }
			}
		}
	}
}