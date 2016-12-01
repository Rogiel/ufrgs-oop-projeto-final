package br.ufrgs.dunjeonsdragons.gamelogic;

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
     * The last time the "run" method was called
     */
    private long lastUpdate;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new GameManager instance
     */
    public GameManager() {
        entities = new HashMap<>();
        lastUpdate = System.currentTimeMillis();
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

    public void update() {
        long now = System.currentTimeMillis();
        long delta = now - lastUpdate;
        lastUpdate = now;

        for(GameEntity entity : entities.values()) {
            entity.update(delta / 1000.0);
        }
    }

}
