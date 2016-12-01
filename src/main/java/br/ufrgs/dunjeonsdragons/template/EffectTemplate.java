package br.ufrgs.dunjeonsdragons.template;

import br.ufrgs.dunjeonsdragons.effects.Effect;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Rogiel on 9/13/16.
 */
public class EffectTemplate extends Template {

    /**
     * The effect class name
     */
    @XmlAttribute(name = "ClassName")
    Class<? extends Effect> className;

    /**
     * @return the effect class name
     */
    public Class<? extends Effect> getClassName() {
        return className;
    }
}
