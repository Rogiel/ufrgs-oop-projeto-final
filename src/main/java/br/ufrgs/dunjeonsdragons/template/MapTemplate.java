package br.ufrgs.dunjeonsdragons.template;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import java.util.List;

/**
 * Created by Rogiel on 9/13/16.
 */
public class MapTemplate extends Template {

    /**
     * The levels on the map
     */
    @XmlElements({
            @XmlElement(name = "Level", type = LevelTemplate.class)
    })
    private List<LevelTemplate> levels;

    public List<LevelTemplate> getLevels() {
        return levels;
    }
}
