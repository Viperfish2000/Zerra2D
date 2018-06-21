package com.zerra.game.manager;

import java.awt.Graphics;
import java.util.concurrent.CopyOnWriteArrayList;

import com.zerra.Game;
import com.zerra.game.world.tile.Tile;
import com.zerra.game.world.tile.TileGrass;


public class TileManager {

	private static CopyOnWriteArrayList<Tile> tilesToRender = new CopyOnWriteArrayList<Tile>();
	
	public static CopyOnWriteArrayList<Tile> getTiles() {
		return tilesToRender;
	}
	
	public void createTile(Tile tile) {
		if(tile == null) {
			tile = new TileGrass(0, 0);
		}
		tilesToRender.add(tile);
	}
	
	public void destroyTile(Tile Tile) {
		tilesToRender.remove(Tile);
	}
	
	public void tick() {
		for (Tile tile : tilesToRender) {
			tile.tick();
		}
	}
	
	public void render(Graphics g) {
		for (Tile tile : tilesToRender) {
			if(tile.getY() < Game.HEIGHT - 32 && tile.getY() > 0 - 32 && tile.getX() < Game.WIDTH && tile.getX() > 0 - 32) {
				tile.render(g);
			}else {
				tilesToRender.remove(tile);
				tile = null;
			}
		}
	}
}
