package com.zerra.util;

import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.image.BufferedImage;
import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Display {

	private static String title;
	private static int width;
	private static int height;

	private static DoubleBuffer mouseXBuffer = BufferUtils.createDoubleBuffer(1);
	private static DoubleBuffer mouseYBuffer = BufferUtils.createDoubleBuffer(1);

	private static long windowID;
	private static long cursorID;

	private Display() {
	}

	public static void createDisplay(String title, int width, int height) {
		Display.title = title;
		Display.width = width;
		Display.height = height;

		if (isCreated())
			throw new RuntimeException("Display was already created!");
		if (!GLFW.glfwInit()) {
			throw new RuntimeException("Failed to initialize GLFW");
		}
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);

		windowID = GLFW.glfwCreateWindow(width, height, title, NULL, NULL);

		if (windowID == NULL) {
			throw new RuntimeException("Could not create window");
		}

		GLFW.glfwShowWindow(windowID);
		GLFW.glfwFocusWindow(windowID);

		// GLFW.glfwSetKeyCallback(windowID, Keyboard.INSTANCE);
		// GLFW.glfwSetMouseButtonCallback(windowID, Mouse.INSTANCE);
		// GLFW.glfwSetScrollCallback(windowID, Mouse.Scroll.INSTANCE);

		GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(windowID, (int) ((vidMode.width() * 0.5) - (width * 0.5)), (int) ((vidMode.height() * 0.5) - (height * 0.5)));

		GLFW.glfwSwapInterval(1);
		
		GLFW.glfwMakeContextCurrent(windowID);
		GL.createCapabilities();
	}

	public static void update() {
		GLFW.glfwPollEvents();
		GLFW.glfwSwapBuffers(windowID);
	}

	public static void destroy() {
		if (cursorID != NULL) {
			GLFW.glfwDestroyCursor(cursorID);
		}
		GLFW.glfwDestroyWindow(windowID);
		// Keyboard.INSTANCE.free();
		// Mouse.INSTANCE.free();
		// Mouse.Scroll.INSTANCE.free();
	}

	public static void close() {
		GLFW.glfwSetWindowShouldClose(windowID, true);
	}

	public static void setCursor(BufferedImage image, int xhot, int yhot) {
		GLFWImage cursorImage = GLFWImage.create();
		cursorImage.width(image.getWidth());
		cursorImage.height(image.getHeight());
		cursorImage.pixels(Loader.loadToByteBuffer(image));
		cursorID = GLFW.glfwCreateCursor(cursorImage, xhot, yhot);
		if (cursorID != NULL) {
			GLFW.glfwSetCursor(windowID, cursorID);
		}
	}

	public static String getTitle() {
		return title;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public static double getMouseX() {
		GLFW.glfwGetCursorPos(windowID, mouseXBuffer, mouseYBuffer);
		return mouseXBuffer.get(0);
	}

	public static double getMouseY() {
		GLFW.glfwGetCursorPos(windowID, mouseXBuffer, mouseYBuffer);
		return mouseYBuffer.get(0);
	}

	public static int getMouseButton() {
		return GLFW.glfwGetMouseButton(windowID, 0) == GLFW.GLFW_TRUE ? 0 : GLFW.glfwGetMouseButton(windowID, 1) == GLFW.GLFW_TRUE ? 1 : GLFW.glfwGetMouseButton(windowID, 2) == GLFW.GLFW_TRUE ? 2 : -1;
	}

	public static boolean isCreated() {
		return windowID != NULL;
	}

	public static boolean isCloseRequested() {
		return GLFW.glfwWindowShouldClose(windowID);
	}

	public static boolean isKeyPressed(int key) {
		return GLFW.glfwGetKey(windowID, key) == GLFW.GLFW_PRESS;
	}
	
	public static void setTitle(String title) {
		GLFW.glfwSetWindowTitle(windowID, title);
		Display.title = title;
	}
	
	public static void setSize(int width, int height) {
		GLFW.glfwSetWindowSize(windowID, width, height);
		Display.width = width;
		Display.height = height;
	}
}