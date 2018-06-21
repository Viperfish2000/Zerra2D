package com.zerra.game.world.tile;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.zerra.Game;

public class TileBase extends Tile {

	public TileBase(int x, int y) {
		this.setX(x*32 + Game.manager.getPlayerMover().getX());
		this.setY(y*32 + Game.manager.getPlayerMover().getY());
	}

	@Override
	public void tick() {
		this.setX(this.getX() + Game.manager.getPlayerMover().getVelX());
		this.setY(this.getY() + Game.manager.getPlayerMover().getVelY());
	}
	
	@Override
	public void render(Graphics g) {
		
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}
}
