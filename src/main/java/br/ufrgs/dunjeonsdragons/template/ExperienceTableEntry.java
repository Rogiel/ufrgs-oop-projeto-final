package br.ufrgs.dunjeonsdragons.template;

import javax.xml.bind.annotation.XmlAttribute;

public class ExperienceTableEntry extends Template {

    @XmlAttribute(name = "level")
    private long level;

    @XmlAttribute(name = "experience")
    private long experience;

    public long getLevel() {
        return level;
    }

    public long getExperience() {
        return experience;
    }
}

