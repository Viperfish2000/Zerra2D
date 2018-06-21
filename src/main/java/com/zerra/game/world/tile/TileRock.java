package com.zerra.game.world.tile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.zerra.Game;

public class TileRock extends TileBase {
	
	public static BufferedImage image;

	public TileRock(int x, int y) {
		super(x, y);
		if(image == null) {
			try {
				TileRock.image = ImageIO.read(new File("rock.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
	}
	
	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		
		
		if(this.getY() < Game.HEIGHT - 32 && this.getY() > 0 - 32 && this.getX() < Game.WIDTH && this.getX() > 0 - 32) {
			g.drawImage(image, this.getX(), this.getY(), 32, 32, null);
			g.setColor(Color.RED);
			g2d.draw(this.getBounds());
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(this.getX(), this.getY(), 32, 32);
	}
}
