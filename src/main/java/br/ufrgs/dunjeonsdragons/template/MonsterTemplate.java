package br.ufrgs.dunjeonsdragons.template;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by Rogiel on 9/13/16.
 */
public class MonsterTemplate extends Template {

    /**
     * The monster name
     */
    @XmlAttribute(name = "Name")
    private String name;

    public String getName() {
        return name;
    }
}
