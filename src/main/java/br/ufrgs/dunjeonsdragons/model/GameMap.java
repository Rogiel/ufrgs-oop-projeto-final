/*
 * Copyright (c) 2016
 *      Rogiel Sulzbach, Júlio Balena & Gustavo Oliveira.
 *      All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *      This product includes software developed by the copyright holders
 *      and its contributors.
 * 4. Neither the name of the copyright holders nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

package br.ufrgs.dunjeonsdragons.model;

import br.ufrgs.dunjeonsdragons.gamelogic.GameManager;
import br.ufrgs.dunjeonsdragons.template.LevelTemplate;
import br.ufrgs.dunjeonsdragons.template.MapTemplate;
import br.ufrgs.dunjeonsdragons.ui.GameUIController;

import java.io.IOException;

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
     * The map level
     */
    private long level = 1;

    // -----------------------------------------------------------------------------------------------------------------

    public enum State {
        RUNNING,
        VICTORY,
        DEFEAT,
        MAP_COMPLETE
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
     * @param player   the human player on the map
     */
    public GameMap(final MapTemplate template, final GamePlayer player) {
        this.template = template;
        this.player = player;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void performTurn(long turn) {
        if (player.isDead()) {
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
        if (currentLevelIndex >= template.getLevels().size()) {
            state = State.MAP_COMPLETE;
            return;
        }

        final LevelTemplate levelTemplate = template.getLevels().get(currentLevelIndex);
        final GameLevel level = new GameLevel(levelTemplate, player, this.level);

        // add the next level entity
        gameManager.addEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME, level);

        // heal player when going to the next level
        player.resetHealth();

        this.currentLevel = level;
    }

    /**
     * Advances to the next map
     */
    public void nextMap() {
        this.level++;
        resetMap();
    }

    /**
     * Resets the map
     */
    public void resetMap() {
        currentLevelIndex = -1;
        state = State.RUNNING;
        nextLevel();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void didAddToGameManager(GameManager gameManager) {
        super.didAddToGameManager(gameManager);

        uiController = new GameUIController(gameManager);

        final LevelTemplate levelTemplate = template.getLevels().get(currentLevelIndex);
        final GameLevel level = new GameLevel(levelTemplate, player, this.level);
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
