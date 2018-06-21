package com.zerra.util;

import java.io.InputStream;

public class ResourceLocation {

	private String location;

	public ResourceLocation(String location) {
		this.location = location;
	}

	public ResourceLocation(ResourceLocation location) {
		this.location = location.location;
	}

	public ResourceLocation(ResourceLocation folder, String location) {
		this.location = folder.location + "/" + location;
	}

	public String getLocation() {
		return location;
	}

	public InputStream getInputStream() {
		return ResourceLocation.class.getResourceAsStream("/" + location);
	}

	@Override
	public String toString() {
		return "/" + location;
	}
}