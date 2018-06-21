package com.ocelot.gfx.texture;

import com.ocelot.util.ResourceLocation;

public class ModelTexture {

	private ResourceLocation texture;
	private int numberOfRows;
	private boolean hasTransparency;
	private boolean useFakeLighting;

	public ModelTexture(ResourceLocation texture) {
		this.texture = texture;
		this.numberOfRows = 1;
		this.hasTransparency = false;
		this.useFakeLighting = false;
	}

	public ResourceLocation getTexture() {
		return texture;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public boolean hasTransparency() {
		return hasTransparency;
	}

	public boolean usesFakeLighting() {
		return useFakeLighting;
	}

	public ModelTexture setNumberOfRows(int numberOfRows) {
		if(numberOfRows < 1)
			numberOfRows = 1;
		this.numberOfRows = numberOfRows;
		return this;
	}

	public ModelTexture setTransparent(boolean transparent) {
		this.hasTransparency = transparent;
		return this;
	}

	public ModelTexture setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
		return this;
	}
}