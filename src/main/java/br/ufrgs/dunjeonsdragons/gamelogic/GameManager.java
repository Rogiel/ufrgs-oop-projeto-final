package br.ufrgs.dunjeonsdragons.gamelogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rogiel on 9/13/16.
 */
public class GameManager {

    /**
     * A list entities currently managed by the game
     */
    private List<GameEntity> entities;

    /**
     * The last time the "run" method was called
     */
    private long lastUpdate;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new GameManager instance
     */
    public GameManager() {
        entities = new ArrayList<>();
        lastUpdate = System.currentTimeMillis();
    }

    // -----------------------------------------------------------------------------------------------------------------

    public void addEntity(GameEntity entity) {
        if(!entities.add(entity)) {
            return;
        }
        entity.didAddToGameManager(this);
    }

    public void removeEntity(GameEntity entity) {
        if(!entities.remove(entity)) {
            return;
        }
        entity.didRemoveFromGameManager(this);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public void update() {
        long now = System.currentTimeMillis();
        long delta = now - lastUpdate;
        lastUpdate = now;

        for(GameEntity entity : entities) {
            entity.update(delta / 1000.0);
        }
    }

}
