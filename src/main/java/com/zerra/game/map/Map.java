package com.zerra.game.map;

import java.awt.image.BufferedImage;

public class Map {

	private BufferedImage map;
	
	public Map(BufferedImage map) {
		this.setImage(map);
	}

	public BufferedImage getMap() {
		return map;
	}

	public void setImage(BufferedImage map) {
		this.map = map;
	}
}
