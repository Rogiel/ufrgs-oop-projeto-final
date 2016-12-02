package br.ufrgs.dunjeonsdragons.model;

import br.ufrgs.dunjeonsdragons.gamelogic.GameEntity;
import br.ufrgs.dunjeonsdragons.gamelogic.GameManager;

/**
 * Created by Rogiel on 9/6/16.
 */
public abstract class GameObject implements GameEntity {

    /**
     * The game manager the entity is attached at
     */
    protected GameManager gameManager;

    @Override
    public void performTurn(long turn) {
        // NO OP
    }

    @Override
    public void didAddToGameManager(GameManager gameManager) {
        if(this.gameManager != null) {
            throw new RuntimeException("This GameObject is already registered with a game manager.");
        }
        this.gameManager = gameManager;
    }

    @Override
    public void didRemoveFromGameManager(GameManager gameManager) {
        if(this.gameManager != gameManager) {
            throw new RuntimeException("This GameObject is not registered with the given game manager.");
        }
        this.gameManager = null;
    }
}
