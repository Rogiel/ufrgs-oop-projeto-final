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

package br.ufrgs.dunjeonsdragons.ui;

import br.ufrgs.dunjeonsdragons.gamelogic.GameManager;
import br.ufrgs.dunjeonsdragons.model.GameLevel;
import br.ufrgs.dunjeonsdragons.model.GameMap;
import br.ufrgs.dunjeonsdragons.model.GameMonster;
import br.ufrgs.dunjeonsdragons.model.GamePlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.StringTokenizer;

public class GameUIController {

    private static final String ATTACK_COMMAND = "attack";
    private static final String NEXT_LEVEL_COMMAND = "next-level";
    private static final String NEXT_MAP_COMMAND = "next-map";
    private static final String RESET_MAP_COMMAND = "reset-map";
    private static final String STATUS_COMMAND = "status";
    private static final String EXIT_COMMAND = "exit";
    private static final String SHOW_EXPERIENCE = "experience";
    private static final String COMBAT = "combat";
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A buffered reader that reads from stdin, used to read complete lines from the
     * terminal to get user input
     */
    private final BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in)
    );

    /**
     * The game manager this controller is currently managing
     */
    private GameManager gameManager;

    public GameUIController(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public boolean handleUserInput() throws IOException {
        final GameMap map = (GameMap) gameManager.getEntity(GameMap.DEFAULT_MAP_ENTITY_NAME);
        if (map != null && map.getState() == GameMap.State.DEFEAT) {
            System.out.println("You lose. Game over.");
            return true;
        }

        final String commandLine = reader.readLine();
        final StringTokenizer tokenizer = new StringTokenizer(commandLine);

        final String command = tokenizer.nextToken().toLowerCase();

        switch (command) {
            case ATTACK_COMMAND:
                handleAttack(tokenizer);
                gameManager.performTurn();

                break;

            case NEXT_LEVEL_COMMAND:
                nextLevel(tokenizer);
                break;
            case NEXT_MAP_COMMAND:
                nextMap(tokenizer);
                break;
            case RESET_MAP_COMMAND:
                resetMap(tokenizer);
                break;

            case STATUS_COMMAND:
                handleStatus(tokenizer);
                gameManager.performTurn();
                break;

            case SHOW_EXPERIENCE:
                handleExperience(tokenizer);
                break;

            case COMBAT:
                handleCombat(tokenizer);
                break;

            case EXIT_COMMAND:
                return false;

            default:
                System.err.println("Unknown command: " + command);
                break;
        }

        return false;
    }

    private void handleCombat(StringTokenizer tokenizer) {
        final GameLevel gameLevel = (GameLevel) gameManager.getEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME);
        final GameMonster monster = gameLevel.getMonster();

        while (!monster.isDead()) {
            handleAttack(tokenizer);
        }
        handleStatus(tokenizer);
    }

    private void handleAttack(final StringTokenizer tokenizer) {
        final GamePlayer player = (GamePlayer) gameManager.getEntity(GamePlayer.DEFAULT_PLAYER_ENTITY_NAME);
        final GameLevel gameLevel = (GameLevel) gameManager.getEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME);

        player.attack(gameLevel.getMonster()); // player attacks mob
        if (!gameLevel.getMonster().isDead()) {
            gameLevel.getMonster().attack(player); // mob attacks player
        }

        if (gameLevel.isComplete()) {
            final GameMap gameMap = (GameMap) gameManager.getEntity(GameMap.DEFAULT_MAP_ENTITY_NAME);
            gameMap.nextLevel();
        }

    }

    private void nextLevel(final StringTokenizer tokenizer) {
        final GameMap map = (GameMap) gameManager.getEntity(GameMap.DEFAULT_MAP_ENTITY_NAME);
        if (!map.getCurrentLevel().isComplete()) {
            System.err.println("Current level is not complete.");
            return;
        }
        map.nextLevel();
    }

    private void nextMap(final StringTokenizer tokenizer) {
        final GameMap map = (GameMap) gameManager.getEntity(GameMap.DEFAULT_MAP_ENTITY_NAME);
        if (map.getState() != GameMap.State.MAP_COMPLETE) {
            System.err.println("You must first win this map before going to the next.");
            return;
        }
        map.nextMap();
    }

    private void resetMap(final StringTokenizer tokenizer) {
        final GameMap map = (GameMap) gameManager.getEntity(GameMap.DEFAULT_MAP_ENTITY_NAME);
        map.resetMap();
    }

    private void handleStatus(final StringTokenizer tokenizer) {
        final GamePlayer character = (GamePlayer) gameManager.getEntity(GamePlayer.DEFAULT_PLAYER_ENTITY_NAME);
        System.out.println("Character: " + character.getName() + ", Race: " + character.getRaceTemplate().getName() + ", Classe: " + character.getClassTemplate().getName() + " Level: " + character.getLevel());
        System.out.println("\tHealth: " + NumberFormat.getNumberInstance().format(character.getHealth()));
        System.out.println("\tDamage: " + NumberFormat.getNumberInstance().format(character.getDamage()));

        final GameLevel level = (GameLevel) gameManager.getEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME);
        if (level != null) {
            final GameMonster monster = level.getMonster();
            if (monster == null) {
                System.out.println("Monster:");
                System.out.println("\tNo monster on current level");
            } else {
                System.out.println("Monster: " + monster.getName());
                System.out.println("\tHealth: " + NumberFormat.getNumberInstance().format(monster.getHealth()));
                System.out.println("\tDamage: " + monster.getDamage());
            }
        }
    }

    private void handleExperience(final StringTokenizer tokenizer) {
        final GamePlayer character = (GamePlayer) gameManager.getEntity(GamePlayer.DEFAULT_PLAYER_ENTITY_NAME);
        System.out.println("Character: " + character.getName() + ", Race: " + character.getRaceTemplate().getName() + ", Classe: " + character.getClassTemplate().getName());
        System.out.println("\tExperience: " + NumberFormat.getNumberInstance().format(character.getExperience()));
    }

}
