package com.zerra.gfx.gui;

import com.zerra.util.ResourceLocation;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * An abstract component that can be added to a gui.
 * 
 * @author Ocelot5836
 */
public class Component {

	private Gui parent;
	private float x;
	private float y;
	private float width;
	private float height;

	public Component(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Updates the component.
	 */
	protected void update() {
	}

	/**
	 * Renders the component to the screen.
	 * 
	 * @param mouseX
	 *            The x position of the mouse
	 * @param mouseY
	 *            The y position of the mouse
	 * @param partialTicks
	 *            The percentage between this update and last update
	 */
	protected void render(double mouseX, double mouseY, float partialTicks) {
	}

	/**
	 * Draws a textured quad to the screen.
	 * 
	 * @param texture
	 *            The texture this quad will use
	 * @param x
	 *            The x position of the quad
	 * @param y
	 *            The y position of the quad
	 * @param width
	 *            The width of the quad and texture coords
	 * @param height
	 *            The height of the quad and texture coords
	 * @param u
	 *            The x of the part of the texture to sample
	 * @param v
	 *            The y of the part of the texture to sample
	 * @param textureSize
	 *            The size of the texture. Width and height should be the same since you can only bind square textures
	 */
	protected void drawTexturedRect(ResourceLocation texture, float x, float y, float width, float height, float u, float v, float textureSize) {
		parent.drawTexturedRect(texture, this.x + x, this.y + y, width, height, u, v, textureSize);
	}

	/**
	 * Checks the mouse position to see if it is over this gui.
	 * 
	 * @param mouseX
	 *            The x position of the mouse
	 * @param mouseY
	 *            The y position of the mouse
	 * @return Whether or not the mouse is within this component or not
	 */
	public boolean isHovered(double mouseX, double mouseY) {
		return mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;
	}

	/**
	 * @return The gui that is rendering this component
	 */
	protected Gui getParent() {
		return parent;
	}

	/**
	 * @return The x position of this component
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return The y position of this component
	 */
	public float getY() {
		return y;
	}

	/**
	 * @return The width of this component
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @return The height of this component
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Sets the parent gui. <i><b>THIS SHOULD NEVER BE TOUCHED BY CHILD CLASSES</b></i>
	 * 
	 * @param parent
	 *            The gui that is rendering this component
	 */
	protected void setParent(Gui parent) {
		this.parent = parent;
	}
}