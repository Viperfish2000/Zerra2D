package com.zerra.game.manager;

import java.util.HashMap;
import java.util.Map;

import com.zerra.Zerra;

public class GameStateManager {

	private static GameStateManager manager;
	
	private static Map<Integer, GameState> gameStates = new HashMap<Integer, GameState>();
	private static int numOfStates = 0;
	
	private static GameState currentState;

	private GameStateManager() {
		GameStateManager.manager.addGameState(new GameState("UNKNOWN"));
		GameStateManager.manager.addGameState(new GameState("STARTUP"));
		GameStateManager.manager.addGameState(new GameState("MAINMENU"));
		GameStateManager.manager.addGameState(new GameState("SETTINGS"));
		GameStateManager.manager.addGameState(new GameState("LOADING"));
		GameStateManager.manager.addGameState(new GameState("WORLDMENU"));
		GameStateManager.manager.addGameState(new GameState("WORLD"));
		GameStateManager.manager.addGameState(new GameState("INGAMESETTINGS"));
	}
	
	public static GameStateManager getManager() {
		if(manager == null) {
			manager = new GameStateManager();
		}
		return manager;
	}
	
	public Map<Integer, GameState> getGameStates() {
		return gameStates;
	}

	public GameState addGameState(GameState gameState) {
		for(int i = 0; i < gameStates.size(); i++) {
			if(gameStates.get(i).getStateName().matches(gameState.getStateName())) {
				Zerra.logger().error("Game state with name " + gameState.getStateName() + " already exists!");
				return null;
			}
		}
		return gameStates.put(numOfStates++, gameState);
	}

	public static GameState getCurrentState() {
		return currentState;
	}

	public static void setCurrentState(GameState currentState) {
		GameStateManager.currentState = currentState;
	}
}
