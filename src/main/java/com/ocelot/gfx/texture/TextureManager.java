package com.ocelot.gfx.texture;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import com.ocelot.util.Loader;
import com.ocelot.util.LoadingUtils;
import com.ocelot.util.ResourceLocation;

public class TextureManager {

	public static final ResourceLocation MISSING_TEXTURE_LOCATION = new ResourceLocation("missingno");

	@Nonnull
	private ResourceLocation boundTextureLocation;
	private Map<String, ITexture> textures;

	public TextureManager() {
		textures = new HashMap<String, ITexture>();
		this.load(MISSING_TEXTURE_LOCATION, Loader.loadTexture(LoadingUtils.createMissingImage(256, 256)));
		boundTextureLocation = MISSING_TEXTURE_LOCATION;
	}
	
	public void loadTexture(ResourceLocation location, ITexture texture) {
		textures.put(location.toString(), texture);
	}

	public void bind(ResourceLocation location) {
		if (location == null)
			return;
		if (textures.get(location.toString()) == null) {
			textures.put(location.toString(), Loader.loadTexture(location));
		}
		this.boundTextureLocation = location;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures.get(location.toString()).getId());
	}

	public ITexture getTexture(ResourceLocation location) {
		if (location == null)
			location = MISSING_TEXTURE_LOCATION;
		bind(location);
		return textures.get(location.toString());
	}

	public ResourceLocation getBoundTextureLocation() {
		return boundTextureLocation;
	}

	public void load(ResourceLocation location, ITexture texture) {
		textures.put(location.toString(), texture);
	}
}