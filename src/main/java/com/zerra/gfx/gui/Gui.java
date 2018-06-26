package com.zerra.gfx.gui;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.zerra.util.Maths;
import com.zerra.util.ResourceLocation;

public class Gui {

	private List<GuiTexture> textures;

	public Gui() {
		this.textures = new ArrayList<GuiTexture>();
	}

	public void update() {
	}

	public void render(double mouseX, double mouseY, float partialTicks) {
	}

	protected void drawTexturedRect(ResourceLocation texture, float x, float y, float width, float height, float u, float v, float textureSize) {
		textures.add(new GuiTexture(texture, new Vector4f(u / textureSize, v / textureSize, width / textureSize, height / textureSize), Maths.createTransformationMatrix(new Vector2f(x, y), new Vector2f(width, height))));
	}

	public List<GuiTexture> getTextures() {
		return textures;
	}
}