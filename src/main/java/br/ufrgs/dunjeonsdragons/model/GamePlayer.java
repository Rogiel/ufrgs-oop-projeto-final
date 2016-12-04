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

import br.ufrgs.dunjeonsdragons.template.ExperienceTableEntry;
import br.ufrgs.dunjeonsdragons.template.PlayerClassTemplate;
import br.ufrgs.dunjeonsdragons.template.PlayerRaceTemplate;

import java.util.ArrayList;
import java.util.List;

public class GamePlayer extends GameCharacter {

    public static final String DEFAULT_PLAYER_ENTITY_NAME = "GamePlayer";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A template for the player class
     */
    private PlayerClassTemplate classTemplate;

    /**
     * A template for the player race
     */
    private PlayerRaceTemplate raceTemplate;

    /**
     * The players experience table
     */
    private List<ExperienceTableEntry> experienceTable;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The character name
     */
    private String name;

    /**
     * The player level
     */
    private int level = 1;

    /**
     * The player experience
     */
    private long experience;

    // -----------------------------------------------------------------------------------------------------------------

    public GamePlayer(PlayerRaceTemplate raceTemplate, PlayerClassTemplate classTemplate, List<ExperienceTableEntry> experienceTable) {
        this.raceTemplate = raceTemplate;
        this.classTemplate = classTemplate;
        this.experienceTable = experienceTable;
        resetHealth();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public double getDamage() {
        switch (classTemplate.getPrimaryStat()) {
            case "Strength":
                return getStrength();
            case "Vitality":
                return getVitality();
            case "Dexterity":
                return getDexterity();
            case "Intelligence":
                return getIntelligence();
            case "StrengthIntelligence":
                return getStrength() + getIntelligence();
            default:
                return 0;
        }
    }

    @Override
    public double getMaxHealth() {
        return getVitality();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void didKill(GameCharacter character) {
        if (character instanceof GameMonster) {
            addExperience(((GameMonster) character).getExperienceOnKill());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    public void transferClass(PlayerClassTemplate newClassTemplate) {
        if (!classTemplate.getSubclasses().contains(newClassTemplate)) {
            // TODO use a proper exception
            throw new RuntimeException("The player cannot transfer to the class " + newClassTemplate.getName());
        }
        classTemplate = newClassTemplate;
        resetHealth();
    }

    public void addExperience(long experienceToAdd) {
        this.experience += experienceToAdd;
        System.out.println(name + " got " + experienceToAdd + " experience points.");
        for (ExperienceTableEntry entry : experienceTable) {
            if (this.experience >= entry.getExperience() && this.level < entry.getLevel()) {
                this.level = (int) entry.getLevel();
            }
        }
    }

    public boolean hasSubclassOptions() {
        return classTemplate.isAllowClassTransfer() && level >= classTemplate.getClassTransferLevel();
    }

    public List<PlayerClassTemplate> getSubclassOptions() {
        if (hasSubclassOptions()) {
            final List<PlayerClassTemplate> list = new ArrayList<>();
            for (final PlayerClassTemplate subclassTemplate : this.classTemplate.getSubclasses()) {
                if (subclassTemplate.getRaceRestrictions().contains(raceTemplate)) {
                    list.add(subclassTemplate);
                }
            }
            return list;
        }
        return new ArrayList<>();
    }

    // -----------------------------------------------------------------------------------------------------------------

    public int getStrength() {
        return raceTemplate.getStrength(level) + classTemplate.getStrength(level);
    }

    public int getVitality() {
        return raceTemplate.getVitality(level) + classTemplate.getVitality(level);
    }

    public int getDexterity() {
        return raceTemplate.getDexterity(level) + classTemplate.getDexterity(level);
    }

    public int getIntelligence() {
        return raceTemplate.getIntelligence(level) + classTemplate.getIntelligence(level);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * @return the player class template
     */
    public PlayerClassTemplate getClassTemplate() {
        return classTemplate;
    }

    /**
     * @return the player race template
     */
    public PlayerRaceTemplate getRaceTemplate() {
        return raceTemplate;
    }

    /**
     * @return the player level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return the player experience points
     */
    public long getExperience() {
        return experience;
    }

    /**
     * @return the amount of experience points required to the next level
     */
    public long getExperienceRequiredForNextLevel() {
        for (ExperienceTableEntry entry : experienceTable) {
            if (this.level + 1 == entry.getLevel()) {
                return entry.getExperience();
            }
        }
        return this.experience;
    }

    public long getMinimumExperienceForCurrentLevel() {
        for (ExperienceTableEntry entry : experienceTable) {
            if (this.level == entry.getLevel()) {
                return entry.getExperience();
            }
        }
        return 0;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
