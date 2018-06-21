package com.zerra.game;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {

	private int x, y;

	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
