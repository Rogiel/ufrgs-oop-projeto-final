package br.ufrgs.dunjeonsdragons.template;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Rogiel on 9/13/16.
 */
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
