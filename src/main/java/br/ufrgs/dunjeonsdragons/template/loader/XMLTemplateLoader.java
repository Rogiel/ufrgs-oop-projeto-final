package br.ufrgs.dunjeonsdragons.template.loader;

import br.ufrgs.dunjeonsdragons.template.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLTemplateLoader implements TemplateLoader {

    /**
     * The URL to the data file
     */
    private URL dataFile;

    /**
     * A map of all game data templates
     */
    private Map<String, Template> templates;

    private List<ExperienceTableEntry> experienceTableEntries;

    /**
     * Creates a new XMLTemplateLoader from the given file
     *
     * @param dataFile the file to use as the base for loading templates
     */
    public XMLTemplateLoader(URL dataFile) {
        this.dataFile = dataFile;
    }

    @Override
    public Template load(String identifier) {
        if (templates == null) {
            loadXML();
        }
        return templates.get(identifier);
    }

    @Override
    public List<ExperienceTableEntry> getExperienceTable() {
        return experienceTableEntries;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The GameData.xml root element
     */
    @XmlRootElement(name = "Data")
    private static class GameDataRoot {
        /**
         * A list containing all loaded templates
         */
        @XmlElements({
                @XmlElement(name = "Race", type = PlayerRaceTemplate.class),
                @XmlElement(name = "Class", type = PlayerClassTemplate.class),
                @XmlElement(name = "Monster", type = MonsterTemplate.class),
                @XmlElement(name = "Map", type = MapTemplate.class),
        })
        List<Template> templates;

        /**
         * The experience table
         */
        @XmlElementWrapper(name = "ExperienceTable")
        @XmlElement(name = "Entry")
        List<ExperienceTableEntry> experienceTable;
    }

    /**
     * Loads the XML file and maps each template into a hash map
     */
    private void loadXML() {
        templates = new HashMap<>();
        try {
            JAXBContext context = JAXBContext.newInstance(GameDataRoot.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            GameDataRoot root = (GameDataRoot) unmarshaller.unmarshal(dataFile);
            for (Template t : root.templates) {
                templates.put(t.getIdentifier(), t);
            }
            experienceTableEntries = root.experienceTable;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
