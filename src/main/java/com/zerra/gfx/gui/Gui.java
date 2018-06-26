package com.zerra.gfx.gui;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import com.zerra.gfx.renderer.GuiRenderer;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.object.Quad;
import com.zerra.util.Display;
import com.zerra.util.Maths;
import com.zerra.util.ResourceLocation;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * A basic, empty gui that can be drawn to the screen.
 * 
 * @author Ocelot5836
 */
public class Gui {

	private Quad defaultBackgroundQuad;

	private List<GuiTexture> textures;
	private List<Component> components;

	public Gui() {
		this.defaultBackgroundQuad = new Quad(new Vector3f(), new Vector3f(), new Vector3f(Display.getWidth() / GuiRenderer.scale), new Vector4f(0, 0, 0, 0.25f));
		this.textures = new ArrayList<GuiTexture>();
		this.components = new ArrayList<Component>();
	}

	/**
	 * Updates this gui.
	 */
	public void update() {
		for (Component component : components) {
			if (component.isVisible()) {
				component.update();
			}
		}
	}

	/**
	 * Renders this gui to the screen. Use one of the supplied render methods to render to the screen.
	 * 
	 * @param renderer
	 *            The main game renderer
	 * @param mouseX
	 *            The x position of the mouse
	 * @param mouseY
	 *            The y position of the mouse
	 * @param partialTicks
	 *            The percentage between this update and last update
	 */
	public void render(MasterRenderer renderer, double mouseX, double mouseY, float partialTicks) {
		for (Component component : components) {
			if (component.isVisible()) {
				component.render(renderer, mouseX, mouseY, partialTicks);
			}
		}
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
	public void drawTexturedRect(ResourceLocation texture, float x, float y, float width, float height, float u, float v, float textureSize) {
		textures.add(new GuiTexture(texture, new Vector4f(u, v, width, height), textureSize, Maths.createTransformationMatrix(new Vector2f(x, y), new Vector2f(width, height))));
	}

	/**
	 * Draws the default background.
	 * 
	 * @param renderer
	 *            The renderer used for the game
	 */
	public void drawDefaultBackground(MasterRenderer renderer) {
		renderer.renderQuads(defaultBackgroundQuad);
	}

	/**
	 * Adds the supplied component to this gui
	 * 
	 * @param component
	 *            The component to add
	 */
	protected void addComponent(Component component) {
		component.setParent(this);
		this.components.add(component);
	}

	/**
	 * @return All the rectangles rendered to the gui to be rendered in {@link GuiRenderer#render(List, double, double, float)}
	 */
	public List<GuiTexture> getTextures() {
		return textures;
	}
}