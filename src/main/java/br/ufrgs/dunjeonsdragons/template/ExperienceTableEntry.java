package br.ufrgs.dunjeonsdragons.template;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Rogiel on 9/13/16.
 */
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

