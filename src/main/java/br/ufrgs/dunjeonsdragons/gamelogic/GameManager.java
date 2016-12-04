package br.ufrgs.dunjeonsdragons.gamelogic;

import br.ufrgs.dunjeonsdragons.event.GameEvent;
import br.ufrgs.dunjeonsdragons.event.GameEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rogiel on 9/13/16.
 */
public class GameManager {

    /**
     * A list entities currently managed by the game
     */
    private Map<String, GameEntity> entities;

    /**
     * A list of active game event listeners
     */
    private List<GameEventListener> eventListeners = new ArrayList<>();

    /**
     * The current game turn
     */
    private long turn;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new GameManager instance
     */
    public GameManager() {
        entities = new HashMap<>();
        turn = 0;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public void addEntity(String name, GameEntity entity) {
        if(entities.put(name, entity) != null) {
            return;
        }
        entity.didAddToGameManager(this);
    }

    public void removeEntity(String name) {
        final GameEntity removed = entities.remove(name);
        if(removed == null) {
            return;
        }
        removed.didRemoveFromGameManager(this);
    }

    public GameEntity getEntity(String name) {
        return entities.get(name);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public void addEventListener(GameEventListener eventListener) {
        this.eventListeners.add(eventListener);
    }
    public void removeEventListener(GameEventListener eventListener) {
        this.eventListeners.remove(eventListener);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public void performTurn() {
        final List<GameEntity> entities = new ArrayList<>(this.entities.values());
        for(GameEntity entity : entities) {
            entity.performTurn(turn);
        }
        turn++;
    }

    public void triggerEvent(final GameEvent event) {
        for(final GameEventListener listener : eventListeners) {
            listener.onGameEvent(event);
        }
    }

}
