package br.ufrgs.dunjeonsdragons.model;

import br.ufrgs.dunjeonsdragons.event.GameEvent;
import br.ufrgs.dunjeonsdragons.gamelogic.GameManager;
import br.ufrgs.dunjeonsdragons.template.LevelTemplate;
import br.ufrgs.dunjeonsdragons.template.MapTemplate;
import br.ufrgs.dunjeonsdragons.ui.GameUIController;

import java.io.IOException;

/**
 * Created by Rogiel on 12/1/16.
 */
public class GameMap extends GameObject {

    public static final String DEFAULT_MAP_ENTITY_NAME = "GameMap";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The underlying map template
     */
    private final MapTemplate template;

    /**
     * The current level index
     */
    private int currentLevelIndex = 0;

    // -----------------------------------------------------------------------------------------------------------------

    public enum State {
        RUNNING,
        VICTORY,
        DEFEAT
    }

    private State state = State.RUNNING;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The player instance
     */
    private GamePlayer player;

    /**
     * The current level being played
     */
    private GameLevel currentLevel;

    // -----------------------------------------------------------------------------------------------------------------

    private GameUIController uiController;

    /**
     * Create a new game map instance
     *
     * @param template the map template
     * @param player the human player on the map
     */
    public GameMap(final MapTemplate template, final GamePlayer player) {
        this.template = template;
        this.player = player;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void performTurn(long turn) {
        try {
            uiController.handleUserInput();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameManager.triggerEvent(new GameEvent());

        if(player.isDead()) {
            state = State.DEFEAT;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Advances the map to the next level
     */
    public void nextLevel() {
        // remove this level from the game manager
        gameManager.removeEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME);
        currentLevelIndex++;

        // create the next level
//        if (currentLevelIndex >= template.getLevels().size()) {
//            state = State.VICTORY;
//            return;
//        }

        final LevelTemplate levelTemplate = template.getLevels().get(currentLevelIndex);
        final GameLevel level = new GameLevel(levelTemplate, player, template.getLevel());

        // add the next level entity
        gameManager.addEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME, level);

        this.currentLevel = level;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void didAddToGameManager(GameManager gameManager) {
        super.didAddToGameManager(gameManager);

        uiController = new GameUIController(gameManager);

        final LevelTemplate levelTemplate = template.getLevels().get(currentLevelIndex);
        final GameLevel level = new GameLevel(levelTemplate, player, template.getLevel());
        gameManager.addEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME, level);
        this.currentLevel = level;
    }

    @Override
    public void didRemoveFromGameManager(GameManager gameManager) {
        super.didRemoveFromGameManager(gameManager);
        gameManager.removeEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME);
        uiController = null;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * @return the level being played
     */
    public GameLevel getCurrentLevel() {
        return currentLevel;
    }

    /**
     * @return the game state
     */
    public State getState() {
        return state;
    }
}
