package br.ufrgs.dunjeonsdragons.template;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;

public abstract class Template {

    /**
     * The template identifier
     */
    @XmlID
    @XmlAttribute(name = "identifier")
    private String identifier;

    /**
     * @return the template identifier
     */
    public String getIdentifier() {
        return identifier;
    }
}
