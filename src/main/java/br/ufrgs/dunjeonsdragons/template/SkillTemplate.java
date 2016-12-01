package br.ufrgs.dunjeonsdragons.template;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Rogiel on 9/13/16.
 */
@XmlRootElement(name = "Skill")
public class SkillTemplate extends Template {

    /**
     * The player class name
     */
    @XmlElement(name = "Name")
    private String name;

    /**
     * A list of all effects available for the skill
     */
    @XmlElementWrapper(name = "Effects")
    @XmlElements({
            @XmlElement(name = "Effect", type = EffectTemplate.class)
    })
    private List<EffectTemplate> effects;

    public String getName() {
        return name;
    }

    public List<EffectTemplate> getEffects() {
        return effects;
    }
}
