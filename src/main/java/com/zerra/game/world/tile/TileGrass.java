package com.zerra.game.world.tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.zerra.Game;

public class TileGrass extends TileBase {
	
	public static BufferedImage image;

	public TileGrass(int x, int y) {
		super(x, y);
		
		try {
			TileGrass.image = ImageIO.read(new File("grass.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public void render(Graphics g) {
		if(this.getY() < Game.HEIGHT - 32 && this.getY() > 0 - 32 && this.getX() < Game.WIDTH && this.getX() > 0 - 32) {
			g.drawImage(image, 32, 32, null);
		}
	}

	
}
