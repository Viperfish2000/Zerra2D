package com.zerra.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.zerra.Game;
import com.zerra.game.world.tile.Tile;
import com.zerra.game.world.tile.TileRock;
import com.zerra.game.world.tile.World;

public class EntityPlayer extends EntityLiving {

	public EntityPlayer() {
		this(10, 20);
	}
	
	public EntityPlayer(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setType(EntityType.PLAYER);
	}
	
	@Override
	public void tick() {
		super.tick();
		if(this.ticksExisted % 4 == 0) {
			collision();
		}
	}
	
	private void collision() {
		for (Tile tile : World.getTiles()) {
			if(tile instanceof TileRock) {
				if(getBounds().intersects(((TileRock)tile).getBounds())) {
					Game.manager.getPlayerMover().setVelX(0);
					Game.manager.getPlayerMover().setVelY(0);
					this.setHealth(this.getHealth() - 5);
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(this.getX(), this.getY(), 32, 32);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(this.getX(), this.getY(), 32, 32);
	}
}
