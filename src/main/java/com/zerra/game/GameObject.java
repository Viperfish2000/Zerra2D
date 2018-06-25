package com.zerra.game;

import javax.annotation.Nonnull;

import org.joml.Vector2f;

import com.zerra.gfx.renderer.EntityRenderer;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.util.AxisAlignedBB;
import com.zerra.util.ResourceLocation;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * A basic, abstract, object that can be added to the game. It has an x and a y position.
 * 
 * @author Ocelot5836
 */
public abstract class GameObject {

	/** The x and y position of the object */
	private float x, y;
	/** The last x and y position of the object */
	private float lastX, lastY;

	/**
	 * Updates the object. Called 60 times per second.
	 */
	public abstract void update();

	/**
	 * Called when the object is rendered. Does not actually render the object.
	 * 
	 * @param renderer
	 *            The renderer for the game
	 * @param entityRenderer
	 *            The renderer that rendered this entity
	 */
	public abstract void render(MasterRenderer renderer, EntityRenderer entityRenderer, float partialTicks);

	/**
	 * Used for collisions. If you want to use null, use {@link AxisAlignedBB#EMPTY_AABB} instead.
	 * 
	 * @return The x, y, and size of the entity
	 */
	@Nonnull
	public abstract AxisAlignedBB getCollisionBox();

	/**
	 * Used when rendering to texture this object.
	 * 
	 * @return The texture of this object
	 */
	@Nonnull
	public abstract ResourceLocation getTexture();

	/**
	 * Used when rendering to texture this object. Only really used in a texture atlas.
	 * 
	 * @return The offset
	 */
	@Nonnull
	public abstract Vector2f getTextureOffset();

	/**
	 * This is used in a texture atlas when rendering. This cannot be zero
	 * 
	 * @return The width of the texture in tiles
	 */
	public int getTextureWidth() {
		return 1;
	}

	/**
	 * @return This object's x position
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return This object's y position
	 */
	public float getY() {
		return y;
	}

	/**
	 * @return This object's rendering y position
	 */
	public float getPartialRenderX(float partialTicks) {
		return lastX + (x - lastX) * partialTicks;
	}

	/**
	 * @return This object's rendering y position
	 */
	public float getPartialRenderY(float partialTicks) {
		return lastY + (y - lastY) * partialTicks;
	}

	/**
	 * Sets this entity's position.
	 * 
	 * @param x
	 *            The new x position for the entity
	 * @param y
	 *            The new y position for the entity
	 */
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets this entity's x position
	 * 
	 * @param x
	 *            The new x position for the entity
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Sets this entity's y position
	 * 
	 * @param y
	 *            The new y position for the entity
	 */
	public void setY(float y) {
		this.y = y;
	}

	public void setLastX(float lastX) {
		this.lastX = lastX;
	}

	public void setLastY(float lastY) {
		this.lastY = lastY;
	}
}
