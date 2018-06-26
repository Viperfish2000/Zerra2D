package com.zerra.game.manager;

import javax.swing.Renderer;

public class GameState {

	private String stateName;

	public GameState(String name) {
		this.stateName = name;
	}

	public String getStateName() {
		return stateName;
	}

	public void update() {
	}

	public void render(Renderer renderer, double mouseX, double mouseY, float partialTicks) {
	}

	public void onKeyPressed() {
	}

	public void onKeyReleased() {
	}

	public void onMousePressed() {
	}

	public void onMouseReleased() {
	}
}