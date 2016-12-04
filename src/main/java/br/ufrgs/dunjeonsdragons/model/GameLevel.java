package br.ufrgs.dunjeonsdragons.model;

import br.ufrgs.dunjeonsdragons.gamelogic.GameManager;
import br.ufrgs.dunjeonsdragons.template.LevelTemplate;

/**
 * Created by Rogiel on 12/1/16.
 */
public class GameLevel extends GameObject {

    public static final String DEFAULT_LEVEL_ENTITY_NAME = "GameLevel";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The level template
     */
    private final LevelTemplate template;

    /**
     * The level number
     */
    private final long levelNumber;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The level player
     */
    private final GamePlayer player;

    /**
     * The level monster
     */
    private GameMonster monster;

    // -----------------------------------------------------------------------------------------------------------------

    private boolean complete = false;

    // -----------------------------------------------------------------------------------------------------------------

    public GameLevel(final LevelTemplate template, final GamePlayer player, final long level) {
        this.template = template;
        this.player = player;
        this.levelNumber = level;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void performTurn(long turn) {

    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void didAddToGameManager(GameManager gameManager) {
        super.didAddToGameManager(gameManager);

        monster = new GameMonster(template.getRandomMonster(), (int) levelNumber);
        gameManager.addEntity("LevelMonster", monster);
    }

    @Override
    public void didRemoveFromGameManager(GameManager gameManager) {
        super.didRemoveFromGameManager(gameManager);
        gameManager.removeEntity("LevelMonster");
    }

    // -----------------------------------------------------------------------------------------------------------------

    public GameMonster getMonster() {
        return monster;
    }

    public boolean isComplete() {
        return monster.isDead();
    }
}
