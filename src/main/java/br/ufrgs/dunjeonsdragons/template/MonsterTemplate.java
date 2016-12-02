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

    public String getName() {
        return name;
    }
}
