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

package br.ufrgs.dunjeonsdragons.template;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Class")
public class PlayerClassTemplate extends Template {

    /**
     * The player class name
     */
    @XmlElement(name = "Name")
    private String name;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The class strength per level
     */
    @XmlElement(name = "StrengthPerLevel")
    private int strengthPerLevel;

    /**
     * The class vitality per level
     */
    @XmlElement(name = "VitalityPerLevel")
    private int vitalityPerLevel;

    /**
     * The class dexterity per level
     */
    @XmlElement(name = "DexterityPerLevel")
    private int dexterityPerLevel;

    /**
     * The class intelligence per level
     */
    @XmlElement(name = "IntelligencePerLevel")
    private int intelligencePerLevel;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The class intelligence per level
     */
    @XmlElement(name = "PrimaryStat")
    private String primaryStat;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The race restrictions
     */
    @XmlElementWrapper(name = "RaceRestriction")
    @XmlElement(name = "Race")
    @XmlIDREF
    private List<PlayerRaceTemplate> raceRestrictions;

    /**
     * The subclasses
     */
    @XmlElementWrapper(name = "Subclasses")
    @XmlElement(name = "Class")
    @XmlIDREF
    private List<PlayerClassTemplate> subclasses;

    // -----------------------------------------------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    public int getStrength(int level) {
        return strengthPerLevel * level;
    }

    public int getStrengthPerLevel() {
        return strengthPerLevel;
    }

    public int getVitality(int level) {
        return vitalityPerLevel * level;
    }

    public int getVitalityPerLevel() {
        return vitalityPerLevel;
    }

    public int getDexterity(int level) {
        return vitalityPerLevel * level;
    }

    public int getDexterityPerLevel() {
        return dexterityPerLevel;
    }

    public int getIntelligence(int level) {
        return intelligencePerLevel * level;
    }

    public int getIntelligencePerLevel() {
        return intelligencePerLevel;
    }

    public String getPrimaryStat() {
        return primaryStat;
    }

    public List<PlayerRaceTemplate> getRaceRestrictions() {
        return raceRestrictions;
    }

    public List<PlayerClassTemplate> getSubclasses() {
        return subclasses;
    }
}
