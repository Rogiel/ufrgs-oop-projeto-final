package br.ufrgs.dunjeonsdragons.template;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlIDREF;
import java.util.List;
import java.util.Random;

public class LevelTemplate extends Template {

    private static final Random randomGenerator = new Random();

    /**
     * The level name
     */
    @XmlAttribute(name = "name")
    private String name;

    /**
     * The monsters on the level
     */
    @XmlElementWrapper(name = "Monsters")
    @XmlElement(name = "Monster")
    @XmlIDREF
    private List<MonsterTemplate> monsters;

    public String getName() {
        return name;
    }

    public List<MonsterTemplate> getMonsters() {
        return monsters;
    }

    public MonsterTemplate getRandomMonster() {
        return monsters.get(randomGenerator.nextInt(monsters.size()));
    }
}
