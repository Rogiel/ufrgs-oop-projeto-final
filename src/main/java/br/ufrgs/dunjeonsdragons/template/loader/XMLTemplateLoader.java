/*
 * Copyright (c) 2016
 *      Rogiel Sulzbach, Júlio Balena & Gustavo Oliveira.
 *      All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *      This product includes software developed by the copyright holders
 *      and its contributors.
 * 4. Neither the name of the copyright holders nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

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
