package br.ufrgs.dunjeonsdragons.template;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Rogiel on 9/13/16.
 */
public class MapTemplate extends Template {

    /**
     * The map level (the level the monster will inherit)
     */
    @XmlAttribute(name = "level")
    private int level;

    /**
     * The map level (the level the monster will inherit)
     */
    @XmlAttribute(name = "nextMap")
    @XmlIDREF
    private MapTemplate nextMap;

    /**
     * The levels on the map
     */
    @XmlElements({
            @XmlElement(name = "Level", type = LevelTemplate.class)
    })
    private List<LevelTemplate> levels;

    // -----------------------------------------------------------------------------------------------------------------

    public int getLevel() {
        return level;
    }

    public MapTemplate getNextMap() {
        return nextMap;
    }

    public List<LevelTemplate> getLevels() {
        return levels;
    }
}
