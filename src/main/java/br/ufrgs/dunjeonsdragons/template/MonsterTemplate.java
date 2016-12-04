package br.ufrgs.dunjeonsdragons.template;

import javax.xml.bind.annotation.XmlElement;

public class MonsterTemplate extends Template {

    /**
     * The monster name
     */
    @XmlElement(name = "Name")
    private String name;

    /**
     * The damage per level
     */
    @XmlElement(name = "DamagePerLevel")
    private double damagePerLevel;

    /**
     * The health per level
     */
    @XmlElement(name = "HealthPerLevel")
    private double healthPerLevel;

    /**
     * The experience given by the monster
     */
    @XmlElement(name = "ExperiencePerLevel")
    private long experiencePerLevel;

    public String getName() {
        return name;
    }

    public double getDamagePerLevel() {
        return damagePerLevel;
    }

    public double getHealthPerLevel() {
        return healthPerLevel;
    }

    public long getExperiencePerLevel() {
        return experiencePerLevel;
    }
}
