package br.ufrgs.dunjeonsdragons.template;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Rogiel on 9/13/16.
 */
@XmlRootElement(name = "Race")
public class PlayerRaceTemplate extends Template {

    /**
     *
     */
    @XmlElement(name = "Name")
    private String name;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The class strength per level
     */
    @XmlElement(name = "Strength")
    private int strength;

    /**
     * The class strength per level
     */
    @XmlElement(name = "StrengthPerLevel")
    private int strengthPerLevel;

    /**
     * The class vitality per level
     */
    @XmlElement(name = "Vitality")
    private int vitality;

    /**
     * The class vitality per level
     */
    @XmlElement(name = "VitalityPerLevel")
    private int vitalityPerLevel;

    /**
     * The class dexterity per level
     */
    @XmlElement(name = "Dexterity")
    private int dexterity;

    /**
     * The class dexterity per level
     */
    @XmlElement(name = "DexterityPerLevel")
    private int dexterityPerLevel;

    /**
     * The class intelligence per level
     */
    @XmlElement(name = "Intelligence")
    private int intelligence;

    /**
     * The class intelligence per level
     */
    @XmlElement(name = "IntelligencePerLevel")
    private int intelligencePerLevel;

    // -----------------------------------------------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    public int getStrength(int level) {
        return strength + strengthPerLevel * level;
    }

    public int getStrength() {
        return strength;
    }

    public int getStrengthPerLevel() {
        return strengthPerLevel;
    }

    public int getVitality(int level) {
        return vitality + vitalityPerLevel * level;
    }

    public int getVitality() {
        return vitality;
    }

    public int getVitalityPerLevel() {
        return vitalityPerLevel;
    }

    public int getDexterity(int level) {
        return dexterity + vitalityPerLevel * level;
    }
    public int getDexterity() {
        return dexterity;
    }

    public int getDexterityPerLevel() {
        return dexterityPerLevel;
    }

    public int getIntelligence(int level) {
        return intelligence + intelligencePerLevel * level;
    }
    public int getIntelligence() {
        return intelligence;
    }

    public int getIntelligencePerLevel() {
        return intelligencePerLevel;
    }

}
