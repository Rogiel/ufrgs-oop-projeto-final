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
package br.ufrgs.dunjeonsdragons;

import br.ufrgs.dunjeonsdragons.gamelogic.GameManager;
import br.ufrgs.dunjeonsdragons.model.GameMap;
import br.ufrgs.dunjeonsdragons.model.GamePlayer;
import br.ufrgs.dunjeonsdragons.template.PlayerClassTemplate;
import br.ufrgs.dunjeonsdragons.template.PlayerRaceTemplate;
import br.ufrgs.dunjeonsdragons.template.Template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        final GameEntityFactory factory = new GameEntityFactory(
                Main.class.getClassLoader().getResource("GameData.xml")
        );
        final GameManager gameManager = new GameManager();

        System.out.print("Welcome To DunJeons & Dragons! \n\n~~~ AQUI VAI A LISTA DE COMANDOS ~~~\n\n");

        final GamePlayer player = createPlayerFromInput(factory);
        gameManager.addEntity(GamePlayer.DEFAULT_PLAYER_ENTITY_NAME, player);

        final GameMap map = factory.createMap("TOWER_1", player);
        gameManager.addEntity(GameMap.DEFAULT_MAP_ENTITY_NAME, map);

        while (true) {
            gameManager.performTurn();
            if (map.getState() == GameMap.State.DEFEAT) {
                break;
            }
        }
    }

    private static GamePlayer createPlayerFromInput(final GameEntityFactory factory) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Create your Character \n");
        System.out.print("Character name: ");
        final String name = reader.readLine();

        System.out.print("Choose your Race (HUMAN - ORC - ELF): ");
        final String race = reader.readLine();

        final Template raceTemplateRaw = factory.loader.load(race);
        if (raceTemplateRaw == null) {
            System.err.println("Race " + race + " is invalid.");
        }

        final PlayerRaceTemplate raceTemplate = (PlayerRaceTemplate) raceTemplateRaw;

        final StringBuilder builder = new StringBuilder("Choose your Class (");
        for (PlayerClassTemplate allowedClassTemplate : raceTemplate.getStartingClasses()) {
            builder.append(allowedClassTemplate.getIdentifier() + " - ");
        }
        builder.delete(builder.length() - 3, builder.length());
        builder.append("): ");

        System.out.print(builder.toString());
        final String classe = reader.readLine();

        final PlayerClassTemplate classTemplate = (PlayerClassTemplate) factory.loader.load(classe);
        if (classTemplate == null) {
            System.err.println("Class " + classe + " is invalid.");
        }
        if (!raceTemplate.getStartingClasses().contains(classTemplate)) {
            System.err.println("Race " + race + " cannot be of class " + classe);
        }

        final GamePlayer player = factory.createPlayer(race, classe, factory.loader.getExperienceTable());
        if (!name.isEmpty()) {
            player.setName(name);
        }

        System.out.print("Character creation succesful! \n");
        return player;
    }
}
