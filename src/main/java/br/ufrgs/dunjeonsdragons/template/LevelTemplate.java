package br.ufrgs.dunjeonsdragons.template;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Rogiel on 9/13/16.
 */
public class LevelTemplate extends Template {

    /**
     * The level name
     */
    @XmlAttribute(name = "name")
    private String name;

    /**
     * The monsters on the level
     */
    @XmlElement(name = "Monster")
    @XmlIDREF
    private MonsterTemplate monster;

    public String getName() {
        return name;
    }

    public MonsterTemplate getMonster() {
        return monster;
    }
}
