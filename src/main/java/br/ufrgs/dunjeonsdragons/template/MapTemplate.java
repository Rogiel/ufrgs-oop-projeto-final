package br.ufrgs.dunjeonsdragons.template;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlIDREF;
import java.util.List;

public class MapTemplate extends Template {

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

    public MapTemplate getNextMap() {
        return nextMap;
    }

    public List<LevelTemplate> getLevels() {
        return levels;
    }
}
