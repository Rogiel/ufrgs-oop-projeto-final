package br.ufrgs.dunjeonsdragons.template.loader;

import br.ufrgs.dunjeonsdragons.template.ExperienceTableEntry;
import br.ufrgs.dunjeonsdragons.template.Template;

import java.util.List;

public interface TemplateLoader {

    /**
     * Loads a template given by the identifier
     *
     * @param identifier the template identifier to load
     * @return the loaded template or NULL if the template was not found
     */
    Template load(String identifier);

    /**
     * Gets the experience table
     *
     * @return the experience table
     */
    public List<ExperienceTableEntry> getExperienceTable();

}
