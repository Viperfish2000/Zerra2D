package com.zerra.game.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.zerra.game.entity.Entity;
import com.zerra.game.entity.EntityType;
import com.zerra.game.manager.EntityManager;

@Deprecated
public class KeyInput extends KeyAdapter {

	private EntityManager manager;
	
	public KeyInput(EntityManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode();
		
		for (Entity entity : manager.getEntities().keySet()) {
			if(entity.getType() == EntityType.PLAYER) {
				
				//Key events for the player
				if(key == KeyEvent.VK_W) {
					entity.setVelY(3);
				}
				if(key == KeyEvent.VK_A) {
					entity.setVelX(3);
				}
				if(key == KeyEvent.VK_S) {
					entity.setVelY(-3);
				}
				if(key == KeyEvent.VK_D) {
					entity.setVelX(-3);
				}
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent event) {
		int key = event.getKeyCode();
		
		for (Entity entity : manager.getEntities().keySet()) {
			if(entity.getType() == EntityType.PLAYER) {
				
				//Key events for the player
				if(key == KeyEvent.VK_W) {
					entity.setVelY(0);
				}
				if(key == KeyEvent.VK_A) {
					entity.setVelX(0);
				}
				if(key == KeyEvent.VK_S) {
					entity.setVelY(0);
				}
				if(key == KeyEvent.VK_D) {
					entity.setVelX(0);
				}
			}
		}
	}
}
