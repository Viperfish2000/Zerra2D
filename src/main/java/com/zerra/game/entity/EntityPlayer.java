package com.zerra.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.zerra.game.inventory.PlayerInventory;

public class EntityPlayer extends EntityLiving {

	private int exp = 0;
	private int level = 0;
	private PlayerInventory inventory;
	
	public EntityPlayer() {
		this(10, 20);
	}
	
	public EntityPlayer(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setType(EntityType.PLAYER);
		this.inventory = new PlayerInventory(45);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
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

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public PlayerInventory getInventory() {
		return inventory;
	}
}
