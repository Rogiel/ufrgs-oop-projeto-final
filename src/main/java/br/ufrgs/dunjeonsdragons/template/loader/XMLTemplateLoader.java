package br.ufrgs.dunjeonsdragons.template.loader;

import br.ufrgs.dunjeonsdragons.template.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.List;

/**
 * Created by Rogiel on 9/13/16.
 */
public class XMLTemplateLoader implements TemplateLoader {

    private File path;

    /**
     * Creates a new XMLTemplateLoader from the given file
     *
     * @param path the file to use as the base for loading templates
     */
    public XMLTemplateLoader(File path) {
        this.path = path;
    }

    @XmlRootElement(name = "Data")
    private static class TemplateRoot {
        @XmlElement(name = "Race")
        List<PlayerRaceTemplate> races;
    }

    @Override
    public Template load(String identifier) {
        try {
            JAXBContext context = JAXBContext.newInstance(TemplateRoot.class, EffectTemplate.class, ItemTemplate.class,
                    PlayerClassTemplate.class, PlayerRaceTemplate.class, SkillTemplate.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            ClassLoader classLoader = getClass().getClassLoader();
            TemplateRoot root = (TemplateRoot) unmarshaller.unmarshal(classLoader.getResource("GameData.xml"));

            System.out.println(root.races.size());
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }

}
