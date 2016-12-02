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

    public GameLevel(final LevelTemplate template, final GamePlayer player) {
        this.template = template;
        this.player = player;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void performTurn(long turn) {
        if (monster.isDead()) {
            complete = true;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void didAddToGameManager(GameManager gameManager) {
        super.didAddToGameManager(gameManager);

        monster = new GameMonster(template.getMonster());
        monster.setHealth(10);

        gameManager.addEntity("LevelMonster", monster);
        player.setTarget(monster);
    }

    @Override
    public void didRemoveFromGameManager(GameManager gameManager) {
        super.didRemoveFromGameManager(gameManager);
        gameManager.removeEntity("LevelMonster");
        player.setTarget(null);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public GameMonster getMonster() {
        return monster;
    }

    public boolean isComplete() {
        return complete;
    }
}
