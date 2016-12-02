package br.ufrgs.dunjeonsdragons.gamelogic;

/**
 * Created by Rogiel on 9/13/16.
 */
public interface GameEntity {

    /**
     * Executes the game loop for this entity.
     *
     * @param turn the turn number being simulated
     */
    void performTurn(long turn);

    /**
     * A event called whenever a entity is added to a game manager
     *
     * @param gameManager the game manager that the entity was registered at
     */
    void didAddToGameManager(GameManager gameManager);

    /**
     * A event called whenever a entity is removed to a game manager
     *
     * @param gameManager the game manager that the entity was unregistered from
     */
    void didRemoveFromGameManager(GameManager gameManager);

}
