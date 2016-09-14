package br.ufrgs.dunjeonsdragons.template.loader;

import br.ufrgs.dunjeonsdragons.template.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rogiel on 9/13/16.
 */
public class XMLTemplateLoader implements TemplateLoader {

    private URL dataFile;

    /**
     * A map of all game data templates
     */
    private Map<String, Template> templates;

    /**
     * Creates a new XMLTemplateLoader from the given file
     *
     * @param dataFile the file to use as the base for loading templates
     */
    public XMLTemplateLoader(URL dataFile) {
        this.dataFile = dataFile;
    }

    private void loadXML() {
        templates = new HashMap<>();
        try {
            JAXBContext context = JAXBContext.newInstance(TemplateRoot.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            ClassLoader classLoader = getClass().getClassLoader();
            TemplateRoot root = (TemplateRoot) unmarshaller.unmarshal(dataFile);

            for (Template t : root.templates) {
                templates.put(t.getIdentifier(), t);
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @XmlRootElement(name = "Data")
    private static class TemplateRoot {
        @XmlElements({
                @XmlElement(name = "Race", type = PlayerRaceTemplate.class),
                @XmlElement(name = "Class", type = PlayerClassTemplate.class)
        })
        List<Template> templates;
    }

    @Override
    public Template load(String identifier) {
        if(templates == null) {
            loadXML();
        }
        return templates.get(identifier);
    }

}
