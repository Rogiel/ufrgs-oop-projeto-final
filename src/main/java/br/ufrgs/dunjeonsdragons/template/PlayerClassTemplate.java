package br.ufrgs.dunjeonsdragons.template;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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

    public String getName() {
        return name;
    }

    public int getStrengthPerLevel() {
        return strengthPerLevel;
    }

    public int getVitalityPerLevel() {
        return vitalityPerLevel;
    }

    public int getDexterityPerLevel() {
        return dexterityPerLevel;
    }

    public int getIntelligencePerLevel() {
        return intelligencePerLevel;
    }

}
