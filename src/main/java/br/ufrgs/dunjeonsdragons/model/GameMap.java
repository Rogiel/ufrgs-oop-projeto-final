package br.ufrgs.dunjeonsdragons.model;

import br.ufrgs.dunjeonsdragons.gamelogic.GameManager;
import br.ufrgs.dunjeonsdragons.template.LevelTemplate;
import br.ufrgs.dunjeonsdragons.template.MapTemplate;

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

    /**
     * The player instance
     */
    private GamePlayer player;

    /**
     * The current level being played
     */
    private GameLevel currentLevel;

    // -----------------------------------------------------------------------------------------------------------------

    public GameMap(final MapTemplate template, final GamePlayer player) {
        this.template = template;
        this.player = player;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public void nextLevel() {
        // remove this level from the game manager
        gameManager.removeEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME);

        // create the next level
        if (template.getLevels().size() >= currentLevelIndex) {
            // TODO win!!!
            return;
        }

        final LevelTemplate levelTemplate = template.getLevels().get(currentLevelIndex++);
        final GameLevel level = new GameLevel(levelTemplate, player);

        // add the next level entity
        gameManager.addEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME, level);

        this.currentLevel = level;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void didAddToGameManager(GameManager gameManager) {
        super.didAddToGameManager(gameManager);

        final LevelTemplate levelTemplate = template.getLevels().get(currentLevelIndex);
        final GameLevel level = new GameLevel(levelTemplate, player);
        gameManager.addEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME, level);
        this.currentLevel = level;
    }

    @Override
    public void didRemoveFromGameManager(GameManager gameManager) {
        super.didRemoveFromGameManager(gameManager);
        gameManager.removeEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public GameLevel getCurrentLevel() {
        return currentLevel;
    }
}
