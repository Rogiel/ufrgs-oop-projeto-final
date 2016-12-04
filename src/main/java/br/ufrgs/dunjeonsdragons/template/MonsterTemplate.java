package br.ufrgs.dunjeonsdragons.template;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Rogiel on 9/13/16.
 */
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

    public String getName() {
        return name;
    }

    public double getDamagePerLevel() {
        return damagePerLevel;
    }

    public double getHealthPerLevel() {
        return healthPerLevel;
    }
}
