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
    private long levelNumber = 1;

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
        monster.resetHealth();

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
