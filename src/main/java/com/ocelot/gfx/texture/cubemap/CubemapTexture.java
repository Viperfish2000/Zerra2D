package com.ocelot.gfx.texture.cubemap;

import com.ocelot.gfx.texture.ITexture;

public class CubemapTexture implements ITexture {

	private int id;
	
	public CubemapTexture(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public int getWidth() {
		return 0;
	}

	@Override
	public int getHeight() {
		return 0;
	}
}