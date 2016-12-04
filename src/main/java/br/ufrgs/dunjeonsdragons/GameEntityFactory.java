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

import br.ufrgs.dunjeonsdragons.model.GameMap;
import br.ufrgs.dunjeonsdragons.model.GameMonster;
import br.ufrgs.dunjeonsdragons.model.GamePlayer;
import br.ufrgs.dunjeonsdragons.template.*;
import br.ufrgs.dunjeonsdragons.template.loader.TemplateLoader;
import br.ufrgs.dunjeonsdragons.template.loader.XMLTemplateLoader;

import java.net.URL;
import java.util.List;

public class GameEntityFactory {

    /**
     * The template loader instance
     */
    public TemplateLoader loader;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new GameEntityFactory instance
     *
     * @param dataFile the datafile path
     */
    public GameEntityFactory(URL dataFile) {
        loader = new XMLTemplateLoader(dataFile);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new player object from the given classIdentifier template identifier
     *
     * @param classIdentifier the player class template identifier
     * @return a newly created GamePlayer object
     */
    public GamePlayer createPlayer(String raceIdentifier, String classIdentifier, List<ExperienceTableEntry> experienceTable) {
        final PlayerRaceTemplate raceTemplate = (PlayerRaceTemplate) loader.load(raceIdentifier);
        final PlayerClassTemplate classTemplate = (PlayerClassTemplate) loader.load(classIdentifier);

        if (raceTemplate == null) {
            // TODO use a proper exception here
            throw new RuntimeException("Invalid race template");
        }

        if (classTemplate == null) {
            // TODO use a proper exception here
            throw new RuntimeException("Invalid class template");
        }

        if (!classTemplate.getRaceRestrictions().contains(raceTemplate)) {
            throw new RuntimeException("The race " + raceIdentifier + " cannot be of class " + classIdentifier);
        }

        return new GamePlayer(
                (PlayerRaceTemplate) loader.load(raceIdentifier),
                (PlayerClassTemplate) loader.load(classIdentifier),
                experienceTable
        );
    }

    /**
     * Creates a new monster object from the given monster template identifier
     *
     * @param monsterTemplateIdentifier the monster template identifier
     * @param level                     the monster level
     * @return a newly created GameMonster object
     */
    public GameMonster createMonster(String monsterTemplateIdentifier, final int level) {
        return new GameMonster(
                (MonsterTemplate) loader.load(monsterTemplateIdentifier), level
        );
    }

    /**
     * Creates a new map object from the given map template identifier
     * and player instance
     *
     * @param mapTemplateIdentifier the map template identifier
     * @return a newly created GameMap object
     */
    public GameMap createMap(String mapTemplateIdentifier, final GamePlayer player) {
        return new GameMap(
                (MapTemplate) loader.load(mapTemplateIdentifier),
                player
        );
    }

}
